//package com.example.shoppingapp.itemdetailscreen;
//
//import android.graphics.Paint;
//import android.os.Bundle;
//import android.content.Intent;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.shoppingapp.R;
//import com.example.shoppingapp.imageslider.ImageSliderView;
//
//import java.util.ArrayList;
//
//public class ItemDetailActivity extends AppCompatActivity {
//
//    ImageSliderView imageSlider;
//
//    TextView tvPartName, tvCategory, tvPartNo, tvMrp, tvSaleRate, tvUnitStock;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_item_detail);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        // 🔹 Init Views
//        imageSlider = findViewById(R.id.imageSlider);
//
//        tvPartName = findViewById(R.id.tvPartName);
//        tvCategory = findViewById(R.id.tvCategory);
//        tvPartNo = findViewById(R.id.tvPartNo);
//        tvMrp = findViewById(R.id.tvMrp);
//        tvSaleRate = findViewById(R.id.tvSaleRate);
//        tvUnitStock = findViewById(R.id.tvUnitStock);
//
//        // 🔹 Get Intent Data
//        Intent intent = getIntent();
//
//        if (intent != null) {
//
//            tvPartName.setText(intent.getStringExtra("part_name"));
//
//            tvCategory.setText(
//                    intent.getStringExtra("maincategory_name")
//                            + " • "
//                            + intent.getStringExtra("subcategory_name")
//            );
//
//            tvPartNo.setText("Part No: " + intent.getStringExtra("part_no"));
//            tvMrp.setText("MRP ₹" + intent.getStringExtra("mrp"));
//            tvMrp.setPaintFlags(tvMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//            tvSaleRate.setText("₹" + intent.getStringExtra("sale_rate"));
//
//            tvUnitStock.setText(
//                    "Unit: " + intent.getStringExtra("unit_nm")
//                            + " | Stock: " + intent.getStringExtra("avl_stock")
//            );
//
//            // 🔹 Set images to reusable slider
////            ArrayList<String> images =
////                    intent.getStringArrayListExtra("images");
////
////            if (images != null && !images.isEmpty()) {
////                imageSlider.setImages(images);
////            }
//            // 🔹 Set images to reusable slider (drawable images)
//            ArrayList<Integer> images =
//                    intent.getIntegerArrayListExtra("images");
//
//            if (images != null && !images.isEmpty()) {
//                imageSlider.setImages(images);
//            }
//        }
//    }
//
//    // 🔹 ADD THIS (BELOW onCreate)
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (imageSlider != null) {
//            imageSlider.startAutoScrollPublic();
//        }
//    }
//
//    // 🔹 ALREADY ADDED
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (imageSlider != null) {
//            imageSlider.stopAutoScroll();
//        }
//    }
//}
//
//package com.example.shoppingapp.itemdetailscreen;
//
//import android.content.Intent;
//import android.graphics.Paint;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.shoppingapp.R;
//import com.example.shoppingapp.imageslider.ImageSliderView;
//
//import java.util.ArrayList;
//
//public class ItemDetailActivity extends AppCompatActivity {
//
//    private static final String TAG = "ItemDetailActivity";
//
//    private ImageSliderView imageSlider;
//
//    private TextView tvPartName, tvCategory, tvPartNo,
//            tvMrp, tvSaleRate, tvUnitStock;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_item_detail);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top,
//                    systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        // =====================================================
//        // 🔹 INIT VIEWS
//        // =====================================================
//        imageSlider = findViewById(R.id.imageSlider);
//
//        tvPartName = findViewById(R.id.tvPartName);
//        tvCategory = findViewById(R.id.tvCategory);
//        tvPartNo = findViewById(R.id.tvPartNo);
//        tvMrp = findViewById(R.id.tvMrp);
//        tvSaleRate = findViewById(R.id.tvSaleRate);
//        tvUnitStock = findViewById(R.id.tvUnitStock);
//
//        // =====================================================
//        // 🔹 GET INTENT DATA (NULL SAFE)
//        // =====================================================
//        Intent intent = getIntent();
//
//        if (intent == null) {
//            Log.e(TAG, "Intent is NULL");
//            showFallbackImage();
//            return;
//        }
//
//        String partName = intent.getStringExtra("part_name");
//        String mainCat  = intent.getStringExtra("maincategory_name");
//        String subCat   = intent.getStringExtra("subcategory_name");
//        String partNo   = intent.getStringExtra("part_no");
//        String mrp      = intent.getStringExtra("mrp");
//        String saleRate = intent.getStringExtra("sale_rate");
//        String unitNm   = intent.getStringExtra("unit_nm");
//        String stock    = intent.getStringExtra("avl_stock");
//
//        Log.d(TAG, "Received Item: " + safe(partName));
//        Log.d(TAG, "Category: " + safe(mainCat) + " • " + safe(subCat));
//
//        // =====================================================
//        // 🔹 SET TEXT DATA (SAFE)
//        // =====================================================
//        tvPartName.setText(safe(partName));
//        tvCategory.setText(safe(mainCat) + " • " + safe(subCat));
//        tvPartNo.setText("Part No: " + safe(partNo));
//
//        tvMrp.setText("MRP ₹" + safe(mrp));
//        tvMrp.setPaintFlags(tvMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//        tvSaleRate.setText("₹" + safe(saleRate));
//
//        tvUnitStock.setText(
//                "Unit: " + safe(unitNm) + " | Stock: " + safe(stock)
//        );
//
//        // =====================================================
//        // 🔹 IMAGE HANDLING (API → FALLBACK)
//        // =====================================================
//        ArrayList<String> images =
//                intent.getStringArrayListExtra("images");
//
//        if (images != null && !images.isEmpty()) {
//            Log.d(TAG, "API image list size = " + images.size());
//            imageSlider.setImageUrls(images);   // ✅ CORRECT METHOD
//        } else {
//            Log.e(TAG, "No API images found, loading fallback drawable");
//            showFallbackImage();
//        }
//    }
//
//    // =====================================================
//    // 🔹 FALLBACK IMAGE HANDLER
//    // =====================================================
//    private void showFallbackImage() {
//        ArrayList<Integer> fallbackImages = new ArrayList<>();
//        fallbackImages.add(R.drawable.nutbolt);
//
//        imageSlider.setDrawableImages(fallbackImages); // ✅ CORRECT METHOD
//    }
//
//    // =====================================================
//    // 🔹 NULL SAFETY
//    // =====================================================
//    private String safe(String value) {
//        return (value != null && !value.trim().isEmpty())
//                ? value : "N/A";
//    }
//
//    // =====================================================
//    // 🔹 SLIDER LIFECYCLE
//    // =====================================================
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (imageSlider != null) {
//            imageSlider.startAutoScrollPublic();
//            Log.d(TAG, "Slider auto-scroll started");
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (imageSlider != null) {
//            imageSlider.stopAutoScroll();
//            Log.d(TAG, "Slider auto-scroll stopped");
//        }
//    }
//}
//

package com.example.shoppingapp.itemdetailscreen;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shoppingapp.R;
import com.example.shoppingapp.imageslider.ImageSliderView;
import com.example.shoppingapp.CartScreen.CartModel;
import com.example.shoppingapp.utils.CartStorage;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class ItemDetailActivity extends AppCompatActivity {

    private static final String TAG = "ItemDetailActivity";

    private ImageSliderView imageSlider;

    private TextView tvPartName, tvCategory, tvPartNo,
            tvMrp, tvSaleRate, tvUnitStock;

    // 🔹 NEW: ADD TO CART BUTTON
    private MaterialButton btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top,
                    systemBars.right, systemBars.bottom);
            return insets;
        });

        // =====================================================
        // 🔹 INIT VIEWS
        // =====================================================
        imageSlider = findViewById(R.id.imageSlider);

        tvPartName = findViewById(R.id.tvPartName);
        tvCategory = findViewById(R.id.tvCategory);
        tvPartNo = findViewById(R.id.tvPartNo);
        tvMrp = findViewById(R.id.tvMrp);
        tvSaleRate = findViewById(R.id.tvSaleRate);
        tvUnitStock = findViewById(R.id.tvUnitStock);

        // 🔹 NEW
        btnAddToCart = findViewById(R.id.btnAddToCart);

        // =====================================================
        // 🔹 GET INTENT DATA (NULL SAFE)
        // =====================================================
        Intent intent = getIntent();

        if (intent == null) {
            Log.e(TAG, "Intent is NULL");
            showFallbackImage();
            return;
        }

        String itemId   = intent.getStringExtra("item_id");
        String partName = intent.getStringExtra("part_name");
        String mainCat  = intent.getStringExtra("maincategory_name");
        String subCat   = intent.getStringExtra("subcategory_name");
        String partNo   = intent.getStringExtra("part_no");
        String mrp      = intent.getStringExtra("mrp");
        String saleRate = intent.getStringExtra("sale_rate");
        String unitNm   = intent.getStringExtra("unit_nm");
        String stock    = intent.getStringExtra("avl_stock");

        ArrayList<String> images =
                intent.getStringArrayListExtra("images");

        Log.d(TAG, "Received Item: " + safe(partName));

        // =====================================================
        // 🔹 SET TEXT DATA (SAFE)
        // =====================================================
        tvPartName.setText(safe(partName));
        tvCategory.setText(safe(mainCat) + " • " + safe(subCat));
        tvPartNo.setText("Part No: " + safe(partNo));

        tvMrp.setText("MRP ₹" + safe(mrp));
        tvMrp.setPaintFlags(tvMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        tvSaleRate.setText("₹" + safe(saleRate));

        tvUnitStock.setText(
                "Unit: " + safe(unitNm) + " | Stock: " + safe(stock)
        );

        // =====================================================
        // 🔹 IMAGE HANDLING (API → FALLBACK)
        // =====================================================
        if (images != null && !images.isEmpty()) {
            imageSlider.setImageUrls(images);
        } else {
            showFallbackImage();
        }

        // =====================================================
        // 🔹 ADD TO CART (NEW LOGIC ONLY)
        // =====================================================
        btnAddToCart.setOnClickListener(v -> {

            String finalItemId = (itemId != null) ? itemId : "";

            String imageUrl =
                    (images != null && !images.isEmpty()) ? images.get(0) : null;

            double mrpValue = parseDoubleSafe(mrp); // ✅ MRP used

            CartModel cartItem = new CartModel(
                    finalItemId,
                    safe(partName),
                    safe(unitNm),
                    mrpValue,      // ✅ GST INCLUDED
                    1,
                    imageUrl
            );

            CartStorage.addToCart(this, cartItem);

            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();

            Log.d(TAG, "Item added to cart with MRP: " + mrpValue);
        });


    }

    // =====================================================
    // 🔹 FALLBACK IMAGE HANDLER
    // =====================================================
    private void showFallbackImage() {
        ArrayList<Integer> fallbackImages = new ArrayList<>();
        fallbackImages.add(R.drawable.nutbolt);
        imageSlider.setDrawableImages(fallbackImages);
    }

    // =====================================================
    // 🔹 NULL SAFETY
    // =====================================================
    private String safe(String value) {
        return (value != null && !value.trim().isEmpty())
                ? value : "N/A";
    }

    private int parseIntSafe(String value) {
        try {
            return (int) Double.parseDouble(value);
        } catch (Exception e) {
            return 0;
        }
    }
    // ✅ ADD THIS
    private double parseDoubleSafe(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0.0;
        }
    }
    // =====================================================
    // 🔹 SLIDER LIFECYCLE
    // =====================================================
    @Override
    protected void onResume() {
        super.onResume();
        if (imageSlider != null) {
            imageSlider.startAutoScrollPublic();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (imageSlider != null) {
            imageSlider.stopAutoScroll();
        }
    }
}

