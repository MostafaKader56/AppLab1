package com.technoship.appLab1.models;

public class HealthTip {
    private final String title, details, image;
    public HealthTip(String title, String details, String image) {
        this.title = title;
        this.details = details;
        this.image = image;
    }


    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }
}
