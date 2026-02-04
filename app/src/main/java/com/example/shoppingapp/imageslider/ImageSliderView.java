//package com.example.shoppingapp.imageslider;
//
//import android.content.Context;
//import android.util.AttributeSet;
//
//import androidx.annotation.NonNull;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.viewpager2.widget.ViewPager2;
//
//import com.example.shoppingapp.R;
//import com.google.android.material.tabs.TabLayout;
//import com.google.android.material.tabs.TabLayoutMediator;
//
//import java.util.List;
//
//public class ImageSliderView extends ConstraintLayout {
//
//    private ViewPager2 viewPager;
//    private TabLayout tabLayout;
//
//    // Constructor used when view is added in XML
//    public ImageSliderView(@NonNull Context context, AttributeSet attrs) {
//        super(context, attrs);
//
//        // Inflate slider layout into this custom view
//        inflate(context, R.layout.view_image_slider, this);
//
//        viewPager = findViewById(R.id.viewPager);
//        tabLayout = findViewById(R.id.tabDots);
//    }
//
//    // Public method to set images
//    public void setImages(List<String> images) {
//        if (images == null || images.isEmpty()) return;
//
//        ImageSliderAdapter adapter = new ImageSliderAdapter(images);
//        viewPager.setAdapter(adapter);
//
//        new TabLayoutMediator(tabLayout, viewPager,
//                (tab, position) -> {
//                    // no text, only dots
//                }).attach();
//    }
//}

package com.example.shoppingapp.imageslider;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shoppingapp.R;

import java.util.ArrayList;
import java.util.List;

public class ImageSliderView extends ConstraintLayout {

    private ViewPager2 viewPager;
    private LinearLayout dotContainer;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable autoScrollRunnable;

    private static final long AUTO_SCROLL_DELAY = 3000;

    private List<Integer> originalImages = new ArrayList<>();
    private List<Integer> sliderImages = new ArrayList<>();

    private ImageView[] dots;

    public ImageSliderView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_image_slider, this);

        viewPager = findViewById(R.id.viewPager);
        dotContainer = findViewById(R.id.dotContainer);

        setupFadeAnimation();
        setupTouchPause();
    }

    // ================================
    // PUBLIC METHOD
    // ================================
    public void setImages(List<Integer> images) {
        if (images == null || images.size() <= 1) return;

        originalImages.clear();
        originalImages.addAll(images);

        // 🔁 Fake list for infinite scroll
        sliderImages.clear();
        sliderImages.add(images.get(images.size() - 1)); // last
        sliderImages.addAll(images);                      // all
        sliderImages.add(images.get(0));                  // first

        ImageSliderAdapter adapter = new ImageSliderAdapter(
                sliderImages,
                position -> {
                    int realPosition = getRealPosition(position);

                    android.content.Intent intent =
                            new android.content.Intent(
                                    getContext(),
                                    com.example.shoppingapp.itemdetailscreen.FullscreenImageActivity.class
                            );

                    intent.putIntegerArrayListExtra(
                            "images",
                            new ArrayList<>(originalImages)
                    );
                    intent.putExtra("position", realPosition);

                    getContext().startActivity(intent);
                }
        );

        viewPager.setAdapter(adapter);


        // Start at first real item
        viewPager.setCurrentItem(1, false);

        setupDots(originalImages.size());
        setupInfiniteScroll();
        setupDotSync();
        startAutoScroll();
    }

    // ================================
    // AUTO SCROLL
    // ================================
    private void startAutoScroll() {
        stopAutoScroll();

        autoScrollRunnable = () -> {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            handler.postDelayed(autoScrollRunnable, AUTO_SCROLL_DELAY);
        };

        handler.postDelayed(autoScrollRunnable, AUTO_SCROLL_DELAY);
    }

    public void stopAutoScroll() {
        handler.removeCallbacksAndMessages(null);
    }
    public void startAutoScrollPublic() {
        startAutoScroll();
    }


    // Pause auto-scroll when user touches
    private void setupTouchPause() {
        viewPager.getChildAt(0).setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                stopAutoScroll();
            } else if (event.getAction() == MotionEvent.ACTION_UP
                    || event.getAction() == MotionEvent.ACTION_CANCEL) {
                startAutoScroll();
            }
            return false;
        });
    }

    // ================================
    // INFINITE SCROLL HANDLING
    // ================================
    private void setupInfiniteScroll() {
        viewPager.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageScrollStateChanged(int state) {
                        if (state == ViewPager2.SCROLL_STATE_IDLE) {
                            int position = viewPager.getCurrentItem();

                            if (position == 0) {
                                viewPager.setCurrentItem(
                                        sliderImages.size() - 2, false);
                            } else if (position == sliderImages.size() - 1) {
                                viewPager.setCurrentItem(1, false);
                            }
                        }
                    }
                });
    }

    // ================================
    // DOT INDICATOR
    // ================================
    private void setupDots(int count) {
        dotContainer.removeAllViews();
        dots = new ImageView[count];

        for (int i = 0; i < count; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageResource(R.drawable.dot_inactive);

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);

            dotContainer.addView(dots[i], params);
        }

        setActiveDot(0);
    }

    private void setupDotSync() {
        viewPager.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        setActiveDot(getRealPosition(position));
                    }
                });
    }

    private void setActiveDot(int index) {
        if (dots == null) return;

        for (int i = 0; i < dots.length; i++) {
            dots[i].setImageResource(
                    i == index
                            ? R.drawable.dot_active
                            : R.drawable.dot_inactive
            );
        }
    }

    private int getRealPosition(int position) {
        if (position == 0) {
            return originalImages.size() - 1;
        } else if (position == sliderImages.size() - 1) {
            return 0;
        } else {
            return position - 1;
        }
    }

    // ================================
    // FADE ANIMATION
    // ================================
    private void setupFadeAnimation() {
        viewPager.setPageTransformer((page, position) ->
                page.setAlpha(1 - Math.abs(position))
        );
    }
}
