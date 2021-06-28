package com.technoship.appLab1.models;

import com.technoship.appLab1.utils.Constants;

import java.util.ArrayList;

public class ResultRequest {
    private String patientName, phone, notes, img;
//    private double lat, lng;
    private ArrayList<String> time;

    public ResultRequest(){}

    public ResultRequest(String patientName, String phone, String notes, String img) {
        this.patientName = patientName;
        this.phone = phone;
        this.notes = notes;
        this.img = img;
        this.time = Constants.setCurrentTime();
    }

//    public void setLat(double lat) {
//        this.lat = lat;
//    }
//
//    public void setLng(double lng) {
//        this.lng = lng;
//    }

    public String getPatientName() {
        return patientName;
    }

    public String getPhone() {
        return phone;
    }

    public String getNotes() {
        return notes;
    }

    public String getImg() {
        return img;
    }

//    public double getLat() {
//        return lat;
//    }
//
//    public double getLng() {
//        return lng;
//    }

    public ArrayList<String> getTime() {
        return time;
    }
}
