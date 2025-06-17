package com.duk.crsipandroid.mvp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherItem {
    @SerializedName("dt_txt")
    private String dtTxt;
    private MainWeather main;
    private List<Weather> weather;

    public WeatherItem(String dtTxt, MainWeather main, List<Weather> weather){
        this.dtTxt = dtTxt;
        this.main = main;
        this.weather = weather;
    }
    public String getDtTxt() { return dtTxt; }
    public MainWeather getMain() { return main; }
    public List<Weather> getWeather() { return weather; }
}