package com.technoship.appLab1.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Announcement implements Parcelable {
    private String title, details;
    private String titleAr, detailsAr;
    private String image;
    private boolean published;
    private ArrayList<String> time;

    public Announcement() { }

    protected Announcement(Parcel in) {
        title = in.readString();
        details = in.readString();
        titleAr = in.readString();
        detailsAr = in.readString();
        image = in.readString();
        published = in.readByte() != 0;
        time = in.createStringArrayList();
    }

    public static final Creator<Announcement> CREATOR = new Creator<Announcement>() {
        @Override
        public Announcement createFromParcel(Parcel in) {
            return new Announcement(in);
        }

        @Override
        public Announcement[] newArray(int size) {
            return new Announcement[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public String getDetailsAr() {
        return detailsAr;
    }

    public boolean isPublished() {
        return published;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(details);
        parcel.writeString(titleAr);
        parcel.writeString(detailsAr);
        parcel.writeString(image);
        parcel.writeByte((byte) (published ? 1 : 0));
        parcel.writeStringList(time);
    }
}
