package com.example.shoppingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImagePrefManager {

    private static final String PREF_NAME = "profile_pref";
    private static final String KEY_PROFILE_IMAGE = "profile_image";

    // ⭐ Save Bitmap to SharedPreferences
    public static void saveImage(Context context, Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_PROFILE_IMAGE, encodedImage).apply();
    }

    // ⭐ Load Bitmap from SharedPreferences
    public static Bitmap getImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String encodedImage = prefs.getString(KEY_PROFILE_IMAGE, null);

        if (encodedImage != null) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }

        return null;
    }

    // ⭐ Optional: delete saved image
    public static void clearImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_PROFILE_IMAGE).apply();
    }
}
