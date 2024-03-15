package com.example.orderease.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.orderease.R;
import com.example.orderease.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.widget.TextView;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth= FirebaseAuth.getInstance();



        setVariable();





        TextView textView = findViewById(R.id.textView7);
        textView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        TextView textView2 = findViewById(R.id.textView8);
        textView2.setOnClickListener(v -> {
            Intent intent2 = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent2);
        });






    }

    private void setVariable() {
        binding.loginBtn.setOnClickListener(v -> {
            String email=binding.userEdt.getText().toString();
            String password =binding.passEdt.getText().toString();
            if (!email.isEmpty() && !password.isEmpty()){
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, task -> {
                    if (task.isComplete()){
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else {
                        Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                Toast.makeText(LoginActivity.this,"Please fill username and password",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
