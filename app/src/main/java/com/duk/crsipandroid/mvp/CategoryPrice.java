package com.duk.crsipandroid.mvp;

public class CategoryPrice {
    public String categoryName;
    public String rup;
    public String rupStat;
    public String dol;
    public  String dolStat;

    public CategoryPrice(String categoryName, String rup, String rupStat, String dol, String dolStat){
        this.categoryName = categoryName;
        this.rup = rup;
        this.rupStat = rupStat;
        this.dol = dol;
        this.dolStat = dolStat;
    }
}
