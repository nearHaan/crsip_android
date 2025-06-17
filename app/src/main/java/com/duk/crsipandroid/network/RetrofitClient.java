package com.duk.crsipandroid.network;

import com.duk.crsipandroid.api.ApiService;

import retrofit2.Retrofit;

public class RetrofitClient {
    private static final String BASE_URL = "https://rubberboard.gov.in/";
    private static Retrofit retrofit;

    public static ApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}