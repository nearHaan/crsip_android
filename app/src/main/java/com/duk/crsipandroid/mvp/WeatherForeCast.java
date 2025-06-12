package com.duk.crsipandroid.mvp;

public class WeatherForeCast {
    public String date;
    public String day;
    public String time;
    public int weatherRes;
    public String weather;
    public String temp;
    public String precp;

    public WeatherForeCast(String date, String day, String time, int weatherRes, String weather, String temp, String precp){
        this.date = date;
        this.day = day;
        this.time = time;
        this.weatherRes = weatherRes;
        this.weather = weather;
        this.temp = temp;
        this.precp = precp;
    }
}