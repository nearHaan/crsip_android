package com.duk.crsipandroid.network;

import com.duk.crsipandroid.BuildConfig;
import com.duk.crsipandroid.api.PriceApiService;

import retrofit2.Retrofit;

public class RetrofitClientPrices {
    private static final String BASE_URL = BuildConfig.RUBBER_BASE_URL;
    private static Retrofit retrofit;

    public static PriceApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(PriceApiService.class);
    }
}