package com.example.orderease.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.orderease.Adapter.FoodListAdapter;
import com.example.orderease.Domain.Favorite;
import com.example.orderease.Domain.Foods;
import com.example.orderease.R;
import com.example.orderease.databinding.ActivityAddItemsBinding;
import com.example.orderease.databinding.ActivityFavoriteBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends BaseActivity {
    private ActivityFavoriteBinding binding;
    private FoodListAdapter adapterListFood;
    ArrayList<Foods> foods = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
    }

    private void setVariable() {
        binding.toBack.setOnClickListener(v -> onBackPressed());
        binding.refreshView.setOnRefreshListener(() -> fetchUserFavorites());
        fetchUserFavorites();
    }

    private void fetchUserFavorites() {
        String currentId = mAuth.getCurrentUser().getUid();
        DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference().child("Favorites");
        binding.progressBar.setVisibility(View.VISIBLE);
        foods.clear();
        favoritesRef.orderByChild("ownerId").equalTo(currentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Favorite favorite = snapshot.getValue(Favorite.class);
                    if (favorite != null) {
                        foods.add(favorite.getFood());
                    }
                }

                if (foods.size() > 0) {

                    binding.favoriteRecycler.setVisibility(View.VISIBLE);
                    binding.emptyTxt2.setVisibility(View.GONE);
                    binding.emptyFavoritesImage.setVisibility(View.GONE);
                    binding.progressBar.setVisibility(View.GONE);
                    binding.favoriteRecycler.setLayoutManager(new GridLayoutManager(FavoriteActivity.this, 2));
                    adapterListFood = new FoodListAdapter(foods);
                    binding.favoriteRecycler.setAdapter(adapterListFood);
                } else {

                    binding.favoriteRecycler.setVisibility(View.GONE);
                    binding.emptyTxt2.setVisibility(View.VISIBLE);
                    binding.emptyFavoritesImage.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.GONE);
                }

                binding.refreshView.setRefreshing(false);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }
}