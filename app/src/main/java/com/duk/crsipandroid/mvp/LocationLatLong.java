package com.duk.crsipandroid.mvp;

public class LocationLatLong {
    private String location;
    private double latitude;
    private double longitude;

    public LocationLatLong(String location, double latitude, double longitude){
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocation(){ return location; }
    public double getLatitude(){ return latitude; }
    public double getLongitude() { return longitude; }
}
