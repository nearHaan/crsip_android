package com.duk.crsipandroid.network;

import com.duk.crsipandroid.BuildConfig;
import com.duk.crsipandroid.api.PriceApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientPrice {
    private static Retrofit retrofit;
    private static final String BASE_URL = BuildConfig.RUBBER_BASE_URL;

    public static PriceApiService getApiServie(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(PriceApiService.class);
    }
}
