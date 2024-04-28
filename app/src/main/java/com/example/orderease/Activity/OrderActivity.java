package com.example.orderease.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import com.example.orderease.R;
import com.example.orderease.databinding.ActivityAddItemsBinding;
import com.example.orderease.databinding.ActivityOrderBinding;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.GoogleMap;

public class OrderActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {
    private ActivityOrderBinding binding;
    private GoogleMap mMap;
    private Marker marker;

    private String markerTitle = "Order Address";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setVariable();
    }

    private void setVariable() {
        binding.backBtn2.setOnClickListener(v -> {
            Intent intent = new Intent(OrderActivity.this, MainActivity.class);
            startActivity(intent);
        });

        binding.button.setOnClickListener(v -> {
            Intent intent = new Intent(OrderActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(OrderActivity.this, "Your order has been sent. We will contact you shortly.", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng erevan = new LatLng(40.1772, 44.5035);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(erevan, 12));

        MarkerOptions markerOptions = new MarkerOptions()
                .position(erevan)
                .title(markerTitle)
                .draggable(true);
        marker = mMap.addMarker(markerOptions);
        marker.showInfoWindow();


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                marker.setPosition(latLng);
                marker.setTitle(markerTitle);
                marker.showInfoWindow();
            }
        });
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDrag(Marker marker) {
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

}