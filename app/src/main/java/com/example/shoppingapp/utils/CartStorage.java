//
//package com.example.shoppingapp.utils;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import com.example.shoppingapp.CartScreen.CartModel;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CartStorage {
//
//    private static final String PREF_NAME = "shopping_cart";
//    private static final String KEY_CART = "CART_LIST";
//
//    // ---------------------- SAVE CART ----------------------
//    public static void saveCart(Context context, List<CartModel> cartList) {
//        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//
//        Gson gson = new Gson();
//        String json = gson.toJson(cartList);
//
//        editor.putString(KEY_CART, json);
//        editor.apply();
//    }
//
//    // ---------------------- LOAD CART ----------------------
//    public static List<CartModel> getCart(Context context) {
//        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//
//        Gson gson = new Gson();
//        String json = prefs.getString(KEY_CART, null);
//
//        Type type = new TypeToken<List<CartModel>>() {}.getType();
//
//        if (json == null) {
//            return new ArrayList<>(); // empty cart
//        }
//
//        return gson.fromJson(json, type);
//    }
//
//    // ---------------------- ADD / UPDATE CART ----------------------
//    public static void addToCart(Context context, CartModel item) {
//
//        List<CartModel> cartList = getCart(context);
//
//        boolean found = false;
//
//        for (CartModel cart : cartList) {
//            if (cart.getId().equals(item.getId())) {
//                // if product exists → increase qty
//                cart.setQty(cart.getQty() + item.getQty());
//                found = true;
//                break;
//            }
//        }
//
//        if (!found) {
//            cartList.add(item);
//        }
//
//        saveCart(context, cartList);
//    }
//
//    // ---------------------- DECREASE QTY ----------------------
//    /**
//     * Decrease quantity of productId by 1. If qty becomes 0 or less, the product is removed.
//     */
//    public static void decreaseQty(Context context, String productId) {
//        List<CartModel> cartList = getCart(context);
//
//        for (int i = 0; i < cartList.size(); i++) {
//            CartModel item = cartList.get(i);
//            if (item.getId().equals(productId)) {
//                int newQty = item.getQty() - 1;
//                if (newQty <= 0) {
//                    cartList.remove(i);
//                } else {
//                    item.setQty(newQty);
//                }
//                break;
//            }
//        }
//
//        saveCart(context, cartList);
//    }
//
//    // ---------------------- REMOVE A PRODUCT ----------------------
//    public static void removeItem(Context context, String productId) {
//
//        List<CartModel> cartList = getCart(context);
//
//        for (int i = 0; i < cartList.size(); i++) {
//            if (cartList.get(i).getId().equals(productId)) {
//                cartList.remove(i);
//                break;
//            }
//        }
//
//        saveCart(context, cartList);
//    }
//
//    // ---------------------- CLEAR COMPLETE CART ----------------------
//    public static void clearCart(Context context) {
//        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//        prefs.edit().remove(KEY_CART).apply();
//    }
//
//    // ---------------------- HELPER: TOTAL ITEMS ----------------------
//    /**
//     * Returns total number of items (sum of quantities) in the cart.
//     */
//    public static int getTotalItems(Context context) {
//        List<CartModel> cartList = getCart(context);
//        int total = 0;
//        for (CartModel item : cartList) {
//            total += item.getQty();
//        }
//        return total;
//    }
//}

package com.example.shoppingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.shoppingapp.CartScreen.CartModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CartStorage {

    private static final String TAG = "CartStorage";

    private static final String PREF_NAME = "shopping_cart";
    private static final String KEY_CART = "CART_LIST";

    // =====================================================
    // 🔹 SAVE CART
    // =====================================================
    public static void saveCart(Context context, List<CartModel> cartList) {

        if (context == null) return;

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(cartList);

        editor.putString(KEY_CART, json);
        editor.apply();

        Log.d(TAG, "Cart saved. Items count = " + cartList.size());
    }

    // =====================================================
    // 🔹 LOAD CART
    // =====================================================
    public static List<CartModel> getCart(Context context) {

        if (context == null) return new ArrayList<>();

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        String json = prefs.getString(KEY_CART, null);

        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<CartModel>>() {}.getType();

        List<CartModel> list = gson.fromJson(json, type);
        return list != null ? list : new ArrayList<>();
    }

    // =====================================================
    // 🔹 ADD / UPDATE CART
    // =====================================================
    public static void addToCart(Context context, CartModel newItem) {

        if (context == null || newItem == null) return;

        List<CartModel> cartList = getCart(context);

        boolean found = false;

        for (CartModel cart : cartList) {

            if (cart.getItemId().equals(newItem.getItemId())) {
                // ✅ Item already exists → increase qty
                cart.setQty(cart.getQty() + newItem.getQty());
                found = true;
                break;
            }
        }

        if (!found) {
            cartList.add(newItem);
        }

        saveCart(context, cartList);
    }

    // =====================================================
    // 🔹 DECREASE QTY BY 1
    // =====================================================
    public static void decreaseQty(Context context, String itemId) {

        if (context == null || itemId == null) return;

        List<CartModel> cartList = getCart(context);

        Iterator<CartModel> iterator = cartList.iterator();

        while (iterator.hasNext()) {
            CartModel item = iterator.next();

            if (itemId.equals(item.getItemId())) {
                int newQty = item.getQty() - 1;

                if (newQty <= 0) {
                    iterator.remove(); // ✅ safe remove
                } else {
                    item.setQty(newQty);
                }
                break;
            }
        }

        saveCart(context, cartList);
    }

    // =====================================================
    // 🔹 REMOVE ITEM COMPLETELY
    // =====================================================
    public static void removeItem(Context context, String itemId) {

        if (context == null || itemId == null) return;

        List<CartModel> cartList = getCart(context);

        Iterator<CartModel> iterator = cartList.iterator();

        while (iterator.hasNext()) {
            CartModel item = iterator.next();
            if (itemId.equals(item.getItemId())) {
                iterator.remove();
                break;
            }
        }

        saveCart(context, cartList);
    }

    // =====================================================
    // 🔹 CLEAR CART
    // =====================================================
    public static void clearCart(Context context) {

        if (context == null) return;

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        prefs.edit().remove(KEY_CART).apply();

        Log.d(TAG, "Cart cleared");
    }

    // =====================================================
    // 🔹 TOTAL ITEMS COUNT (BADGE)
    // =====================================================
    public static int getTotalItems(Context context) {

        List<CartModel> cartList = getCart(context);
        int total = 0;

        for (CartModel item : cartList) {
            total += item.getQty();
        }

        return total;
    }
}
