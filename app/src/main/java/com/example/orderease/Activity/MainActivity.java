package com.example.orderease.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orderease.Adapter.BestFoodsAdapter;
import com.example.orderease.Adapter.CategoryAdapter;
import com.example.orderease.Domain.Category;
import com.example.orderease.Domain.Foods;
import com.example.orderease.Domain.Location;
import com.example.orderease.Domain.Price;
import com.example.orderease.Domain.Time;
import com.example.orderease.R;
import com.example.orderease.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private TextView viewAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageView likedBtn = findViewById(R.id.likedBtn);
        ImageView addItemBtn = findViewById(R.id.AddItemBtn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PassActivity.class));
            }
        });

        likedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FavoriteActivity.class));

            }
        });


        initLocation();
        initTime();
        initPrice();
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
        Query query = myRef.orderByChild("BestFood").equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        list.add(issue.getValue(Foods.class));
                    }
                    if (list.size()>0) {
                        binding.bestFoodView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                        RecyclerView.Adapter adapter=new BestFoodsAdapter(list);
                        binding.bestFoodView.setAdapter(adapter);
                    }
                    binding.progressBarBestFood.setVisibility(View.GONE);
                }
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

    private void initLocation() {
        DatabaseReference myRef=database.getReference("Location");
        ArrayList<Location >list=new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        list.add(issue.getValue(Location.class));
                    }
                    ArrayAdapter<Location> adapter=new ArrayAdapter<>(MainActivity.this,R.layout.sp_item,list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.locationSp.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initTime() {
        DatabaseReference myRef=database.getReference("Time");
        ArrayList<Time>list=new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        list.add(issue.getValue(Time.class));
                    }
                    ArrayAdapter<Time> adapter=new ArrayAdapter<>(MainActivity.this,R.layout.sp_item,list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.timeSp.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initPrice() {
        DatabaseReference myRef=database.getReference("Price");
        ArrayList<Price>list=new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        list.add(issue.getValue(Price.class));
                    }
                    ArrayAdapter<Price> adapter=new ArrayAdapter<>(MainActivity.this,R.layout.sp_item,list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.priceSp.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}