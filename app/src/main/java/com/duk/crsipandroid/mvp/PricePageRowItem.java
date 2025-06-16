package com.duk.crsipandroid.mvp;

public class PricePageRowItem{
    public String CategoryName;
    public String rup;
    public String rupStat;
    public String dol;
    public  String dolStat;

    public PricePageRowItem(String CategoryName, String rup, String rupStat, String dol, String dolStat){
        this.CategoryName = CategoryName;
        this.rup = rup;
        this.rupStat = rupStat;
        this.dol = dol;
        this.dolStat = dolStat;
    }
}
