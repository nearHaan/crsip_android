package com.duk.crsipandroid.api;

import com.duk.crsipandroid.mvp.PriceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface PriceApiService {
    @GET("rubservice")
    Call<List<PriceResponse>> getPrices(
            @Query("task") String task,
            @Query("type") String type
    );
}