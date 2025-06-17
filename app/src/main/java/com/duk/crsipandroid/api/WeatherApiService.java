package com.duk.crsipandroid.api;

import com.duk.crsipandroid.mvp.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("forecast")
    Call<WeatherResponse> getWeatherResponse(
        @Query("lat") String lat,
        @Query("lon") String lon,
        @Query("appid") String appId
    );
}
