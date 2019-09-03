package com.example.a16500.socketdemo.utils;

/**
 * Created by 16500 on 2019/9/3.
 */

public class PlaceJs {
    private int sid;
    private String sname;
    private double slatitude;
    private double slongitude;

    public PlaceJs(int sid, String sname, double slatitude, double slongitude) {
        this.sid = sid;
        this.sname = sname;
        this.slatitude = slatitude;
        this.slongitude = slongitude;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public double getSlatitude() {
        return slatitude;
    }

    public void setSlatitude(double slatitude) {
        this.slatitude = slatitude;
    }

    public double getSlongitude() {
        return slongitude;
    }

    public void setSlongitude(double slongitude) {
        this.slongitude = slongitude;
    }
}
