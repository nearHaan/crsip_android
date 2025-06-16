package com.duk.crsipandroid.mvp;

import java.util.List;

public class PriceResponse {
    public String mareketLocation;
    public String date;
    public List<CategoryPrice> categoryPrice;

    public PriceResponse(String mareketLocation, String date, List<CategoryPrice> categoryPrice){
        this.mareketLocation = mareketLocation;
        this.date = date;
        this.categoryPrice = categoryPrice;
    }

    public String getMarketLocation(){ return mareketLocation; }
    public String getDate(){ return date; }
    public List<CategoryPrice> getCategoryPrice(){ return categoryPrice; }
}
