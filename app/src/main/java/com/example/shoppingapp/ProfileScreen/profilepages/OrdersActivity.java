package com.example.shoppingapp.ProfileScreen.profilepages;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.R;

public class OrdersActivity extends AppCompatActivity {

    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        // Initialize views
        backArrow = findViewById(R.id.backArrow);

        // Back button click
        backArrow.setOnClickListener(v -> onBackPressed());
    }
}
