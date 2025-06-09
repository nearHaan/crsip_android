package com.duk.crsipandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private RecyclerView rv_recommendations, rv_advisory, rv_faqs, rv_rubber_nearby, rv_rubber_price;
    private TextView tv_temp, tv_prec, tv_wind_speed, tv_feels_like, tv_weather;

    private RecommendationAdapter recommendationAdapter;
    private AdvisoryAdapter advisoryAdapter;
    private FaqAdapter faqAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        setupRecyclerViews();
    }

    void initViews(){
        rv_recommendations = findViewById(R.id.rv_recommendations);
        rv_advisory = findViewById(R.id.rv_advisory);
        rv_faqs = findViewById(R.id.rv_faqs);
        rv_rubber_nearby = findViewById(R.id.rv_rubber_nearby);
        rv_rubber_price = findViewById(R.id.rv_rubber_price);
        tv_temp = findViewById(R.id.tv_temp);
        tv_prec = findViewById(R.id.tv_prec);
        tv_wind_speed = findViewById(R.id.tv_wind_speed);
        tv_feels_like = findViewById(R.id.tv_feels_like);
        tv_weather = findViewById(R.id.tv_weather);
    }

    private void setupRecyclerViews(){
        rv_recommendations.setLayoutManager(new GridLayoutManager(this, 3));
        recommendationAdapter = new RecommendationAdapter(getRecommedationItems());
        rv_recommendations.setAdapter(recommendationAdapter);

        rv_advisory.setLayoutManager(new GridLayoutManager(this, 4));
        advisoryAdapter = new AdvisoryAdapter(getAdvisoryItems());
        rv_advisory.setAdapter(advisoryAdapter);

        rv_faqs.setLayoutManager(new GridLayoutManager(this, 4));
        faqAdapter = new FaqAdapter(getFaqItems());
        rv_faqs.setAdapter(faqAdapter);
    }

    private List<RecommendationItem> getRecommedationItems(){
        List<RecommendationItem> items = new ArrayList<>();
        items.add(new RecommendationItem(0, "Cloves", R.drawable.org_logo));
        items.add(new RecommendationItem(1, "ABC", R.drawable.org_logo));
        items.add(new RecommendationItem(2, "ABC", R.drawable.org_logo));
        items.add(new RecommendationItem(3, "ABC", R.drawable.org_logo));
        items.add(new RecommendationItem(4, "ABC", R.drawable.org_logo));
        items.add(new RecommendationItem(5, "ABC", R.drawable.org_logo));
        return items;
    }

    private List<AdvisoryItem> getAdvisoryItems(){
        List<AdvisoryItem> items = new ArrayList<>();
        items.add(new AdvisoryItem(0, "ABC", R.drawable.org_logo));
        items.add(new AdvisoryItem(1, "ABC", R.drawable.org_logo));
        items.add(new AdvisoryItem(2, "ABC", R.drawable.org_logo));
        items.add(new AdvisoryItem(3, "ABC", R.drawable.org_logo));
        items.add(new AdvisoryItem(4, "ABC", R.drawable.org_logo));
        items.add(new AdvisoryItem(5, "ABC", R.drawable.org_logo));
        items.add(new AdvisoryItem(6, "ABC", R.drawable.org_logo));
        items.add(new AdvisoryItem(7, "ABC", R.drawable.org_logo));
        return items;
    }

    private List<FaqItem> getFaqItems() {
        List<FaqItem> items = new ArrayList<>();
        items.add(new FaqItem(0, "ABC", R.drawable.org_logo));
        items.add(new FaqItem(1, "ABC", R.drawable.org_logo));
        items.add(new FaqItem(2, "ABC", R.drawable.org_logo));
        items.add(new FaqItem(3, "ABC", R.drawable.org_logo));
        return items;
    }
}

class RecommendationItem{
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

class AdvisoryItem{
    public int id;
    public String title;
    public int icon;
    public int bgColor;

    public AdvisoryItem(int id, String title, int icon){
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.bgColor = R.color.app_green;
    }
}

class FaqItem{
    int id;
    public String title;
    public int icon;

    public int bgColor;

    public FaqItem(int id, String title, int icon){
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.bgColor = R.color.app_green;
    }
}

class RubberNearbyItem{
    int id;
    public String title;
    public String subTitle;
    public RubberNearbyItem(int id, String title, String subTitle){
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
    }
}