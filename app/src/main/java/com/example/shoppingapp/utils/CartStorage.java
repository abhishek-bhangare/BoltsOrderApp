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
//}

package com.example.shoppingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.shoppingapp.CartScreen.CartModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartStorage {

    private static final String PREF_NAME = "shopping_cart";
    private static final String KEY_CART = "CART_LIST";

    // ---------------------- SAVE CART ----------------------
    public static void saveCart(Context context, List<CartModel> cartList) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(cartList);

        editor.putString(KEY_CART, json);
        editor.apply();
    }

    // ---------------------- LOAD CART ----------------------
    public static List<CartModel> getCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = prefs.getString(KEY_CART, null);

        Type type = new TypeToken<List<CartModel>>() {}.getType();

        if (json == null) {
            return new ArrayList<>(); // empty cart
        }

        return gson.fromJson(json, type);
    }

    // ---------------------- ADD / UPDATE CART ----------------------
    public static void addToCart(Context context, CartModel item) {

        List<CartModel> cartList = getCart(context);

        boolean found = false;

        for (CartModel cart : cartList) {
            if (cart.getId().equals(item.getId())) {
                // if product exists → increase qty
                cart.setQty(cart.getQty() + item.getQty());
                found = true;
                break;
            }
        }

        if (!found) {
            cartList.add(item);
        }

        saveCart(context, cartList);
    }

    // ---------------------- DECREASE QTY ----------------------
    /**
     * Decrease quantity of productId by 1. If qty becomes 0 or less, the product is removed.
     */
    public static void decreaseQty(Context context, String productId) {
        List<CartModel> cartList = getCart(context);

        for (int i = 0; i < cartList.size(); i++) {
            CartModel item = cartList.get(i);
            if (item.getId().equals(productId)) {
                int newQty = item.getQty() - 1;
                if (newQty <= 0) {
                    cartList.remove(i);
                } else {
                    item.setQty(newQty);
                }
                break;
            }
        }

        saveCart(context, cartList);
    }

    // ---------------------- REMOVE A PRODUCT ----------------------
    public static void removeItem(Context context, String productId) {

        List<CartModel> cartList = getCart(context);

        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).getId().equals(productId)) {
                cartList.remove(i);
                break;
            }
        }

        saveCart(context, cartList);
    }

    // ---------------------- CLEAR COMPLETE CART ----------------------
    public static void clearCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_CART).apply();
    }

    // ---------------------- HELPER: TOTAL ITEMS ----------------------
    /**
     * Returns total number of items (sum of quantities) in the cart.
     */
    public static int getTotalItems(Context context) {
        List<CartModel> cartList = getCart(context);
        int total = 0;
        for (CartModel item : cartList) {
            total += item.getQty();
        }
        return total;
    }
}
