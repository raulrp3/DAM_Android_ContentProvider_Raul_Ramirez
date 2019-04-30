package com.example.alumno_fp.contentproviders.models;

public class Call {

    private String phone;
    private String type;

    public Call(String phone, String type) {
        this.phone = phone;
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Call{" +
                "phone='" + phone + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
