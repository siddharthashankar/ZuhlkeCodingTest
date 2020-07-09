package com.sid.zuhlkecodingtest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Items {
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("cameras")
    @Expose
    private List<TrafficsCamera> cameras = null;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<TrafficsCamera> getCameras() {
        return cameras;
    }

    public void setCameras(List<TrafficsCamera> cameras) {
        this.cameras = cameras;
    }

}