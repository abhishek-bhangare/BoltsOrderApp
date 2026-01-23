package com.example.shoppingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.CartScreen.CartFragment;
import com.example.shoppingapp.CategoriesScreen.CategoriesFragment;
import com.example.shoppingapp.HomeScreen.HomeFragment;
import com.example.shoppingapp.OrderScreen.OrderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    View overline; // renamed
    View bottomNavContainer; // 🔥 new: animate container (nav + overline)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Edge-to-Edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigation = findViewById(R.id.bottomNavigation);
        overline = findViewById(R.id.overline);
        bottomNavContainer = findViewById(R.id.bottomNavContainer); // 🔥

        // Load HomeFragment by default
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
            bottomNavigation.post(() -> moveOverline(0)); // position overline above first tab
        }

        // Handle BottomNavigation item clicks
        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int index = 0;

            if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment();
                index = 0;
            } else if (item.getItemId() == R.id.nav_order) {
                selectedFragment = new OrderFragment();
                index = 1;
            } else if (item.getItemId() == R.id.nav_categories) {
                selectedFragment = new CategoriesFragment();
                index = 2;
            } else if (item.getItemId() == R.id.nav_print) {
                selectedFragment = new CartFragment();
                index = 3;
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                moveOverline(index);
            }
            return true;
        });
    }

    // Replace fragment helper
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    // Animate overline above the selected icon
    private void moveOverline(int index) {
        int totalItems = bottomNavigation.getMenu().size();
        float tabWidth = bottomNavigation.getWidth() / (float) totalItems;

        // Center the overline above the icon
        float targetX = tabWidth * index + (tabWidth - overline.getWidth()) / 2f;

        overline.animate()
                .x(targetX)
                .setDuration(200)
                .start();
    }

// 🔥 Reusable scroll listener (works with ScrollView + RecyclerView)
public void setupScrollListener(View scrollableView) {
    if (scrollableView instanceof ScrollView) {
        ScrollView scrollView = (ScrollView) scrollableView;
        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            int scrollY = scrollView.getScrollY();
            handleScroll(scrollY, scrollView);
        });
    } else if (scrollableView instanceof RecyclerView) {
        RecyclerView recyclerView = (RecyclerView) scrollableView;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);
                int scrollY = recyclerView.computeVerticalScrollOffset();
                handleScroll(scrollY, recyclerView);
            }
        });
    }
}

// 🔥 Core scroll handling
private void handleScroll(int scrollY, View view) {
    Integer lastScrollY = (Integer) view.getTag();
    if (lastScrollY == null) lastScrollY = 0;

    if (scrollY > lastScrollY + 10) { // hide
        if (bottomNavContainer.getTranslationY() == 0) {
            bottomNavContainer.animate()
                    .translationY(bottomNavContainer.getHeight())
                    .alpha(0f) // fade out 🔥
                    .setDuration(400);
        }
    } else if (scrollY < lastScrollY - 10) { // show
        if (bottomNavContainer.getTranslationY() == bottomNavContainer.getHeight()) {
            bottomNavContainer.animate()
                    .translationY(0)
                    .alpha(1f) // fade in 🔥
                    .setDuration(400);
        }
    }

    if (scrollY == 0) { // always show at top
        bottomNavContainer.animate()
                .translationY(0)
                .alpha(1f)
                .setDuration(400);
    }

    view.setTag(scrollY);
}
}
