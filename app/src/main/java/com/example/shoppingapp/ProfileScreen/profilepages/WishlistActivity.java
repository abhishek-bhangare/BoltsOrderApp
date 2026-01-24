//package com.example.shoppingapp.profilepages;
//
//import android.os.Bundle;
//import android.view.View;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.shoppingapp.FavoriteManager;
//import com.example.shoppingapp.OrderScreen.OrderCardModel;
//import com.example.shoppingapp.OrderScreen.OrderCardsAdapter;
//import com.example.shoppingapp.R;
//
//import java.util.List;
//
//public class WishlistActivity extends AppCompatActivity {
//
//    private RecyclerView wishlistRecycler;
//    private OrderCardsAdapter adapter;
//    private View emptyLayout;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_wishlist);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        wishlistRecycler = findViewById(R.id.wishlistRecycler);
//        emptyLayout = findViewById(R.id.emptyLayout);
//
//        wishlistRecycler.setLayoutManager(new GridLayoutManager(this, 2));
//
//        loadWishlist();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        loadWishlist();
//    }
//
//    private void loadWishlist() {
//        List<OrderCardModel> wishlistItems = FavoriteManager.getFavorites();
//
//        adapter = new OrderCardsAdapter(this, wishlistItems);
//        wishlistRecycler.setAdapter(adapter);
//
//        if (wishlistItems.isEmpty()) {
//            wishlistRecycler.setVisibility(View.GONE);
//            emptyLayout.setVisibility(View.VISIBLE);
//        } else {
//            wishlistRecycler.setVisibility(View.VISIBLE);
//            emptyLayout.setVisibility(View.GONE);
//        }
//    }
//}

package com.example.shoppingapp.ProfileScreen.profilepages;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shoppingapp.FavoriteManager;
import com.example.shoppingapp.OrderScreen.OrderCardModel;
import com.example.shoppingapp.OrderScreen.OrderCardsAdapter;
import com.example.shoppingapp.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class WishlistActivity extends AppCompatActivity {

    private RecyclerView wishlistRecycler;
    private OrderCardsAdapter adapter;
    private View emptyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wishlist);
        Window window = getWindow();
        // Status bar color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.topBar));



        // Handle system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        MaterialToolbar toolbar = findViewById(R.id.toolbarWishlist);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        wishlistRecycler = findViewById(R.id.wishlistRecycler);
        emptyLayout = findViewById(R.id.emptyLayout);

        wishlistRecycler.setLayoutManager(new GridLayoutManager(this, 2));

        // Load favorites from SharedPreferences (IMPORTANT)
        FavoriteManager.loadFavorites(this);

        loadWishlist();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWishlist(); // Refresh list when coming back
    }

    private void loadWishlist() {
        List<OrderCardModel> wishlistItems = FavoriteManager.getFavorites();

        adapter = new OrderCardsAdapter(this, wishlistItems);
        wishlistRecycler.setAdapter(adapter);

        // Show empty layout if no favorites
        if (wishlistItems == null || wishlistItems.isEmpty()) {
            wishlistRecycler.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            wishlistRecycler.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        }
    }
}
