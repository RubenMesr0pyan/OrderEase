package com.example.orderease.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.orderease.R;
import com.example.orderease.databinding.ActivityPassBinding;

public class PassActivity extends BaseActivity {
    private String passTxt = "0";
    private ActivityPassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(v -> {
            String enteredPassword = binding.BestFoodBtn.getText().toString().trim();
            if (enteredPassword.equals(passTxt)) {
                openAddItemsActivity();
            } else {
                Toast.makeText(PassActivity.this, "Please enter the correct password", Toast.LENGTH_SHORT).show();
            }
        });

        binding.backBtn2.setOnClickListener(v -> {
            Intent intent = new Intent(PassActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void openAddItemsActivity() {
        Intent intent = new Intent(PassActivity.this, AddItemsActivity.class);
        startActivity(intent);
        finish();
    }
}
