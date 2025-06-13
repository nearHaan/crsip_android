package com.duk.crsipandroid.mvp;

import com.duk.crsipandroid.R;

public class AdvisoryItem{
    public int id;
    public String title;
    public int icon;
    public int bgColor;

    public AdvisoryItem(int id, String title, int icon){
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.bgColor = R.color.red;
    }
}
