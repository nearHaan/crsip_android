package com.duk.crsipandroid.mvp;

import com.google.gson.annotations.SerializedName;

public class MainWeather {
    private double temp;
    @SerializedName("feels_like")
    private double feelsLike;

    public MainWeather(double temp, double feelsLike){
        this.temp =temp;
        this.feelsLike = feelsLike;
    }
    public double getTemp() { return temp; }
    public double getFeelsLike() { return feelsLike; }
}