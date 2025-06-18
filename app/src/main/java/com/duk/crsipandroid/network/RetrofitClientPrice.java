package com.duk.crsipandroid.network;

import com.duk.crsipandroid.BuildConfig;
import com.duk.crsipandroid.adapters.WeatherDeserializer;
import com.duk.crsipandroid.api.WeatherApiService;
import com.duk.crsipandroid.mvp.WeatherResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientPrice {
    private static Retrofit retrofit;
    private static final String BASE_URL = BuildConfig.RUBBER_BASE_URL;

    public static WeatherApiService getApiServie(){
        if (retrofit == null){
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(WeatherResponse.class, new WeatherDeserializer())
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit.create(WeatherApiService.class);
    }
}
