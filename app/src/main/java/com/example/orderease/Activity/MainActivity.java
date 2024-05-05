package com.example.orderease.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orderease.Adapter.BestFoodsAdapter;
import com.example.orderease.Adapter.CategoryAdapter;
import com.example.orderease.Domain.Category;
import com.example.orderease.Domain.Foods;
import com.example.orderease.R;
import com.example.orderease.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private TextView viewAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageView addItemBtn = findViewById(R.id.addItemBtn);
        ImageView likedBtn = findViewById(R.id.likedBtn);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            if (email != null && email.equals("rubenmesropyan307@gmail.com")) {
                addItemBtn.setVisibility(View.VISIBLE);
                likedBtn.setVisibility(View.GONE);
                addItemBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddItemsActivity.class)));
            } else {
                addItemBtn.setVisibility(View.GONE);
                likedBtn.setVisibility(View.VISIBLE);
            }
        } else {
            addItemBtn.setVisibility(View.GONE);
            likedBtn.setVisibility(View.VISIBLE);
        }

        likedBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FavoriteActivity.class)));

        initBestFood();
        initCategory();
        setVaribale();
    }


    private void setVaribale() {
        binding.logoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        });
        binding.searchBtn.setOnClickListener(v -> {
            String text = binding.searchEdt.getText().toString();
            if (!text.isEmpty()){
                Intent intent  = new Intent(MainActivity.this,ListFoodActivity.class);
                intent.putExtra("text",text);
                intent.putExtra("isSearch",true);
                startActivity(intent);
            }
        });
        binding.cartBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,CartActivity.class)));
    }

    private void initBestFood() {
        DatabaseReference myRef = database.getReference("Foods");
        binding.progressBarBestFood.setVisibility(View.VISIBLE);
        ArrayList<Foods> list = new ArrayList<>();

        String searchText = binding.searchEdt.getText().toString().trim().toLowerCase();

        myRef.orderByChild("BestFood").equalTo(true)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Foods food = dataSnapshot.getValue(Foods.class);
                                if (food != null && food.getTitle().toLowerCase().contains(searchText)) {
                                    list.add(food);
                                }
                            }
                            if (!list.isEmpty()) {
                                binding.bestFoodView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                                RecyclerView.Adapter adapter=new BestFoodsAdapter(list);
                                binding.bestFoodView.setAdapter(adapter);
                            }
                        }
                        binding.progressBarBestFood.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }


    private void initCategory() {
        DatabaseReference myRef = database.getReference("Category");
        binding.progressBarCategory.setVisibility(View.VISIBLE);
        ArrayList<Category> list = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        list.add(issue.getValue(Category.class));
                    }
                    if (list.size()>0) {
                        binding.categoryView.setLayoutManager(new GridLayoutManager(MainActivity.this,4));
                        RecyclerView.Adapter adapter=new CategoryAdapter(list);
                        binding.categoryView.setAdapter(adapter);
                    }
                    binding.progressBarCategory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}