package com.example.orderease.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.orderease.R;
import com.example.orderease.databinding.ActivityIntroBinding;
import com.google.firebase.auth.FirebaseAuth;

public class IntroActivity extends BaseActivity {
    ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth= FirebaseAuth.getInstance();
        setVariable();
        getWindow().setStatusBarColor(Color.parseColor("#FFE4B5"));
    }

    private void setVariable() {
        binding.loginBtn.setOnClickListener(v -> {
if (mAuth.getCurrentUser()!=null){
startActivity(new Intent(IntroActivity.this, MainActivity.class));

}else {
startActivity(new Intent(IntroActivity.this, LoginActivity.class));

}
        });

        binding.signupBtn.setOnClickListener(v -> startActivity(new Intent(IntroActivity.this, SignupActivity.class)));
    }
}