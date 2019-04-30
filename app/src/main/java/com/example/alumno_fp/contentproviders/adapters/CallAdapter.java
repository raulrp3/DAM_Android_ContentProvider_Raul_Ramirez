package com.example.alumno_fp.contentproviders.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alumno_fp.contentproviders.R;
import com.example.alumno_fp.contentproviders.models.Call;

import java.util.List;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.ViewHolcerCall> {

    private Context context;
    private List<Call> calls;

    public CallAdapter(Context context, List<Call> calls){
        this.context = context;
        this.calls = calls;
    }


    @NonNull
    @Override
    public ViewHolcerCall onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_call, viewGroup, false);
        final ViewHolcerCall vhc = new ViewHolcerCall(view);

        return vhc;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolcerCall viewHolderCall, int i) {
        viewHolderCall.textPhone.setText(calls.get(i).getPhone());
        viewHolderCall.textContact.setText(calls.get(i).getType());
    }

    @Override
    public int getItemCount() {
        return calls.size();
    }

    public class ViewHolcerCall extends RecyclerView.ViewHolder{

        TextView textPhone;
        TextView textContact;

        public ViewHolcerCall(View itemView){
            super(itemView);

            textPhone = itemView.findViewById(R.id.textPhone);
            textContact = itemView.findViewById(R.id.textType);
        }
    }
}
