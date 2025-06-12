package com.duk.crsipandroid.mvp;

public class RubberFacility{
    int id;
    public String title;
    public String subTitle;
    public double lat;
    public double lon;
    public RubberFacility(int id, String title, String subTitle, double lat, double lon){
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.lat = lat;
        this.lon = lon;
    }
}