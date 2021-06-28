package com.technoship.appLab1.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Offer implements Parcelable {
    private String title, details;
    private String titleAr, detailsAr;
    private String image;
    private boolean published, OneTimeUse;
    private String offerId;
    private ArrayList<String> time;

    public Offer() { }

    protected Offer(Parcel in) {
        title = in.readString();
        details = in.readString();
        titleAr = in.readString();
        detailsAr = in.readString();
        image = in.readString();
        published = in.readByte() != 0;
        OneTimeUse = in.readByte() != 0;
        offerId = in.readString();
        time = in.createStringArrayList();
    }

    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
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

    public String getImage() {
        return image;
    }

    public boolean isPublished() {
        return published;
    }

    public boolean isOneTimeUse() {
        return OneTimeUse;
    }

    public String getOfferId() {
        return offerId;
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
        parcel.writeByte((byte) (OneTimeUse ? 1 : 0));
        parcel.writeString(offerId);
        parcel.writeStringList(time);
    }
}
