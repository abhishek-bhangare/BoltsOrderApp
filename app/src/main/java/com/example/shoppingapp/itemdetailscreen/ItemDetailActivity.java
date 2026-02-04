package com.example.shoppingapp.itemdetailscreen;

import android.graphics.Paint;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shoppingapp.R;
import com.example.shoppingapp.imageslider.ImageSliderView;

import java.util.ArrayList;

public class ItemDetailActivity extends AppCompatActivity {

    ImageSliderView imageSlider;

    TextView tvPartName, tvCategory, tvPartNo, tvMrp, tvSaleRate, tvUnitStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 🔹 Init Views
        imageSlider = findViewById(R.id.imageSlider);

        tvPartName = findViewById(R.id.tvPartName);
        tvCategory = findViewById(R.id.tvCategory);
        tvPartNo = findViewById(R.id.tvPartNo);
        tvMrp = findViewById(R.id.tvMrp);
        tvSaleRate = findViewById(R.id.tvSaleRate);
        tvUnitStock = findViewById(R.id.tvUnitStock);

        // 🔹 Get Intent Data
        Intent intent = getIntent();

        if (intent != null) {

            tvPartName.setText(intent.getStringExtra("part_name"));

            tvCategory.setText(
                    intent.getStringExtra("maincategory_name")
                            + " • "
                            + intent.getStringExtra("subcategory_name")
            );

            tvPartNo.setText("Part No: " + intent.getStringExtra("part_no"));
            tvMrp.setText("MRP ₹" + intent.getStringExtra("mrp"));
            tvMrp.setPaintFlags(tvMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvSaleRate.setText("₹" + intent.getStringExtra("sale_rate"));

            tvUnitStock.setText(
                    "Unit: " + intent.getStringExtra("unit_nm")
                            + " | Stock: " + intent.getStringExtra("avl_stock")
            );

            // 🔹 Set images to reusable slider
//            ArrayList<String> images =
//                    intent.getStringArrayListExtra("images");
//
//            if (images != null && !images.isEmpty()) {
//                imageSlider.setImages(images);
//            }
            // 🔹 Set images to reusable slider (drawable images)
            ArrayList<Integer> images =
                    intent.getIntegerArrayListExtra("images");

            if (images != null && !images.isEmpty()) {
                imageSlider.setImages(images);
            }
        }
    }

    // 🔹 ADD THIS (BELOW onCreate)
    @Override
    protected void onResume() {
        super.onResume();
        if (imageSlider != null) {
            imageSlider.startAutoScrollPublic();
        }
    }

    // 🔹 ALREADY ADDED
    @Override
    protected void onPause() {
        super.onPause();
        if (imageSlider != null) {
            imageSlider.stopAutoScroll();
        }
    }
}
