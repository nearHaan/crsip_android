package com.duk.crsipandroid.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.duk.crsipandroid.R;
import com.duk.crsipandroid.adapters.AdvisoryAdapter;
import com.duk.crsipandroid.adapters.FacilityAdapter;
import com.duk.crsipandroid.adapters.FaqAdapter;
import com.duk.crsipandroid.adapters.PricePageAdapter;
import com.duk.crsipandroid.adapters.RecommendationAdapter;
import com.duk.crsipandroid.adapters.WeatherAdapter;
import com.duk.crsipandroid.mvp.AdvisoryItem;
import com.duk.crsipandroid.mvp.FaqItem;
import com.duk.crsipandroid.mvp.PricePageItem;
import com.duk.crsipandroid.mvp.PricePageRowItem;
import com.duk.crsipandroid.mvp.RecommendationItem;
import com.duk.crsipandroid.mvp.RubberFacility;
import com.duk.crsipandroid.mvp.WeatherForeCast;
import com.duk.crsipandroid.utils.BottomSheetLocation;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Home extends AppCompatActivity implements RecommendationAdapter.OnItemClickListener, AdvisoryAdapter.onItemClickListener, FaqAdapter.onItemClickListener, FacilityAdapter.onItemClickListener, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, BottomSheetLocation.onItemClickListener {

    private RecyclerView rv_recommendations, rv_advisory, rv_faqs, rv_rubber_facility, rv_rubber_price_page, rv_weather_forecast;
    private MaterialButton btn_domestic, btn_international, btn_location_facility, btn_location_weather;
    private TextView tv_temp, tv_prec, tv_wind_speed, tv_feels_like, tv_weather, tv_username, tv_version_no;
    private NestedScrollView sv_main;
    private FloatingActionButton fab_chatbot;
    private ExtendedFloatingActionButton fab_testing, fab_ask_expert;
    private RelativeLayout notificationContainer;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;
    private BottomSheetLocation bottomSheetLocation;
    private RecommendationAdapter recommendationAdapter;
    private AdvisoryAdapter advisoryAdapter;
    private FaqAdapter faqAdapter;
    private  FacilityAdapter facilityAdapter;
    private WeatherAdapter weatherAdapter;
    private PricePageAdapter pricePageAdapter;

    private boolean isDomestic = true;
    private boolean isFacilitySelection = true;
    private List<PricePageItem> domesticPages, internationalPages;
    private List<String> locationsList;
    private SnapHelper snapHelper;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        setupToolbar();
        setupDrawer();
        setupNavigationHeader();
        setButtons();
        setupBS();
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
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        tv_version_no = findViewById(R.id.tv_version_no);
        btn_location_facility = findViewById(R.id.btn_location_facility);
        btn_location_weather = findViewById(R.id.btn_location_weather);
        btn_location_facility.setOnClickListener(this);
        btn_location_weather.setOnClickListener(this);
        sv_main = findViewById(R.id.sv_main);
        fab_chatbot = findViewById(R.id.fab_chatbot);
        fab_testing = findViewById(R.id.fab_testing);
        fab_ask_expert = findViewById(R.id.fab_ask_expert);
        sv_main.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > 200){
                    fab_ask_expert.setExtended(false);
                    fab_testing.setExtended(false);
                } else {
                    fab_ask_expert.setExtended(true);
                    fab_testing.setExtended(true);
                }
            }
        });
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
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        notificationContainer.setOnClickListener(this);
    }

    private void setupDrawer() {
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.white));
        toggle.syncState();
        tv_version_no.setText("Version 1.0.0");
    }

    private void setupNavigationHeader() {
        View headerView = navigationView.getHeaderView(0);
        tv_username = headerView.findViewById(R.id.tv_username);
        tv_username.setText("Farhaan");
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

    private void setupBS(){
        locationsList = new ArrayList<>();
        locationsList = Arrays.asList(new String[]{"Kollam", "Trivandrum", "Ernakulam", "Alappuzha", "Idukki", "Palakkad"});
        bottomSheetLocation = new BottomSheetLocation(locationsList);
        bottomSheetLocation.setItemClickListener(this);
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
        } else if (v.getId() == R.id.btn_location_facility) {
            isFacilitySelection = true;
            bottomSheetLocation.show(getSupportFragmentManager(), "Location Bottom Sheet");
        } else if (v.getId() == R.id.btn_location_weather) {
            isFacilitySelection = false;
            bottomSheetLocation.show(getSupportFragmentManager(), "Location Bottom Sheet");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_opt1){
            Toast.makeText(this, "My Plantations clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_opt2) {
            Toast.makeText(this, "Change Password clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_opt3) {
            Toast.makeText(this, "Change Language clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_opt4) {
            handleLogout();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    void handleLogout(){
        SharedPreferences.Editor editor = getSharedPreferences("CRISP", MODE_PRIVATE).edit();
        editor.remove("phone_number");
        editor.remove("password");
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    // Method to close drawer programmatically
    public void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onSheetItemClick(String title, int position) {
        if (isFacilitySelection){
            Toast.makeText(this, title+" facility",Toast.LENGTH_SHORT).show();
            btn_location_facility.setText(title);
        } else {
            Toast.makeText(this, title+" weather",Toast.LENGTH_SHORT).show();
            btn_location_weather.setText(title);
        }
        bottomSheetLocation.dismiss();
    }
}

