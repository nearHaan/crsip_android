package com.duk.crsipandroid.mvp;

import java.util.List;

public class PriceResponse {
    public String mareketLocation;
    public String date;
    public List<PricePageRowItem> categoryPrice;

    public PriceResponse(String mareketLocation, String date, List<PricePageRowItem> categoryPrice){
        this.mareketLocation = mareketLocation;
        this.date = date;
        this.categoryPrice = categoryPrice;
    }
}
