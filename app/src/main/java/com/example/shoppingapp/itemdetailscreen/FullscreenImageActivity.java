package com.example.shoppingapp.itemdetailscreen;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shoppingapp.R;
import com.example.shoppingapp.imageslider.ImageSliderAdapter;

import java.util.ArrayList;

public class FullscreenImageActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ImageView btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fullscreen (no edge padding needed here)
        setContentView(R.layout.activity_fullscreen_image);

        viewPager = findViewById(R.id.viewPager);
        btnClose = findViewById(R.id.btnClose);

        // 🔹 Get images + clicked position
        ArrayList<Integer> images =
                getIntent().getIntegerArrayListExtra("images");

        int position = getIntent().getIntExtra("position", 0);

        if (images != null && !images.isEmpty()) {
            ImageSliderAdapter adapter =
                    new ImageSliderAdapter(images, null);

            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(position, false);
        }

        // 🔹 Close button
        btnClose.setOnClickListener(v -> finish());
    }
}
