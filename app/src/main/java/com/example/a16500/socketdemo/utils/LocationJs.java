package com.example.a16500.socketdemo.utils;

/**
 * Created by 16500 on 2019/9/3.
 */

public class LocationJs {

    private int uid;
    private int message;
    private double latitude;
    private double longitude;

    public LocationJs(int uid, int message, double latitude, double longitude) {
        this.uid = uid;
        this.message = message;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
