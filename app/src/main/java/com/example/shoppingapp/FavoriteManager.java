
package com.example.shoppingapp;

import android.content.Context;

import com.example.shoppingapp.OrderScreen.OrderCardModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoriteManager {

    private static final List<OrderCardModel> favoriteList = new ArrayList<>();
    private static boolean isLoaded = false;

    // Load favorites from SharedPreferences once
    public static void loadFavorites(Context context) {
        if (isLoaded) return;

        String json = WishlistSharedPrefHelper.getWishlist(context);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<OrderCardModel>>() {}.getType();
            List<OrderCardModel> savedList = gson.fromJson(json, type);

            if (savedList != null) {
                favoriteList.clear();
                favoriteList.addAll(savedList);
            }
        }

        isLoaded = true;
    }

    // Save favorites to SharedPreferences
    public static void save(Context context) {
        Gson gson = new Gson();
        String json = gson.toJson(favoriteList);
        WishlistSharedPrefHelper.saveWishlist(context, json);
    }

    public static void addToFavorite(OrderCardModel item) {
        if (!favoriteList.contains(item)) {
            favoriteList.add(item);
        }
    }

    public static void removeFromFavorite(OrderCardModel item) {
        favoriteList.remove(item);
    }

    public static List<OrderCardModel> getFavorites() {
        return favoriteList;
    }

    public static boolean isFavorite(OrderCardModel item) {
        return favoriteList.contains(item);
    }
}
