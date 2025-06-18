package com.duk.crsipandroid.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.duk.crsipandroid.BuildConfig;
import com.duk.crsipandroid.R;
import com.duk.crsipandroid.adapters.AdvisoryAdapter;
import com.duk.crsipandroid.adapters.FacilityAdapter;
import com.duk.crsipandroid.adapters.FaqAdapter;
import com.duk.crsipandroid.adapters.PricePageAdapter;
import com.duk.crsipandroid.adapters.RecommendationAdapter;
import com.duk.crsipandroid.adapters.WeatherAdapter;
import com.duk.crsipandroid.api.PriceApiService;
import com.duk.crsipandroid.api.WeatherApiService;
import com.duk.crsipandroid.mvp.AdvisoryItem;
import com.duk.crsipandroid.mvp.FaqItem;
import com.duk.crsipandroid.mvp.LocationLatLong;
import com.duk.crsipandroid.mvp.PriceResponse;
import com.duk.crsipandroid.mvp.RecommendationItem;
import com.duk.crsipandroid.mvp.RubberFacility;
import com.duk.crsipandroid.mvp.WeatherForeCast;
import com.duk.crsipandroid.mvp.WeatherItem;
import com.duk.crsipandroid.mvp.WeatherResponse;
import com.duk.crsipandroid.network.RetrofitClientPrice;
import com.duk.crsipandroid.network.RetrofitClientWeather;
import com.duk.crsipandroid.utils.BottomSheetLocation;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity implements RecommendationAdapter.OnItemClickListener, AdvisoryAdapter.onItemClickListener, FaqAdapter.onItemClickListener, FacilityAdapter.onItemClickListener, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, BottomSheetLocation.onItemClickListener {

    private RecyclerView rv_recommendations, rv_advisory, rv_faqs, rv_rubber_facility, rv_rubber_price_page, rv_weather_forecast;
    private MaterialButton btn_domestic, btn_international, btn_location_facility, btn_location_weather;
    private TextView tv_temp, tv_prec, tv_wind_speed, tv_feels_like, tv_weather, tv_username, tv_version_no;
    private ImageView iv_weather;
    private NestedScrollView sv_main;
    private FloatingActionButton fab_chatbot;
    private ExtendedFloatingActionButton fab_testing, fab_ask_expert;
    private RelativeLayout notificationContainer;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;
    private LinearLayout ll_rubber_price, ll_price_loading, ll_weather_box;
    private BottomSheetLocation bottomSheetLocation;
    private RecommendationAdapter recommendationAdapter;
    private AdvisoryAdapter advisoryAdapter;
    private FaqAdapter faqAdapter;
    private FacilityAdapter facilityAdapter;
    private WeatherAdapter weatherAdapter;
    private PricePageAdapter pricePageAdapter;

    private boolean isDomestic = true;
    private boolean isFacilitySelection = true;
    private List<PriceResponse> domesticPages, internationalPages;
    private List<LocationLatLong> locationsList;
    private SnapHelper snapHelper;
    private RecyclerView.LayoutManager layoutManager;
    private final String ICON_BASE_URL = "https://openweathermap.org/img/wn/";
    private final String ICON_URL_SUFFIX = "@2x.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_home);

        fetchPrices("indian");
        initViews();
        setupToolbar();
        setupDrawer();
        setupNavigationHeader();
        setButtons();
        setupBS();
        setupRecyclerViews();
        fetchPrices(isDomestic?"indian":"international");
        fetchWeather("8.615891", "76.852488");
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
        iv_weather = findViewById(R.id.iv_weather);
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
        ll_rubber_price = findViewById(R.id.ll_rubber_price);
        ll_price_loading = findViewById(R.id.ll_price_loading);
        ll_weather_box = findViewById(R.id.ll_weather_box);
        sv_main = findViewById(R.id.sv_main);
        fab_chatbot = findViewById(R.id.fab_chatbot);
        fab_testing = findViewById(R.id.fab_testing);
        fab_ask_expert = findViewById(R.id.fab_ask_expert);
        fab_chatbot.setOnClickListener(this);
        fab_testing.setOnClickListener(this);
        fab_ask_expert.setOnClickListener(this);
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
        fetchPrices(isDomestic?"indian":"international");
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
    }

    private void setupBS(){
        locationsList = new ArrayList<>();
        locationsList.add(new LocationLatLong("Thiruvananthapuram", 8.5000, 76.9167));
        locationsList.add(new LocationLatLong("Kochi", 9.9667, 76.2833));
        locationsList.add(new LocationLatLong("Kozhikode", 11.2500, 75.7800));
        locationsList.add(new LocationLatLong("Kollam", 8.8833, 76.6000));
        locationsList.add(new LocationLatLong("Thrissur", 10.5167, 76.2167));
        locationsList.add(new LocationLatLong("Alappuzha", 9.4833, 76.3333));
        locationsList.add(new LocationLatLong("Kottayam", 9.6000, 76.5667));
        locationsList.add(new LocationLatLong("Palakkad", 10.7667, 76.6500));
        locationsList.add(new LocationLatLong("Kannur", 11.8667, 75.3667));
        locationsList.add(new LocationLatLong("Kasaragod", 12.5000, 75.0000));
        bottomSheetLocation = new BottomSheetLocation(locationsList);
        bottomSheetLocation.setItemClickListener(this);
    }

    private String convertSpeed(double speed){
        double speedKMperH = 3.6*speed;
        String newSpeed = new DecimalFormat("#.#").format(speedKMperH);
        return "Wind: "+newSpeed+" km/hr";
    }

    private void setCurrentWether(WeatherItem weatherItem){
        tv_temp.setText(new DecimalFormat("#").format(weatherItem.getMain().getTemp())+"°C");
        tv_prec.setText(new DecimalFormat("#").format(weatherItem.getMain().getHumidity())+"%");
        tv_wind_speed.setText(convertSpeed(weatherItem.getWind().getSpeed()));
        tv_feels_like.setText("Feels like "+new DecimalFormat("#").format(weatherItem.getMain().getFeelsLike())+"°C");
        String icon_url = ICON_BASE_URL+weatherItem.getWeather().get(0).getIcon()+ICON_URL_SUFFIX;
        Glide.with(this)
                .load(icon_url)
                .placeholder(R.drawable.ic_weather_loading)
                .error(R.drawable.ic_weather_error)
                .into(iv_weather);
        tv_weather.setText(weatherItem.getWeather().get(0).getMain().toUpperCase());
    }

    private void fetchPrices(String type) {
        PriceApiService priceApiService = RetrofitClientPrice.getApiServie();
        retrofit2.Call<List<PriceResponse>> call = priceApiService.getPrices("price", type);

        call.enqueue(new Callback<List<PriceResponse>>() {
            @Override
            public void onResponse(retrofit2.Call<List<PriceResponse>> call, Response<List<PriceResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pricePageAdapter = new PricePageAdapter(response.body());
                    rv_rubber_price_page.setAdapter(pricePageAdapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false);
                    rv_rubber_price_page.setLayoutManager(layoutManager);
                    ll_price_loading.setVisibility(View.GONE);
                    rv_rubber_price_page.setVisibility(View.VISIBLE);
                    ll_rubber_price.setVisibility(View.VISIBLE);
                    if (rv_rubber_price_page.getOnFlingListener() == null) {
                        snapHelper = new LinearSnapHelper();
                        snapHelper.attachToRecyclerView(rv_rubber_price_page);
                    }
                } else {
                    ll_rubber_price.setVisibility(View.GONE);
                    Log.e("API_RESULT", "Empty or Error: " + response.code());
                    Toast.makeText(Home.this, "Error occurred. Check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<PriceResponse>> call, Throwable t) {
                ll_rubber_price.setVisibility(View.GONE);
                Log.e("API_RESULT", "Failed: " + t.getMessage());
                Toast.makeText(Home.this, "Error occurred. Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchWeather(String lat, String lon){
        String API_KEY = BuildConfig.WEATHER_API_KEY;
        WeatherApiService weatherApiService = RetrofitClientWeather.getApiServie();
        retrofit2.Call<WeatherResponse> call = weatherApiService.getWeatherResponse(lat, lon, API_KEY, "metric");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    setCurrentWether(response.body().getList().get(0));
                    weatherAdapter = new WeatherAdapter(response.body().getList());
                    rv_weather_forecast.setAdapter(weatherAdapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false);
                    rv_weather_forecast.setLayoutManager(layoutManager);
                    ll_weather_box.setVisibility(View.VISIBLE);
                } else {
                    Log.e("API_RESULT", "Empty or Error: " + response.code());
                    ll_weather_box.setVisibility(View.GONE);
                    Toast.makeText(Home.this, "Error occurred. Check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e("API_RESULT", "Failed: " + t.getMessage());
                ll_weather_box.setVisibility(View.GONE);
                Toast.makeText(Home.this, "Error occurred. Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
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
        if(v.getId() == R.id.btn_domestic && !isDomestic){
            isDomestic = true;
            ll_price_loading.setVisibility(View.VISIBLE);
            rv_rubber_price_page.setVisibility(View.GONE);
            setButtons();
        } else if (v.getId() == R.id.btn_international && isDomestic){
            isDomestic = false;
            ll_price_loading.setVisibility(View.VISIBLE);
            rv_rubber_price_page.setVisibility(View.GONE);
            setButtons();
        } else if (v.getId() == R.id.notification_container) {
            Toast.makeText(this, "Notifications clicked", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.btn_location_facility) {
            isFacilitySelection = true;
            bottomSheetLocation.show(getSupportFragmentManager(), "Location Bottom Sheet");
        } else if (v.getId() == R.id.btn_location_weather) {
            isFacilitySelection = false;
            bottomSheetLocation.show(getSupportFragmentManager(), "Location Bottom Sheet");
        } else if (v.getId() == R.id.fab_ask_expert) {
            Intent intent = new Intent(this, AskAnExpertActivity.class);
            startActivity(intent);
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

    @Override
    public void onSheetItemClick(LocationLatLong item, int position) {
        if (isFacilitySelection){
            Toast.makeText(this, item.getLocation()+" facility",Toast.LENGTH_SHORT).show();
            btn_location_facility.setText(item.getLocation());
        } else {
            Toast.makeText(this, item.getLocation()+" weather",Toast.LENGTH_SHORT).show();
            btn_location_weather.setText(item.getLocation());
            fetchWeather(String.valueOf(item.getLatitude()), String.valueOf(item.getLongitude()));
        }
        bottomSheetLocation.dismiss();
    }
}

