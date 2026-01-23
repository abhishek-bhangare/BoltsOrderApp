package com.example.shoppingapp;

import android.content.Context;
import android.content.SharedPreferences;

public class WishlistSharedPrefHelper {

    private static final String PREF_NAME = "wishlist_pref";
    private static final String KEY_WISHLIST = "wishlist_data";

    public static void saveWishlist(Context context, String json) {
        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        prefs.edit().putString(KEY_WISHLIST, json).apply();
    }

    public static String getWishlist(Context context) {
        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getString(KEY_WISHLIST, null);
    }
}
