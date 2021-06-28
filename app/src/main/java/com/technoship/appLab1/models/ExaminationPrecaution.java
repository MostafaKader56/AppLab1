package com.technoship.appLab1.models;

public class ExaminationPrecaution {
    private final String title;
    private final String details;

    public ExaminationPrecaution(String title, String details) {
        this.title = title;
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }
}
