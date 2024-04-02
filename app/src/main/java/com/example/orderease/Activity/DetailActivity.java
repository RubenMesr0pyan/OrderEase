package com.example.orderease.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.orderease.Domain.Favorite;
import com.example.orderease.Domain.Foods;
import com.example.orderease.Helper.ManagmentCart;
import com.example.orderease.R;
import com.example.orderease.databinding.ActivityDetailBinding;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends BaseActivity {
    com.example.orderease.databinding.ActivityDetailBinding binding;
    FirebaseAuth mAuth;

    private Foods object;
    private int num = 1 ;
    private ManagmentCart managmentCart;
    boolean isFavorite = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityDetailBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        getIntentExtra();
        setVariable();
        checkIfFavorite();

    }

    private void setVariable() {
        managmentCart= new ManagmentCart(this);
        mAuth = FirebaseAuth.getInstance();

        binding.backBtn.setOnClickListener(v -> finish());
        Glide.with(DetailActivity.this)
                .load(object.getImagePath())
                .into(binding.pic);

        binding.priceTxt.setText("$" + object.getPrice());
        binding.titleTxt.setText(object.getTitle());
        binding.descriptionTxt.setText(object.getDescription());
        binding.rateTxt.setText(object.getStar() + " Rating");
        binding.ratingBar.setRating((float) object.getStar());
        binding.totalTxt.setText((num * object.getPrice() + "$"));

        binding.plusBtn.setOnClickListener(v -> {
            num = num + 1;
            binding.numTxt.setText(num+" ");
            binding.totalTxt.setText("$" + (num * object.getPrice()));
        });
        binding.minusBtn.setOnClickListener(v -> {
            if (num>1){
                num = num - 1;
                binding.numTxt.setText(num+"");
                binding.totalTxt.setText("$" + (num * object.getPrice()));
            }
        });

        binding.addBtn.setOnClickListener(v -> {
            object.setNumberInCart(num);
            managmentCart.insertFood(object);

        });
        binding.favBtn.setOnClickListener(v -> {
            if (isFavorite) {
                binding.favBtn.setImageDrawable(getDrawable(R.drawable.favorite1));
                Toast.makeText(DetailActivity.this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
                removeFromFavorite();
            } else {
                binding.favBtn.setImageDrawable(getDrawable(R.drawable.favorite2));
                Toast.makeText(DetailActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
                addToFavorite();
            }
        });
    }



    void addToFavorite() {
        String currentId = mAuth.getCurrentUser().getUid();
        Favorite favorite = new Favorite(currentId, object);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference favoritesRef = rootRef.child("Favorites");

        String favoriteKey = favoritesRef.push().getKey();


        favoritesRef.child(favoriteKey).setValue(favorite);
        isFavorite = true;
    }


    private void removeFromFavorite() {
        String currentId = mAuth.getCurrentUser().getUid();
        DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference().child("Favorites");

        favoritesRef.orderByChild("ownerId").equalTo(currentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Favorite favorite = snapshot.getValue(Favorite.class);
                    if (favorite != null && favorite.getFood().getId() == object.getId()) {
                        snapshot.getRef().removeValue();
                        Toast.makeText(DetailActivity.this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
                        isFavorite = false;
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void checkIfFavorite() {
        String currentId = mAuth.getCurrentUser().getUid();
        DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference().child("Favorites");

        favoritesRef
                .orderByChild("ownerId")
                .equalTo(currentId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Favorite favorite = snapshot.getValue(Favorite.class);
                    if (favorite != null && favorite.getFood().getId() == object.getId()) {
                        isFavorite = true;
                        binding.favBtn.setImageDrawable(getDrawable(R.drawable.favorite2));
                        return;
                    }
                }
                isFavorite = false;
                binding.favBtn.setImageDrawable(getDrawable(R.drawable.favorite1));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getIntentExtra() {
        object = (Foods) getIntent().getSerializableExtra("object");
    }
}