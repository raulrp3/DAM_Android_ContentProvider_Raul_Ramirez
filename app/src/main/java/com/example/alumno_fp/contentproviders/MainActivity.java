package com.example.alumno_fp.contentproviders;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alumno_fp.contentproviders.adapters.CallAdapter;
import com.example.alumno_fp.contentproviders.models.Call;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonLoad;
    private List<Call> calls;
    private CallAdapter callAdapter;
    private RecyclerView rvCalls;
    public static final int PERMISSIONS_REQUEST_READ_CALLS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalls();
            }
        });
    }

    private void initUI(){
        buttonLoad = findViewById(R.id.button_load);
        rvCalls = findViewById(R.id.rvCalls);
        rvCalls.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvCalls.setHasFixedSize(true);
    }

    private void showCalls(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, PERMISSIONS_REQUEST_READ_CALLS);
        }else{
            calls = getCallNumber();
            callAdapter = new CallAdapter(getApplicationContext(), calls);
            rvCalls.setAdapter(callAdapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CALLS){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showCalls();
            }else{
                Toast.makeText(this, "¡No tienes permisos, no puedes ver el registro de llamadas!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<Call> getCallNumber(){
        List<Call> calls = new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CallLog.Calls.CONTENT_URI,null, null, null, null);

        if (cursor.moveToFirst()){
            do {
                String phone = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                String type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
                calls.add(new Call(phone,getType(Integer.valueOf(type))));
            }while(cursor.moveToNext());
        }

        cursor.close();
        return calls;
    }

    private String getType(int type){
        String resultType = "";
        switch (type){
            case CallLog.Calls.INCOMING_TYPE:{
                resultType = "Incoming call";
            }
            break;
            case CallLog.Calls.MISSED_TYPE: {
                resultType = "Missed call";
            }
            break;
            case CallLog.Calls.OUTGOING_TYPE: {
                resultType = "Outgoing call";
            }
        }

        return resultType;
    }
}
