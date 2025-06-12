package com.duk.crsipandroid.mvp;

import com.duk.crsipandroid.R;

public class RecommendationItem{
    public int id;
    public String title;
    public int icon;

    public int bgColor;

    public RecommendationItem(int id, String title, int icon){
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.bgColor = R.color.app_green;
    }
}
