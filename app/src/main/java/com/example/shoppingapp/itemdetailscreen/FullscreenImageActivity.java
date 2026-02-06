//
//package com.example.shoppingapp.itemdetailscreen;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageButton;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.viewpager2.widget.ViewPager2;
//
//import com.example.shoppingapp.R;
//import com.example.shoppingapp.imageslider.ImageSliderAdapter;
//
//import java.util.ArrayList;
//
//public class FullscreenImageActivity extends AppCompatActivity {
//
//    private ViewPager2 viewPager;
//    private ImageButton btnClose, btnPrev, btnNext;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_fullscreen_image);
//
//        // 🔹 Init views
//        viewPager = findViewById(R.id.viewPager);
//        btnClose = findViewById(R.id.btnClose);
//        btnPrev = findViewById(R.id.btnPrev);
//        btnNext = findViewById(R.id.btnNext);
//
//        // 🔹 Get images + clicked position
//        ArrayList<Integer> images =
//                getIntent().getIntegerArrayListExtra("images");
//        int position = getIntent().getIntExtra("position", 0);
//
//        if (images != null && !images.isEmpty()) {
//            ImageSliderAdapter adapter =
//                    new ImageSliderAdapter(images, null);
//
//            viewPager.setAdapter(adapter);
//            viewPager.setCurrentItem(position, false);
//        }
//
//        // ❌ Close button
//        btnClose.setOnClickListener(v -> finish());
//
//        // ⬅️ Previous arrow
//        btnPrev.setOnClickListener(v -> {
//            int current = viewPager.getCurrentItem();
//            if (current > 0) {
//                viewPager.setCurrentItem(current - 1, true);
//            }
//        });
//
//        // ➡️ Next arrow
//        btnNext.setOnClickListener(v -> {
//            int current = viewPager.getCurrentItem();
//            if (viewPager.getAdapter() != null &&
//                    current < viewPager.getAdapter().getItemCount() - 1) {
//                viewPager.setCurrentItem(current + 1, true);
//            }
//        });
//
//        // 🔹 Hide arrows on first / last image
//        viewPager.registerOnPageChangeCallback(
//                new ViewPager2.OnPageChangeCallback() {
//                    @Override
//                    public void onPageSelected(int pos) {
//                        int count = viewPager.getAdapter().getItemCount();
//
//                        btnPrev.setVisibility(
//                                pos == 0 ? View.INVISIBLE : View.VISIBLE
//                        );
//                        btnNext.setVisibility(
//                                pos == count - 1 ? View.INVISIBLE : View.VISIBLE
//                        );
//                    }
//                }
//        );
//    }
//}
//
package com.example.shoppingapp.itemdetailscreen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shoppingapp.R;

import java.util.ArrayList;

public class FullscreenImageActivity extends AppCompatActivity {

    private static final String TAG = "FullscreenImageAct";

    private ViewPager2 viewPager;
    private ImageButton btnClose, btnPrev, btnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_image);

        // =====================================================
        // 🔹 INIT VIEWS
        // =====================================================
        viewPager = findViewById(R.id.viewPager);
        btnClose = findViewById(R.id.btnClose);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);

        // =====================================================
        // 🔹 GET INTENT DATA
        // =====================================================
        ArrayList<String> imageUrls =
                getIntent().getStringArrayListExtra("images");

        ArrayList<Integer> drawableImages =
                getIntent().getIntegerArrayListExtra("drawable_images");

        int position = getIntent().getIntExtra("position", 0);

        Log.d(TAG, "Received position = " + position);

        // =====================================================
        // 🔹 PRIORITY 1: API IMAGE URLS (ZOOM ENABLED)
        // =====================================================
        if (imageUrls != null && !imageUrls.isEmpty()) {

            Log.d(TAG, "Opening fullscreen with URL images. Count = " + imageUrls.size());

            FullscreenImageAdapter adapter =
                    new FullscreenImageAdapter(imageUrls);

            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(position, false);

        }
        // =====================================================
        // 🔹 PRIORITY 2: DRAWABLE FALLBACK (ZOOM ENABLED)
        // =====================================================
        else if (drawableImages != null && !drawableImages.isEmpty()) {

            Log.d(TAG, "Opening fullscreen with drawable images. Count = " + drawableImages.size());

            FullscreenImageAdapter adapter =
                    new FullscreenImageAdapter(drawableImages, true);

            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(position, false);

        }
        // =====================================================
        // 🔴 NO IMAGES
        // =====================================================
        else {
            Log.e(TAG, "No images received, closing fullscreen");
            finish();
            return;
        }

        // =====================================================
        // ❌ CLOSE BUTTON
        // =====================================================
        btnClose.setOnClickListener(v -> finish());

        // =====================================================
        // ⬅️ PREVIOUS
        // =====================================================
        btnPrev.setOnClickListener(v -> {
            int current = viewPager.getCurrentItem();
            if (current > 0) {
                viewPager.setCurrentItem(current - 1, true);
            }
        });

        // =====================================================
        // ➡️ NEXT
        // =====================================================
        btnNext.setOnClickListener(v -> {
            int current = viewPager.getCurrentItem();
            if (viewPager.getAdapter() != null &&
                    current < viewPager.getAdapter().getItemCount() - 1) {
                viewPager.setCurrentItem(current + 1, true);
            }
        });

        // =====================================================
        // 🔹 HIDE ARROWS WHEN NEEDED
        // =====================================================
        viewPager.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int pos) {
                        if (viewPager.getAdapter() == null) return;

                        int count = viewPager.getAdapter().getItemCount();

                        btnPrev.setVisibility(
                                pos == 0 ? View.INVISIBLE : View.VISIBLE
                        );
                        btnNext.setVisibility(
                                pos == count - 1 ? View.INVISIBLE : View.VISIBLE
                        );
                    }
                }
        );
    }
}
