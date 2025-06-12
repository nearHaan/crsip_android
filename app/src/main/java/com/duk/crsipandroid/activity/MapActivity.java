package com.duk.crsipandroid.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.duk.crsipandroid.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private double lat = 0.0;
    private double lon = 0.0;

    public static final String EXTRA_LATITUDE = "extra_latitude";
    public static final String EXTRA_LONGITUDE = "extra_longitude";
    public static final String EXTRA_TITLE = "extra_title";

    private String markerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Get coordinates from intent extras
        if (getIntent().getExtras() != null) {
            lat = getIntent().getDoubleExtra(EXTRA_LATITUDE, lat);
            lon = getIntent().getDoubleExtra(EXTRA_LONGITUDE, lon);
            markerTitle = getIntent().getStringExtra(EXTRA_TITLE);
            if (markerTitle == null) markerTitle = "Location";
        }

        // Get a handle to the fragment and register the callback
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("Map", "Ready");
        LatLng location = new LatLng(lat, lon);
        googleMap.addMarker(new MarkerOptions()
                .position(location)
                .title(markerTitle)
                .snippet("Sample text"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f));
    }
}