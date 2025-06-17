package com.duk.crsipandroid.mvp;

import java.util.List;

public class WeatherResponse {
    private List<WeatherItem> list;
    public WeatherResponse(List<WeatherItem> list) {
        this.list = list;
    }
    public List<WeatherItem> getList() { return list; }
}
