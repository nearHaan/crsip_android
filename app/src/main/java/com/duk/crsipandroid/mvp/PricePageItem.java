package com.duk.crsipandroid.mvp;

import java.util.List;

public class PricePageItem{
    public String location;
    public List<PricePageRowItem> items;

    public PricePageItem(String location, List<PricePageRowItem> items){
        this.location = location;
        this.items = items;
    }
}
