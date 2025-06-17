package com.duk.crsipandroid.mvp;

import java.util.List;

public class PriceResponse {
    public String MarketLocation;
    public String date;
    public List<CategoryPrice> Categoryprice;

    public PriceResponse(String MarketLocation, String date, List<CategoryPrice> Categoryprice){
        this.MarketLocation = MarketLocation;
        this.date = date;
        this.Categoryprice = Categoryprice;
    }

    public String getMarketLocation(){ return MarketLocation; }
    public String getDate(){ return date; }
    public List<CategoryPrice> getCategoryPrice(){ return Categoryprice; }
}
