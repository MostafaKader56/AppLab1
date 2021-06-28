package com.technoship.appLab1.models;

import com.technoship.appLab1.utils.Constants;

import java.util.ArrayList;

public class LabClient {
    private String userId;
    private ArrayList<String> usedOffersIds;
    private ArrayList<String> time;

    public LabClient() { }

    public LabClient(String userId) {
        this.userId = userId;
        usedOffersIds = new ArrayList<>();
        time = Constants.setCurrentTime();
    }

    public LabClient(String userId, ArrayList<String> usedOffersIds) {
        this.userId = userId;
        this.usedOffersIds = usedOffersIds;
    }

    public String getUserId() {
        return userId;
    }

    public ArrayList<String> getUsedOffersIds() {
        return usedOffersIds;
    }

    public ArrayList<String> getTime() {
        return time;
    }
}
