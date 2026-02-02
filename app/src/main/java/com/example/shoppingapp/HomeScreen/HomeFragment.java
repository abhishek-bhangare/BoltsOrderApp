
//Location Work code -Cached location

package com.example.shoppingapp.HomeScreen;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.shoppingapp.BaseFragment;
import com.example.shoppingapp.ProfileScreen.ProfileActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.network.ApiClient;
import com.example.shoppingapp.network.ApiService;
import com.example.shoppingapp.network.request.MainCategoryRequest;
import com.example.shoppingapp.network.response.MainCategoryResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BaseFragment {

    // ================= LOCATION =================
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101; // 🔵 LOCATION
    private static final String TAG = "HomeFragmentLocation"; // 🔵 LOCATION
    private FusedLocationProviderClient fusedLocationClient; // 🔵 LOCATION
    // ============================================

    private ShapeableImageView profileIcon;
    private EditText searchEditText;
    private TextSwitcher textSwitcher;

    private RecyclerView rvGrocery;

    private final List<String> searchHints = new ArrayList<>();
    private int currentHintIndex = 0;
    private Handler handler = new Handler();
    private Runnable runnable;

    private LinearLayout searchHintLayout;


    public HomeFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // -------- BASIC VIEWS --------
        profileIcon = view.findViewById(R.id.profileIcon);
        searchEditText = view.findViewById(R.id.searchEditText);
        textSwitcher = view.findViewById(R.id.textSwitcher);
        searchHintLayout = view.findViewById(R.id.searchHintLayout);


        // 🔵 LOCATION: init client (NULL SAFE)
        if (getActivity() != null) {
            fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(getActivity());
        }

        // 🔵 LOCATION: permission + location
        checkLocationPermissionAndGetLocation();



        // -------- RECYCLER VIEWS --------
        rvGrocery = view.findViewById(R.id.rvGrocery);


        // 🔥 Grocery categories from API
        fetchGroceryCategoriesFromApi();

        searchEditText.addTextChangedListener(new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    searchHintLayout.setVisibility(View.GONE);
                } else {
                    searchHintLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override public void afterTextChanged(android.text.Editable s) {}
        });



        // -------- PROFILE CLICK --------
        profileIcon.setOnClickListener(v ->
                startActivity(new Intent(getActivity(), ProfileActivity.class)));

        // -------- TEXT SWITCHER --------
        textSwitcher.setFactory(() -> {
            TextView tv = new TextView(getContext());
            tv.setTextSize(16);
            tv.setSingleLine(true);
            tv.setTextColor(
                    getResources().getColor(android.R.color.darker_gray)
            );
            return tv;
        });


        textSwitcher.setInAnimation(getContext(), R.anim.slide_out_bottom);
        textSwitcher.setOutAnimation(getContext(), R.anim.slide_in_top);
        startFloatingText();

        return view;
    }

    // ================= LOCATION METHODS =================

    private void checkLocationPermissionAndGetLocation() {

        if (getContext() == null) return;

        if (ContextCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE
            );

        } else {
            if (checkIfLocationEnabled()) {   // 🔥 change
                getUserLocation();
            }
        }
    }



    private void getUserLocation() {

        if (getContext() == null || fusedLocationClient == null) { // 🟡 NULL SAFE
            Log.w(TAG, "Context or LocationClient is null");
            return;
        }

        if (ActivityCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.w(TAG, "Location permission not granted");
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {

                    if (location == null) { // 🟡 NULL SAFE
                        Log.w(TAG, "Location is NULL (GPS OFF / not cached)");
                        return;
                    }

                    double lat = location.getLatitude();
                    double lng = location.getLongitude();

                    Log.d(TAG, "User Latitude: " + lat);
                    Log.d(TAG, "User Longitude: " + lng);

                    getPincodeFromLocation(lat, lng);
                })
                .addOnFailureListener(e ->
                        Log.e(TAG, "Location error: " + e.getMessage()));
    }

    private void getPincodeFromLocation(double lat, double lng) {

        if (getContext() == null) { // 🟡 NULL SAFE
            Log.w(TAG, "Context is null, skipping geocoder");
            return;
        }

        try {
            Geocoder geocoder =
                    new Geocoder(getContext(), Locale.getDefault());

            List<Address> addresses =
                    geocoder.getFromLocation(lat, lng, 1);

            if (addresses == null || addresses.isEmpty()) { // 🟡 NULL SAFE
                Log.w(TAG, "No address found");
                return;
            }

            Address address = addresses.get(0);

            String pincode = address.getPostalCode();
            String city = address.getLocality();
            String state = address.getAdminArea();

            Log.d(TAG, "User Pincode: " + (pincode != null ? pincode : "N/A"));
            Log.d(TAG, "City: " + (city != null ? city : "N/A"));
            Log.d(TAG, "State: " + (state != null ? state : "N/A"));

        } catch (Exception e) {
            Log.e(TAG, "Geocoder exception: " + e.getMessage());
        }
    }
    private boolean checkIfLocationEnabled() {

        if (getContext() == null) return false;

        LocationManager locationManager =
                (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        if (locationManager != null) {
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }

        if (!gpsEnabled && !networkEnabled) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Enable Location")
                    .setMessage("Location services are required to get your current location.")
                    .setCancelable(false)
                    .setPositiveButton("Turn On", (dialog, which) ->
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                    .setNegativeButton("Cancel", null)
                    .show();
            return false;
        }

        return true;
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation();
            } else {
                Log.w(TAG, "User denied location permission");
            }
        }
    }

    // ================== API : GROCERY CATEGORIES ==================
    private void fetchGroceryCategoriesFromApi() {

        // 🟡 NULL SAFETY
        if (getContext() == null || rvGrocery == null) {
            Log.w("HomeFragment", "Context or RecyclerView is NULL");
            return;
        }

        Log.d("HomeFragment", "Calling Main Category API");

        rvGrocery.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvGrocery.setHasFixedSize(true);
        rvGrocery.setNestedScrollingEnabled(false);

        ApiService apiService =
                ApiClient.getClient().create(ApiService.class);
        // 🔥 Vehicle type
        String comId = "1";
        // 🔥 RAW JSON REQUEST BODY
        MainCategoryRequest request = new MainCategoryRequest(comId);

        apiService.getMainCategories(request)
                .enqueue(new Callback<List<MainCategoryResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<MainCategoryResponse>> call,
                            Response<List<MainCategoryResponse>> response) {

                        Log.d("HomeFragment",
                                "API Response Code: " + response.code());

                        if (!response.isSuccessful()) {
                            Log.e("HomeFragment",
                                    "API failed with code: " + response.code());
                            return;
                        }

                        if (response.body() == null) {
                            Log.w("HomeFragment", "Response body is NULL");
                            return;
                        }

                        if (response.body().isEmpty()) {
                            Log.w("HomeFragment", "Category list is EMPTY");
                            return;
                        }

                        List<MainCategoryResponse> categories = response.body();

                        Log.d("HomeFragment",
                                "Categories count: " + categories.size());

                        rvGrocery.setAdapter(
                                new GroceryAdapter(categories, comId)
                        );

                        // 🔥 SEARCH HINTS
                        searchHints.clear();
                        for (MainCategoryResponse model : categories) {

                            if (model != null && model.getCategoryName() != null) { // 🟢 NULL SAFE
                                searchHints.add("\"" + model.getCategoryName() + "\"");
                            }
                        }

                        if (!searchHints.isEmpty()) {
                            startFloatingText();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<MainCategoryResponse>> call,
                            Throwable t) {

                        Log.e("HomeFragment",
                                "API Call Failed", t);

                        if (getContext() != null) {
                            Toast.makeText(getContext(),
                                    "Failed to load categories",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    // ================== STATIC DATA (UNCHANGED) ==================

//    private List<HomeCategoryModel> getBestsellerList() {
//        List<HomeCategoryModel> list = new ArrayList<>();
//        list.add(new HomeCategoryModel("Vegetables & Fruits", R.drawable.fruits_img));
//        list.add(new HomeCategoryModel("Chips & Namkeen", R.drawable.chips_img));
//        list.add(new HomeCategoryModel("Oil, Ghee & Masala", R.drawable.ghee_img));
//        list.add(new HomeCategoryModel("Dairy, Bread & Eggs", R.drawable.bread_img));
//        list.add(new HomeCategoryModel("Drinks & Juices", R.drawable.juice_img2));
//        list.add(new HomeCategoryModel("Bakery & Biscuits", R.drawable.biscuits));
//        return list;
//    }

    // ================== FLOATING TEXT ==================

    private void startFloatingText() {

        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }

        runnable = new Runnable() {
            @Override
            public void run() {

                if (searchHints.isEmpty()) return;

                textSwitcher.setText(searchHints.get(currentHintIndex));
                currentHintIndex =
                        (currentHintIndex + 1) % searchHints.size();

                handler.postDelayed(this, 2000);
            }
        };

        handler.post(runnable);
    }


    // ================== LIFECYCLE ==================

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected View getScrollableView() {
        return getView().findViewById(R.id.homeScrollView);
    }
    @Override
    public void onResume() {
        super.onResume();
        checkLocationPermissionAndGetLocation();
    }

}
