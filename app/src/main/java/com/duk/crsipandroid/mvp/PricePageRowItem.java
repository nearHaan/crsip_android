package com.duk.crsipandroid.mvp;

public class PricePageRowItem{
    public String categoryName;
    public String rup;
    public String rupStat;
    public String dol;
    public  String dolStat;

    public PricePageRowItem(String categoryName, String rup, String rupStat, String dol, String dolStat){
        this.categoryName = categoryName;
        this.rup = rup;
        this.rupStat = rupStat;
        this.dol = dol;
        this.dolStat = dolStat;
    }
}
