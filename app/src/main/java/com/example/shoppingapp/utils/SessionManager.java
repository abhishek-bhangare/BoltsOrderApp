//package com.example.shoppingapp.utils;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//public class SessionManager {
//
//    private static final String PREF_NAME = "shopping_app_session";
//    private static final String KEY_LOGIN = "is_logged_in";
//    private static final String KEY_CUST_ID = "cust_id";
//    private static final String KEY_MOBILE = "username";
//
//    private SharedPreferences pref;
//    private SharedPreferences.Editor editor;
//
//    public SessionManager(Context context) {
//        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//        editor = pref.edit();
//    }
//
//    // ✅ SAVE LOGIN
//    public void saveLogin(String custId, String mobile) {
//        editor.putBoolean(KEY_LOGIN, true);
//        editor.putString(KEY_CUST_ID, custId);
//        editor.putString(KEY_MOBILE, mobile);
//        editor.apply();
//    }
//
//    // ✅ CHECK LOGIN STATUS
//    public boolean isLoggedIn() {
//        return pref.getBoolean(KEY_LOGIN, false);
//    }
//
//    // ✅ GET CUSTOMER ID
//    public String getCustomerId() {
//        return pref.getString(KEY_CUST_ID, "");
//    }
//
//    // ✅ GET MOBILE NUMBER
//    public String getMobile() {
//        return pref.getString(KEY_MOBILE, "");
//    }
//
//    // ✅ LOGOUT
//    public void logout() {
//        editor.clear();
//        editor.apply();
//    }
//}


package com.example.shoppingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "shopping_app_session";

    // Login state
    private static final String KEY_LOGIN = "is_logged_in";

    // User identifiers
    private static final String KEY_CUST_ID = "cust_id";       // u_id
    private static final String KEY_UNIQUE_ID = "unique_id";   // for profile api

    // Basic user info
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_NAME = "cust_name";
    private static final String KEY_EMAIL = "email";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // ✅ SAVE LOGIN (AFTER LOGIN API)
    public void saveLogin(String custId, String uniqueId, String mobile) {
        editor.putBoolean(KEY_LOGIN, true);
        editor.putString(KEY_CUST_ID, custId);
        editor.putString(KEY_UNIQUE_ID, uniqueId);
        editor.putString(KEY_MOBILE, mobile);
        editor.apply();
    }

    // ✅ OPTIONAL: SAVE BASIC PROFILE (AFTER PROFILE API)
    public void saveProfile(String name, String email) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    // ✅ CHECK LOGIN STATUS
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_LOGIN, false);
    }

    // ✅ GET CUSTOMER ID (u_id)
    public String getCustomerId() {
        return pref.getString(KEY_CUST_ID, "");
    }

    // ✅ GET UNIQUE ID (FOR PROFILE API)
    public String getUniqueId() {
        return pref.getString(KEY_UNIQUE_ID, "");
    }

    // ✅ GET MOBILE
    public String getMobile() {
        return pref.getString(KEY_MOBILE, "");
    }

    // ✅ GET NAME (OPTIONAL CACHE)
    public String getName() {
        return pref.getString(KEY_NAME, "");
    }

    // ✅ GET EMAIL (OPTIONAL CACHE)
    public String getEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    // ✅ LOGOUT
    public void logout() {
        editor.clear();
        editor.apply();
    }
}
