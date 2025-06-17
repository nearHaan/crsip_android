package com.duk.crsipandroid.mvp;

public class CategoryPrice {
    public String CategoryName;
    public double rupeeamt;
    public String rupeeindicator;
    public double dollaramt;
    public  String dollarindiator;

    public CategoryPrice(String CategoryName, double rupeeamt, String rupeeindicator, double dollaramt, String dollarindiator){
        this.CategoryName = CategoryName;
        this.rupeeamt = rupeeamt;
        this.rupeeindicator = rupeeindicator;
        this.dollaramt = dollaramt;
        this.dollarindiator = dollarindiator;
    }

    public String getCategoryName(){ return CategoryName; }
    public double getRupees(){ return rupeeamt; }
    public String getRupStat(){ return  rupeeindicator; }
    public double getDollars(){ return  dollaramt; }
    public String getDolStat(){ return  dollarindiator; }
}
