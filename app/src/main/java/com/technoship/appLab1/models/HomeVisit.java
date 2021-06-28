package com.technoship.appLab1.models;

import com.technoship.appLab1.utils.Constants;

import java.util.ArrayList;

public class HomeVisit {
    private String fullName, phone, address, testName, notes, img;
    private double lat, lng;
    private ArrayList<String> time;

    public HomeVisit(){}

    public HomeVisit(String fullName, String phone, String address, String testName, String notes, String img) {
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.testName = testName;
        this.notes = notes;
        this.img = img;
        this.time = Constants.setCurrentTime();
    }

    public ArrayList<String> getTime() {
        return time;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getNotes() {
        return notes;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getTestName() {
        return testName;
    }

    public String getImg() {
        return img;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
