package com.duk.crsipandroid.mvp;

public class CategoryPrice {
    public String categoryName;
    public double rup;
    public String rupStat;
    public double dol;
    public  String dolStat;

    public CategoryPrice(String categoryName, double rup, String rupStat, double dol, String dolStat){
        this.categoryName = categoryName;
        this.rup = rup;
        this.rupStat = rupStat;
        this.dol = dol;
        this.dolStat = dolStat;
    }

    public String getCategoryName(){ return categoryName; }
    public double getRupees(){ return rup; }
    public String getRupStat(){ return  rupStat; }
    public double getDollars(){ return  dol; }
    public String getDolStat(){ return  dolStat; }
}
