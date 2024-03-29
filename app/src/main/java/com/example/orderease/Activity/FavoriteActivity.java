package com.example.orderease.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.orderease.R;
import com.example.orderease.databinding.ActivityAddItemsBinding;
import com.example.orderease.databinding.ActivityFavoriteBinding;

public class FavoriteActivity extends BaseActivity {
    private ActivityFavoriteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
    }

    private void setVariable() {
        binding.backBtn5.setOnClickListener(v -> {
            Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}