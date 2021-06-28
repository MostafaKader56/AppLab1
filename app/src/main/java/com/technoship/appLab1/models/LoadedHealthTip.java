package com.technoship.appLab1.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class LoadedHealthTip implements Parcelable {
    private String title, details;
    private String titleAr, detailsAr;
    private boolean published;
    private String image;
    private ArrayList<String> time;

    public LoadedHealthTip() { }

    protected LoadedHealthTip(Parcel in) {
        title = in.readString();
        details = in.readString();
        titleAr = in.readString();
        detailsAr = in.readString();
        published = in.readByte() != 0;
        image = in.readString();
        time = in.createStringArrayList();
    }

    public static final Creator<LoadedHealthTip> CREATOR = new Creator<LoadedHealthTip>() {
        @Override
        public LoadedHealthTip createFromParcel(Parcel in) {
            return new LoadedHealthTip(in);
        }

        @Override
        public LoadedHealthTip[] newArray(int size) {
            return new LoadedHealthTip[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public String getImage() {
        return image;
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
        parcel.writeByte((byte) (published ? 1 : 0));
        parcel.writeString(image);
        parcel.writeStringList(time);
    }
}
