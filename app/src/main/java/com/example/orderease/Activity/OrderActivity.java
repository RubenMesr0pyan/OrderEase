package com.example.orderease.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.EditText;
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

import java.util.Locale;
import android.location.Address;
import java.util.List;
import java.io.IOException;


public class OrderActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {
    private ActivityOrderBinding binding;
    private GoogleMap mMap;
    private Marker marker;

    private String markerTitle = "Order Address";

    private EditText addressEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        addressEditText = findViewById(R.id.addresfrommap);

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

        showMarkerWithInfoWindow(erevan, markerTitle);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                marker.setPosition(latLng);
                marker.setTitle(markerTitle);
                marker.showInfoWindow();

                Geocoder geocoder = new Geocoder(OrderActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (!addresses.isEmpty()) {
                        Address address = addresses.get(0);
                        String addressLine = address.getAddressLine(0);
                        addressEditText.setText(addressLine);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showMarkerWithInfoWindow(LatLng latLng, String title) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(title)
                .draggable(true);
        marker = mMap.addMarker(markerOptions);
        marker.showInfoWindow();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        marker.hideInfoWindow();
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        LatLng newPosition = marker.getPosition();
        marker.setPosition(newPosition);
        marker.setTitle(markerTitle);
        marker.showInfoWindow();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(newPosition.latitude, newPosition.longitude, 1);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                String addressLine = address.getAddressLine(0);
                addressEditText.setText(addressLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng newPosition = marker.getPosition();
        marker.setPosition(newPosition);
        marker.setTitle(markerTitle);
        marker.showInfoWindow();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(newPosition.latitude, newPosition.longitude, 1);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                String addressLine = address.getAddressLine(0);
                addressEditText.setText(addressLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}