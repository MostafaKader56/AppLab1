package com.technoship.appLab1.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Branch implements Parcelable {
    private String name, address, phone;
    private String nameAr, addressAr;
    private double longitude, latitude;
    private boolean published;
    private ArrayList<String> time;

    public Branch() { }

    protected Branch(Parcel in) {
        name = in.readString();
        address = in.readString();
        phone = in.readString();
        nameAr = in.readString();
        addressAr = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
        published = in.readByte() != 0;
        time = in.createStringArrayList();
    }

    public static final Creator<Branch> CREATOR = new Creator<Branch>() {
        @Override
        public Branch createFromParcel(Parcel in) {
            return new Branch(in);
        }

        @Override
        public Branch[] newArray(int size) {
            return new Branch[size];
        }
    };

    public String getName() {
        return name;
    }

    public boolean isPublished() {
        return published;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getNameAr() {
        return nameAr;
    }

    public String getAddressAr() {
        return addressAr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(phone);
        parcel.writeString(nameAr);
        parcel.writeString(addressAr);
        parcel.writeDouble(longitude);
        parcel.writeDouble(latitude);
        parcel.writeByte((byte) (published ? 1 : 0));
        parcel.writeStringList(time);
    }
}
