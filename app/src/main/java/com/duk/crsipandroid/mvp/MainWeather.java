package com.duk.crsipandroid.mvp;

import com.google.gson.annotations.SerializedName;

public class MainWeather {
    private double temp;
    private double humidity;
    @SerializedName("feels_like")
    private double feelsLike;

    public MainWeather(double temp, double humidity, double feelsLike){
        this.temp =temp;
        this.humidity = humidity;
        this.feelsLike = feelsLike;
    }
    public double getTemp() { return temp; }
    public double getHumidity() { return humidity; }
    public double getFeelsLike() { return feelsLike; }
}