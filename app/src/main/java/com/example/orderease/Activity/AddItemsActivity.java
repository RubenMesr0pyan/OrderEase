package com.example.orderease.Activity;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.example.orderease.Adapter.BestFoodsAdapter;
import com.example.orderease.Domain.Foods;
import com.example.orderease.R;
import com.example.orderease.databinding.ActivityAddItemsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.content.Intent;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddItemsActivity extends BaseActivity {

    private ActivityAddItemsBinding binding;
    private ImageView addImg;
    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Uri imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addImg = binding.addImg;

        setVariable();
        addImg.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddItemsActivity.this);

            builder.setTitle("Select image source");
            builder.setItems(new CharSequence[]{"Gallery", "Camera"}, (dialog, which) -> {
                switch (which) {
                    case 0:
                        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent, PICK_IMAGE);
                        break;
                    case 1:
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                        }
                        break;
                }
            });
            builder.show();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_IMAGE:
                    if (data != null && data.getData() != null) {
                        imagePath = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                            addImg.setImageBitmap(bitmap);
                            findViewById(R.id.placeholderText).setVisibility(View.GONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        addImg.setImageBitmap(imageBitmap);
                        findViewById(R.id.placeholderText).setVisibility(View.GONE);
                    }
                    break;
            }
        }
    }


    private void setVariable() {
        binding.backBtn2.setOnClickListener(v -> {
            Intent intent = new Intent(AddItemsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        binding.button.setOnClickListener(v -> {
            submitItem();
        });
    }

    private void submitItem() {
        String title = binding.Title.getText().toString();
        String description = binding.Description.getText().toString();
        String priceStr = binding.Price.getText().toString();
        String starValueStr = binding.Star.getText().toString();
        String timeValueStr = binding.TimeValue.getText().toString();
        String idStr = binding.Id.getText().toString();
        String categoryIdStr = binding.CategoryId.getText().toString();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) || TextUtils.isEmpty(priceStr) ||
                TextUtils.isEmpty(starValueStr) || TextUtils.isEmpty(timeValueStr) ||
                TextUtils.isEmpty(idStr) || TextUtils.isEmpty(categoryIdStr)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        double starValue = Double.parseDouble(starValueStr);
        int timeValue = Integer.parseInt(timeValueStr);
        int id = Integer.parseInt(idStr);
        int categoryId = Integer.parseInt(categoryIdStr);

        Foods foodToAdd = new Foods();

        foodToAdd.setTitle(title);
        foodToAdd.setDescription(description);
        foodToAdd.setPrice(price);
        foodToAdd.setStar(starValue);
        foodToAdd.setTimeValue(timeValue);
        foodToAdd.setId(id);
        foodToAdd.setCategoryId(categoryId);
        foodToAdd.setBestFood(binding.isBestCB.isChecked());

        uploadToStorage(foodToAdd);
    }


    private void uploadToRealtimeDb(Foods food) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference foodsRef = database.getReference("Foods");

        String foodId = String.valueOf(food.getId());

        Map<String, Object> updatedValues = new HashMap<>();
        updatedValues.put("CategoryId", food.getCategoryId());
        updatedValues.put("Description", food.getDescription());
        updatedValues.put("BestFood", food.isBestFood());
        updatedValues.put("Id", food.getId());
        updatedValues.put("LocationId", food.getLocationId());
        updatedValues.put("Price", food.getPrice());
        updatedValues.put("ImagePath", food.getImagePath());
        updatedValues.put("PriceId", food.getPriceId());
        updatedValues.put("Star", food.getStar());
        updatedValues.put("TimeId", food.getTimeId());
        updatedValues.put("TimeValue", food.getTimeValue());
        updatedValues.put("Title", food.getTitle());
        updatedValues.put("numberInCart", food.getNumberInCart());


        foodsRef.child(foodId).setValue(updatedValues, (error, ref) -> {
            if (error != null) {
                System.out.println("Data could not be saved. " + error.getMessage());
                binding.progressBar2.setVisibility(View.GONE);
                binding.linearLayout.setVisibility(View.VISIBLE);
            } else {
                System.out.println("Data saved successfully.");
                Intent i = new Intent(AddItemsActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void uploadToStorage(Foods food) {
        binding.progressBar2.setVisibility(View.VISIBLE);
        binding.linearLayout.setVisibility(View.INVISIBLE);
        StorageReference storageReference;
        storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child("foods/" + food.getId()).putFile(imagePath)
                .addOnSuccessListener(taskSnapshot -> storageReference.child("foods/" + food.getId()).getDownloadUrl()
                        .addOnSuccessListener(uri -> {
                            food.setImagePath(uri.toString());
                            uploadToRealtimeDb(food);
                        }))
                .addOnFailureListener(e -> {});
    }
}
