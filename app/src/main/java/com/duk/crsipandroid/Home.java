package com.duk.crsipandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Home extends AppCompatActivity implements RecommendationAdapter.OnItemClickListener, AdvisoryAdapter.onItemClickListener, FaqAdapter.onItemClickListener, FacilityAdapter.onItemClickListener, View.OnClickListener {

    private RecyclerView rv_recommendations, rv_advisory, rv_faqs, rv_rubber_facility, rv_rubber_price_page, rv_weather_forecast;
    private MaterialButton btn_domestic, btn_international;
    private TextView tv_temp, tv_prec, tv_wind_speed, tv_feels_like, tv_weather;
    RelativeLayout notificationContainer;
    private MaterialToolbar toolbar;
    private RecommendationAdapter recommendationAdapter;
    private AdvisoryAdapter advisoryAdapter;
    private FaqAdapter faqAdapter;
    private  FacilityAdapter facilityAdapter;
    private WeatherAdapter weatherAdapter;
    private PricePageAdapter pricePageAdapter;

    private boolean isDomestic = true;
    private List<PricePageItem> domesticPages, internationalPages;

    private SnapHelper snapHelper;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        setupToolbar();
        setButtons();
        setupRecyclerViews();
        setupPages();
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

    @Override
    public void onFacilityClick(RubberFacility item) {
        handleFacilityClick(item);
    }

    void initViews(){
        rv_recommendations = findViewById(R.id.rv_recommendations);
        rv_advisory = findViewById(R.id.rv_advisory);
        rv_faqs = findViewById(R.id.rv_faqs);
        rv_rubber_facility = findViewById(R.id.rv_rubber_facility);
        rv_rubber_price_page = findViewById(R.id.rv_rubber_price_page);
        rv_weather_forecast = findViewById(R.id.rv_weather_forecast);
        tv_temp = findViewById(R.id.tv_temp);
        tv_prec = findViewById(R.id.tv_prec);
        tv_wind_speed = findViewById(R.id.tv_wind_speed);
        tv_feels_like = findViewById(R.id.tv_feels_like);
        tv_weather = findViewById(R.id.tv_weather);
        notificationContainer = findViewById(R.id.notification_container);
        btn_domestic = findViewById(R.id.btn_domestic);
        btn_international = findViewById(R.id.btn_international);
        btn_domestic.setOnClickListener(this);
        btn_international.setOnClickListener(this);
    }

    void setButtons() {
        if (isDomestic) {
            btn_domestic.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.blue)));
            btn_international.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.white)));
            btn_domestic.setTextColor(ContextCompat.getColor(this, R.color.white));
            btn_international.setTextColor(ContextCompat.getColor(this, R.color.blue));
        } else {
            btn_domestic.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.white)));
            btn_international.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.blue)));
            btn_domestic.setTextColor(ContextCompat.getColor(this, R.color.blue));
            btn_international.setTextColor(ContextCompat.getColor(this, R.color.white));

        }
        setupPages();
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.tb_home);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        notificationContainer.setOnClickListener(this);
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

        rv_rubber_facility.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        facilityAdapter = new FacilityAdapter(getFacility());
        facilityAdapter.setItemClickListener(this);
        rv_rubber_facility.setAdapter(facilityAdapter);

        rv_weather_forecast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        weatherAdapter = new WeatherAdapter(getForeCastItems());
        rv_weather_forecast.setAdapter(weatherAdapter);
    }

    private void setupPages(){
        try {
            pricePageAdapter = new PricePageAdapter(getPricePages(isDomestic?"domestic":"international"));
            rv_rubber_price_page.setAdapter(pricePageAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            rv_rubber_price_page.setLayoutManager(layoutManager);

            if (rv_rubber_price_page.getOnFlingListener() == null) {
                snapHelper = new LinearSnapHelper();
                snapHelper.attachToRecyclerView(rv_rubber_price_page);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private String priceJSONlist = "{\n" +
            "  \"domestic\": {\n" +
            "    \"Kottayam\": [\n" +
            "      {\n" +
            "        \"title\": \"Title1\",\n" +
            "        \"rupees\": \"23.23\",\n" +
            "        \"rupStat\": \"rise\",\n" +
            "        \"dollars\": \"23.23\",\n" +
            "        \"dolStat\": \"fall\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"title\": \"Title2\",\n" +
            "        \"rupees\": \"23.23\",\n" +
            "        \"rupStat\": \"rise\",\n" +
            "        \"dollars\": \"23.23\",\n" +
            "        \"dolStat\": \"fall\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"title\": \"Title3\",\n" +
            "        \"rupees\": \"23.23\",\n" +
            "        \"rupStat\": \"rise\",\n" +
            "        \"dollars\": \"23.23\",\n" +
            "        \"dolStat\": \"fall\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Trivandrum\": [\n" +
            "      {\n" +
            "        \"title\": \"Title1\",\n" +
            "        \"rupees\": \"23.23\",\n" +
            "        \"rupStat\": \"rise\",\n" +
            "        \"dollars\": \"23.23\",\n" +
            "        \"dolStat\": \"fall\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"title\": \"Title2\",\n" +
            "        \"rupees\": \"23.23\",\n" +
            "        \"rupStat\": \"rise\",\n" +
            "        \"dollars\": \"23.23\",\n" +
            "        \"dolStat\": \"fall\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"title\": \"Title3\",\n" +
            "        \"rupees\": \"23.23\",\n" +
            "        \"rupStat\": \"rise\",\n" +
            "        \"dollars\": \"23.23\",\n" +
            "        \"dolStat\": \"fall\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Kollam\": [\n" +
            "      {\n" +
            "        \"title\": \"Title4\",\n" +
            "        \"rupees\": \"23.23\",\n" +
            "        \"rupStat\": \"rise\",\n" +
            "        \"dollars\": \"23.23\",\n" +
            "        \"dolStat\": \"fall\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"title\": \"Title5\",\n" +
            "        \"rupees\": \"23.23\",\n" +
            "        \"rupStat\": \"rise\",\n" +
            "        \"dollars\": \"23.23\",\n" +
            "        \"dolStat\": \"fall\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  \"international\": {\n" +
            "    \"India\": [\n" +
            "      {\n" +
            "        \"title\": \"Title6\",\n" +
            "        \"rupees\": \"23.23\",\n" +
            "        \"rupStat\": \"rise\",\n" +
            "        \"dollars\": \"23.23\",\n" +
            "        \"dolStat\": \"fall\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"USA\": [\n" +
            "      {\n" +
            "        \"title\": \"Title7\",\n" +
            "        \"rupees\": \"23.23\",\n" +
            "        \"rupStat\": \"rise\",\n" +
            "        \"dollars\": \"23.23\",\n" +
            "        \"dolStat\": \"fall\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"title\": \"Title8\",\n" +
            "        \"rupees\": \"23.23\",\n" +
            "        \"rupStat\": \"rise\",\n" +
            "        \"dollars\": \"23.23\",\n" +
            "        \"dolStat\": \"fall\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";
    private List<PricePageItem> getPricePages(String domain) throws JSONException{
        List<PricePageItem> items = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(priceJSONlist);
            JSONObject domestic = root.getJSONObject(domain);

            Iterator<String> keys = domestic.keys();

            while (keys.hasNext()) {
                String location = keys.next(); // e.g., "Kottayam"
                JSONArray itemsArray = domestic.getJSONArray(location);

                List<PricePageRowItem> rowItems = new ArrayList<>();

                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject rowObj = itemsArray.getJSONObject(i);
                    PricePageRowItem rowItem = new PricePageRowItem(
                            rowObj.getString("title"),
                            rowObj.getString("rupees"),
                            rowObj.getString("rupStat"),
                            rowObj.getString("dollars"),
                            rowObj.getString("dolStat")
                    );
                    rowItems.add(rowItem);
                }

                PricePageItem pricePageItem = new PricePageItem(location, rowItems);
                items.add(pricePageItem);
            }
            return items;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
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

    private List<RubberFacility> getFacility() {
        List<RubberFacility> items = new ArrayList<>();
        items.add(new RubberFacility(0, "Kollam", "Subtitle", 8.886900, 76.590469));
        items.add(new RubberFacility(1, "Trivandrum", "Subtitle", 8.530240, 76.929100));
        items.add(new RubberFacility(2, "Title3", "Subtitle", 8.5241, 76.9366));
        items.add(new RubberFacility(3, "Title4", "Subtitle", 8.5241, 76.9366));
        items.add(new RubberFacility(4, "Title5", "Subtitle", 8.5241, 76.9366));
        return items;
    }

    private List<WeatherForeCast> getForeCastItems() {
        List<WeatherForeCast> items = new ArrayList<>();
        items.add(new WeatherForeCast("11-06-2025", "Wednesday", "11:00am", R.drawable.duk_logo, "Light Rain", "29°C", "73%"));
        items.add(new WeatherForeCast("11-06-2025", "Wednesday", "11:00am", R.drawable.duk_logo, "Light Rain", "29°C", "73%"));
        items.add(new WeatherForeCast("11-06-2025", "Wednesday", "11:00am", R.drawable.duk_logo, "Light Rain", "29°C", "73%"));
        items.add(new WeatherForeCast("11-06-2025", "Wednesday", "11:00am", R.drawable.duk_logo, "Light Rain", "29°C", "73%"));
        items.add(new WeatherForeCast("11-06-2025", "Wednesday", "11:00am", R.drawable.duk_logo, "Light Rain", "29°C", "73%"));
        items.add(new WeatherForeCast("11-06-2025", "Wednesday", "11:00am", R.drawable.duk_logo, "Light Rain", "29°C", "73%"));
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

    private void handleFacilityClick(RubberFacility item){
        Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MapActivity.class);

        intent.putExtra(MapActivity.EXTRA_LATITUDE, item.lat);
        intent.putExtra(MapActivity.EXTRA_LONGITUDE, item.lon);
        intent.putExtra(MapActivity.EXTRA_TITLE, item.title);

        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_domestic){
            isDomestic = true;
            setButtons();
        } else if (v.getId() == R.id.btn_international){
            isDomestic = false;
            setButtons();
        } else if (v.getId() == R.id.notification_container) {
            Toast.makeText(this, "Notifications clicked", Toast.LENGTH_SHORT).show();
        }
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

class RubberFacility{
    int id;
    public String title;
    public String subTitle;
    public double lat;
    public double lon;
    public RubberFacility(int id, String title, String subTitle, double lat, double lon){
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.lat = lat;
        this.lon = lon;
    }
}

class WeatherForeCast {
    public String date;
    public String day;
    public String time;
    public int weatherRes;
    public String weather;
    public String temp;
    public String precp;

    public WeatherForeCast(String date, String day, String time, int weatherRes, String weather, String temp, String precp){
        this.date = date;
        this.day = day;
        this.time = time;
        this.weatherRes = weatherRes;
        this.weather = weather;
        this.temp = temp;
        this.precp = precp;
    }
}

class PricePageItem{
    public String title;
    public List<PricePageRowItem> items;

    public PricePageItem(String title, List<PricePageRowItem> items){
        this.title = title;
        this.items = items;
    }
}

class PricePageRowItem{
    public String title;
    public String rup;
    public String rupStat;
    public String dol;
    public  String dolStat;

    public PricePageRowItem(String title, String rup, String rupStat, String dol, String dolStat){
        this.title = title;
        this.rup = rup;
        this.rupStat = rupStat;
        this.dol = dol;
        this.dolStat = dolStat;
    }
}