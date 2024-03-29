package com.example.orderease.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.orderease.R;
import com.example.orderease.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.widget.TextView;

public class SignupActivity extends BaseActivity {
    ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        setVariable();


        TextView textView5 = findViewById(R.id.textView5);
        textView5.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });

    }

    private void setVariable() {
        binding.signupBtn.setOnClickListener(v -> {
            String email = binding.userEdit.getText().toString();
            String password = binding.passEdit.getText().toString();

            if (password.length() < 6) {
                Toast.makeText(SignupActivity.this, "Your password must be 6 character", Toast.LENGTH_LONG).show();
                return;
            }
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, task -> {
                if (task.isComplete()) {

                    Log.i(TAG, "onComplete: ");
                    FirebaseUser user = mAuth.getCurrentUser();
                    sendVerificationEmail(user);
                } else {
                    Log.i(TAG, "failure: " + task.getException());
                    Toast.makeText(SignupActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                }

            });
        });
    }


    private void sendVerificationEmail(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "We send verification email", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(this, "Failed to send verification email. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}