package com.example.orderease.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.orderease.R;
import com.example.orderease.databinding.ActivityAddItemsBinding;
import android.content.Intent;

public class AddItemsActivity extends BaseActivity {

    private ActivityAddItemsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
    }

    private void setVariable() {
        binding.backBtn2.setOnClickListener(v -> {
            Intent intent = new Intent(AddItemsActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

}
