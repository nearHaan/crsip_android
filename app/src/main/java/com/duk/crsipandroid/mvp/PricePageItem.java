package com.duk.crsipandroid.mvp;

import java.util.List;

public class PricePageItem{
    public String title;
    public List<PricePageRowItem> items;

    public PricePageItem(String title, List<PricePageRowItem> items){
        this.title = title;
        this.items = items;
    }
}
