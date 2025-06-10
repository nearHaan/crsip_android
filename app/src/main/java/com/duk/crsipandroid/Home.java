package com.duk.crsipandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements RecommendationAdapter.OnItemClickListener, AdvisoryAdapter.onItemClickListener, FaqAdapter.onItemClickListener {

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

    @Override
    public void onRecommendationClick(RecommendationItem item) {
        handleRecommendationClick(item);
    }

    @Override
    public void onAdvisoryItemClick(AdvisoryItem item) {
        handleAdvisoryClick(item);
    }

    @Override
    public void onFaqItemClick(FaqItem item) {
        handleFaqClick(item);
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
        recommendationAdapter.setItemClickListener(this);
        rv_recommendations.setAdapter(recommendationAdapter);

        rv_advisory.setLayoutManager(new GridLayoutManager(this, 4));
        advisoryAdapter = new AdvisoryAdapter(getAdvisoryItems());
        advisoryAdapter.setItemClickListener(this);
        rv_advisory.setAdapter(advisoryAdapter);

        rv_faqs.setLayoutManager(new GridLayoutManager(this, 4));
        faqAdapter = new FaqAdapter(getFaqItems());
        faqAdapter.setItemClickListener(this);
        rv_faqs.setAdapter(faqAdapter);
    }

    private List<RecommendationItem> getRecommedationItems(){
        List<RecommendationItem> items = new ArrayList<>();
        items.add(new RecommendationItem(0, "Cloves", R.drawable.duk_logo));
        items.add(new RecommendationItem(1, "Planting Pits", R.drawable.org_logo));
        items.add(new RecommendationItem(2, "Plant Spacing/Density", R.drawable.duk_logo));
        items.add(new RecommendationItem(3, "Intercrops", R.drawable.org_logo));
        items.add(new RecommendationItem(4, "Rubsis", R.drawable.duk_logo));
        items.add(new RecommendationItem(5, "DFR/Soil and Leaf Sampling", R.drawable.org_logo));
        return items;
    }

    private List<AdvisoryItem> getAdvisoryItems(){
        List<AdvisoryItem> items = new ArrayList<>();
        items.add(new AdvisoryItem(0, "Latex Harvesting", R.drawable.duk_logo));
        items.add(new AdvisoryItem(1, "Rubber Processing", R.drawable.org_logo));
        items.add(new AdvisoryItem(2, "Disease and Pest Management", R.drawable.duk_logo));
        items.add(new AdvisoryItem(3, "Disease Identification", R.drawable.org_logo));
        items.add(new AdvisoryItem(4, "ABC", R.drawable.duk_logo));
        items.add(new AdvisoryItem(5, "Flood Vulnerability", R.drawable.org_logo));
        items.add(new AdvisoryItem(6, "Drought", R.drawable.duk_logo));
        items.add(new AdvisoryItem(7, "DFR/Soil and Leaf Sampling", R.drawable.org_logo));
        return items;
    }

    private List<FaqItem> getFaqItems() {
        List<FaqItem> items = new ArrayList<>();
        items.add(new FaqItem(0, "Crsip Management", R.drawable.org_logo));
        items.add(new FaqItem(1, "Crop Protection", R.drawable.duk_logo));
        items.add(new FaqItem(2, "Latex Harvesting", R.drawable.org_logo));
        items.add(new FaqItem(3, "Planting Materials", R.drawable.duk_logo));
        return items;
    }

    private void handleRecommendationClick(RecommendationItem item){
        Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show();
    }

    private void handleAdvisoryClick(AdvisoryItem item){
        Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show();
    }

    private void handleFaqClick(FaqItem item){
        Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show();
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