//
//package com.example.shoppingapp.ProfileScreen;
//
//import android.Manifest;
//import android.app.AlertDialog;
//import android.content.ContentValues;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.shoppingapp.EditProfileScreen.EditProfileActivity;
//import com.example.shoppingapp.LoginScreen.LoginActivity;
//import com.example.shoppingapp.R;
//import com.example.shoppingapp.ProfileScreen.profilepages.AboutUsActivity;
//import com.example.shoppingapp.ProfileScreen.profilepages.OrdersActivity;
//import com.example.shoppingapp.ProfileScreen.profilepages.WalletActivity;
//import com.example.shoppingapp.ProfileScreen.profilepages.WishlistActivity;
//import com.example.shoppingapp.network.ApiClient;
//import com.example.shoppingapp.network.UserApiService;
//import com.example.shoppingapp.network.response.UserProfile;
//import com.example.shoppingapp.utils.ImagePrefManager;
//import com.example.shoppingapp.utils.SessionManager;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class ProfileActivity extends AppCompatActivity {
//
//    private static final String TAG = "PROFILE_ACTIVITY";
//
//    // ---------------- UI ----------------
//    private ImageButton btnBack;
//    private ImageView profileImage;
//    private TextView tvMobile, tvName, tvAddress;
//    private SessionManager sessionManager;
//
//    // ---------------- API ----------------
//    private UserApiService userApiService;
//
//    // 🔥🔥 ADD THIS: store profile for Edit Profile
//    private UserProfile currentUserProfile;
//
//    // ---------------- Image handling ----------------
//    private Uri imageUri;
//    private ActivityResultLauncher<String> requestCameraPermission;
//    private ActivityResultLauncher<String> requestGalleryPermission;
//    private ActivityResultLauncher<Uri> cameraLauncher;
//    private ActivityResultLauncher<String> galleryLauncher;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
//
//        Log.d(TAG, "onCreate");
//
//        sessionManager = new SessionManager(this);
//
//        // 🔒 Security check
//        if (!sessionManager.isLoggedIn()) {
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
//            return;
//        }
//
//        // API init
//        userApiService = ApiClient.getClient().create(UserApiService.class);
//
//        initViews();
//        setupTopBar();
//        setupRowContent();
//        setupRowClicks();
//        initImagePickers();
//        loadSavedProfileImage();
//
//        // Load fast data
//        loadUserDataFromSession();
//
//        // Load profile from API
//        fetchUserProfileFromApi();
//    }
//
//    // ---------------- SESSION DATA ----------------
//
//    private void loadUserDataFromSession() {
//        String mobile = sessionManager.getMobile();
//        Log.d(TAG, "Session mobile: " + mobile);
//
//        if (mobile != null && !mobile.isEmpty()) {
//            tvMobile.setText(mobile);
//        }
//    }
//
//    // ---------------- PROFILE API ----------------
//
//    private void fetchUserProfileFromApi() {
//
//        String uniqueId = sessionManager.getUniqueId();
//        Log.d(TAG, "Fetching profile with uniqueId: " + uniqueId);
//
//        if (uniqueId == null || uniqueId.isEmpty()) return;
//
//        userApiService.getUserProfile(uniqueId)
//                .enqueue(new Callback<List<UserProfile>>() {
//
//                    @Override
//                    public void onResponse(@NonNull Call<List<UserProfile>> call,
//                                           @NonNull Response<List<UserProfile>> response) {
//
//                        if (response.isSuccessful()
//                                && response.body() != null
//                                && !response.body().isEmpty()) {
//
//                            UserProfile user = response.body().get(0);
//
//                            currentUserProfile = user; // 🔥 IMPORTANT for Edit Profile
//
//                            Log.d(TAG, "Profile loaded: " + user.getCustName());
//
//                            // Mobile
//                            if (user.getCustMobile() != null)
//                                tvMobile.setText(user.getCustMobile());
//
//                            // Name
//                            if (user.getCustName() != null)
//                                tvName.setText(user.getCustName());
//
//                            // -------- FULL ADDRESS (Address 1 + Address 2) --------
//                            StringBuilder addressBuilder = new StringBuilder();
//
//                            if (user.getAddress() != null && !user.getAddress().isEmpty())
//                                addressBuilder.append(user.getAddress());
//
//                            if (user.getAddress1() != null && !user.getAddress1().isEmpty()) {
//                                if (addressBuilder.length() > 0) addressBuilder.append("\n");
//                                addressBuilder.append(user.getAddress1());
//                            }
//
//                            if (user.getArea() != null && !user.getArea().isEmpty())
//                                addressBuilder.append("\n").append(user.getArea());
//
//                            if (user.getCity() != null && !user.getCity().isEmpty())
//                                addressBuilder.append(", ").append(user.getCity());
//
//                            if (user.getPincode() != null && !user.getPincode().isEmpty())
//                                addressBuilder.append(" - ").append(user.getPincode());
//
//                            tvAddress.setText(addressBuilder.toString());
//
//                            Log.d(TAG, "Address:\n" + addressBuilder);
//
//                        } else {
//                            Log.w(TAG, "Profile API empty response");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Call<List<UserProfile>> call,
//                                          @NonNull Throwable t) {
//                        Log.e(TAG, "Profile API failed", t);
//                    }
//                });
//    }
//
//    // ---------------- INIT ----------------
//
//    private void initViews() {
//        btnBack = findViewById(R.id.btnBack);
//        profileImage = findViewById(R.id.profileImage);
//        tvMobile = findViewById(R.id.tvMobile);
//        tvName = findViewById(R.id.tvName);
//        tvAddress = findViewById(R.id.tvAddress);
//    }
//
//    // ---------------- TOP BAR ----------------
//
//    private void setupTopBar() {
//        btnBack.setOnClickListener(v -> finish());
//    }
//
//    // ---------------- SET ROW ICON + TITLE ----------------
//
//    private void setupRowContent() {
//        setupRow(R.id.itemEditProfile, R.drawable.ic_credit_card, R.color.blue_500, "Edit Profile");
//        setupRow(R.id.itemOrders, R.drawable.ic_shop_bag, R.color.orange_500, "Your Orders");
//        setupRow(R.id.itemWishlist, R.drawable.ic_favorite_outline, R.color.pink_500, "Wishlist");
//        setupRow(R.id.itemWallet, R.drawable.ic_credit_card, R.color.green_500, "Wallet");
//        setupRow(R.id.itemAbout, R.drawable.ic_info, R.color.purple_500, "About Us");
//    }
//
//    private void setupRow(int rowId, int iconRes, int iconColor, String title) {
//        ImageView ivIcon = findViewById(rowId).findViewById(R.id.ivIcon);
//        TextView tvTitle = findViewById(rowId).findViewById(R.id.tvTitle);
//
//        ivIcon.setImageResource(iconRes);
//        ivIcon.setColorFilter(getResources().getColor(iconColor));
//        tvTitle.setText(title);
//    }
//
//    // ---------------- ROW CLICKS ----------------
//
//    private void setupRowClicks() {
//
//        // 🔥🔥 UPDATED: PASS PROFILE VIA INTENT
//        findViewById(R.id.itemEditProfile).setOnClickListener(v -> {
//
//            if (currentUserProfile == null) {
//                Toast.makeText(this, "Profile not loaded yet", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
//            intent.putExtra("user_profile", currentUserProfile); // ✅ Parcelable
//            startActivity(intent);
//        });
//
//
//        findViewById(R.id.itemOrders)
//                .setOnClickListener(v -> startActivity(new Intent(this, OrdersActivity.class)));
//
//        findViewById(R.id.itemWishlist)
//                .setOnClickListener(v -> startActivity(new Intent(this, WishlistActivity.class)));
//
//        findViewById(R.id.itemWallet)
//                .setOnClickListener(v -> startActivity(new Intent(this, WalletActivity.class)));
//
//        findViewById(R.id.itemAbout)
//                .setOnClickListener(v -> startActivity(new Intent(this, AboutUsActivity.class)));
//
//        findViewById(R.id.itemLogout)
//                .setOnClickListener(v -> showLogoutDialog());
//    }
//
//    // ---------------- PROFILE IMAGE ----------------
//
//    private void loadSavedProfileImage() {
//        Bitmap bitmap = ImagePrefManager.getImage(this);
//        if (bitmap != null) {
//            profileImage.setImageBitmap(bitmap);
//        }
//
//        profileImage.setOnClickListener(v -> showImageSourceDialog());
//    }
//
//    private void initImagePickers() {
//
//        requestCameraPermission =
//                registerForActivityResult(new ActivityResultContracts.RequestPermission(),
//                        granted -> {
//                            if (granted) openCamera();
//                            else Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
//                        });
//
//        requestGalleryPermission =
//                registerForActivityResult(new ActivityResultContracts.RequestPermission(),
//                        granted -> {
//                            if (granted) openGallery();
//                            else Toast.makeText(this, "Gallery permission denied", Toast.LENGTH_SHORT).show();
//                        });
//
//        cameraLauncher =
//                registerForActivityResult(new ActivityResultContracts.TakePicture(), success -> {
//                    if (success && imageUri != null) {
//                        profileImage.setImageURI(imageUri);
//                        saveImage(imageUri);
//                    }
//                });
//
//        galleryLauncher =
//                registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
//                    if (uri != null) {
//                        profileImage.setImageURI(uri);
//                        saveImage(uri);
//                    }
//                });
//    }
//
//    private void showImageSourceDialog() {
//        new AlertDialog.Builder(this)
//                .setTitle("Select Profile Image")
//                .setItems(new String[]{"Camera", "Gallery"}, (d, i) -> {
//                    if (i == 0)
//                        requestCameraPermission.launch(Manifest.permission.CAMERA);
//                    else
//                        requestGalleryPermission.launch(getGalleryPermission());
//                })
//                .show();
//    }
//
//    private void openCamera() {
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, "Profile_Image");
//
//        imageUri = getContentResolver()
//                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//
//        cameraLauncher.launch(imageUri);
//    }
//
//    private void openGallery() {
//        galleryLauncher.launch("image/*");
//    }
//
//    private void saveImage(Uri uri) {
//        try {
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//            ImagePrefManager.saveImage(this, bitmap);
//        } catch (Exception e) {
//            Log.e(TAG, "Image save failed", e);
//        }
//    }
//
//    private String getGalleryPermission() {
//        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
//                ? Manifest.permission.READ_MEDIA_IMAGES
//                : Manifest.permission.READ_EXTERNAL_STORAGE;
//    }
//
//    // ---------------- LOGOUT ----------------
//
//    private void showLogoutDialog() {
//        new AlertDialog.Builder(this)
//                .setTitle("Logout")
//                .setMessage("Are you sure you want to logout?")
//                .setPositiveButton("Logout", (d, w) -> logout())
//                .setNegativeButton("Cancel", null)
//                .show();
//    }
//
//    private void logout() {
//        sessionManager.logout();
//        Intent intent = new Intent(this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        finish();
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d(TAG, "onResume → refreshing profile");
//
//        fetchUserProfileFromApi(); // 🔥 reload updated data
//    }
//
//}
//

package com.example.shoppingapp.ProfileScreen;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.EditProfileScreen.EditProfileActivity;
import com.example.shoppingapp.LoginScreen.LoginActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.ProfileScreen.profilepages.AboutUsActivity;
import com.example.shoppingapp.ProfileScreen.profilepages.OrdersActivity;
import com.example.shoppingapp.ProfileScreen.profilepages.WalletActivity;
import com.example.shoppingapp.ProfileScreen.profilepages.WishlistActivity;
import com.example.shoppingapp.network.ApiClient;
import com.example.shoppingapp.network.UserApiService;
import com.example.shoppingapp.network.request.UserProfileRequest;
import com.example.shoppingapp.network.response.UserProfile;
import com.example.shoppingapp.network.response.UserProfileWrapperResponse;
import com.example.shoppingapp.utils.ImagePrefManager;
import com.example.shoppingapp.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "PROFILE_ACTIVITY";

    // ---------------- UI ----------------
    private ImageButton btnBack;
    private ImageView profileImage;

    // NOTE: No tvMobile / tvName / tvAddress
    // We use row-based layout (rowMobile, rowName, etc.)

    // ---------------- SESSION ----------------
    private SessionManager sessionManager;

    // ---------------- API ----------------
    private UserApiService userApiService;
    private UserProfile currentUserProfile; // used for Edit Profile

    // ---------------- IMAGE PICKERS ----------------
    private Uri imageUri;
    private ActivityResultLauncher<String> requestCameraPermission;
    private ActivityResultLauncher<String> requestGalleryPermission;
    private ActivityResultLauncher<Uri> cameraLauncher;
    private ActivityResultLauncher<String> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Log.d(TAG, "onCreate()");

        // ---------- Session init ----------
        sessionManager = new SessionManager(this);

        // ---------- Security check ----------
        if (!sessionManager.isLoggedIn()) {
            Log.w(TAG, "User not logged in → redirecting to Login");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // ---------- API init ----------
        userApiService = ApiClient.getClient().create(UserApiService.class);

        // ---------- UI init ----------
        initViews();
        setupTopBar();
        setupRowContent();
        setupRowClicks();
        initImagePickers();
        loadSavedProfileImage();

        // ---------- Load data ----------
        loadProfileFromSession();   // fast local data
        fetchUserProfileFromApi();  // latest server data
    }

    // =================================================
    // LOAD DATA FROM SESSION (INSTANT UI)
    // =================================================
    private void loadProfileFromSession() {

        Log.d(TAG, "Loading profile from session");

        setProfileRow(R.id.rowMobile, "Mobile", sessionManager.getMobile());
        setProfileRow(R.id.rowName, "Name", sessionManager.getName());
        setProfileRow(R.id.rowEmail, "Email", sessionManager.getEmail());
    }

    // =================================================
    // PROFILE API CALL (POST)
    // =================================================
    private void fetchUserProfileFromApi() {

        String custId = sessionManager.getCustomerId();
        String uniqueId = sessionManager.getUniqueId();

        Log.d(TAG, "fetchUserProfileFromApi()");
        Log.d(TAG, "custId=" + custId + ", uniqueId=" + uniqueId);

        if (custId == null || custId.isEmpty()
                || uniqueId == null || uniqueId.isEmpty()) {

            Log.e(TAG, "Session data missing → API aborted");
            return;
        }

        // Create request body
        UserProfileRequest request =
                new UserProfileRequest(custId, uniqueId);

        Log.d(TAG, "Profile API request created");

        userApiService.getUserProfile(request)
                .enqueue(new Callback<UserProfileWrapperResponse>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<UserProfileWrapperResponse> call,
                            @NonNull Response<UserProfileWrapperResponse> response) {

                        Log.d(TAG, "Profile API response code=" + response.code());

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isStatus()
                                && response.body().getData() != null) {

                            UserProfile user = response.body().getData();
                            currentUserProfile = user;

                            Log.d(TAG, "Profile loaded: " + user.getCustName());

                            // Update rows
                            setProfileRow(R.id.rowName, "Name", user.getCustName());
                            setProfileRow(R.id.rowMobile, "Mobile", user.getCustMobile());
                            setProfileRow(R.id.rowEmail, "Email", user.getEmail());
                            setProfileRow(R.id.rowShop, "Shop", user.getShopName());
                            setProfileRow(R.id.rowAddress, "Address", user.getAddress());
                            setProfileRow(R.id.rowCity, "City", user.getCity());
                            setProfileRow(R.id.rowArea, "Area", user.getArea());
                            setProfileRow(R.id.rowLandmark, "Landmark", user.getLandmark());
                            setProfileRow(R.id.rowState, "State", user.getState());
                            setProfileRow(R.id.rowPincode, "Pincode", user.getPincode());


                            // Build address
                            StringBuilder address = new StringBuilder();

                            if (user.getAddress() != null && !user.getAddress().isEmpty())
                                address.append(user.getAddress());

                            if (user.getAddress1() != null && !user.getAddress1().isEmpty())
                                address.append("\n").append(user.getAddress1());

                            if (user.getArea() != null && !user.getArea().isEmpty())
                                address.append("\n").append(user.getArea());

                            if (user.getCity() != null && !user.getCity().isEmpty())
                                address.append(", ").append(user.getCity());

                            if (user.getPincode() != null && !user.getPincode().equals("0"))
                                address.append(" - ").append(user.getPincode());

                            setProfileRow(
                                    R.id.rowAddress,
                                    "Address",
                                    address.length() > 0 ? address.toString() : "-"
                            );

                        } else {
                            Log.w(TAG, "Profile API returned empty or status=false");
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<UserProfileWrapperResponse> call,
                            @NonNull Throwable t) {

                        Log.e(TAG, "Profile API FAILED", t);
                        Toast.makeText(ProfileActivity.this,
                                "Failed to load profile",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    // =================================================
    // HELPER: SET PROFILE ROW (LABEL : VALUE)
    // =================================================
    private void setProfileRow(int rowId, String label, String value) {

        View row = findViewById(rowId);
        if (row == null) return;

        TextView tvLabel = row.findViewById(R.id.tvLabel);
        TextView tvValue = row.findViewById(R.id.tvValue);

        // ✅ ALWAYS set label
        tvLabel.setText(label);

        // ✅ Show dash if value empty
        if (value == null || value.trim().isEmpty() || value.equals("0")) {
            tvValue.setText("-");
            Log.d(TAG, label + " → -");
        } else {
            tvValue.setText(value);
            Log.d(TAG, label + " → " + value);
        }
    }



    // =================================================
    // INIT UI
    // =================================================
    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        profileImage = findViewById(R.id.profileImage);
    }

    private void setupTopBar() {
        btnBack.setOnClickListener(v -> finish());
    }

    // =================================================
    // PROFILE OPTIONS LIST
    // =================================================
    private void setupRowContent() {
        setupRow(R.id.itemEditProfile, R.drawable.ic_credit_card, R.color.blue_500, "Edit Profile");
        setupRow(R.id.itemOrders, R.drawable.ic_shop_bag, R.color.orange_500, "Your Orders");
        setupRow(R.id.itemWishlist, R.drawable.ic_favorite_outline, R.color.pink_500, "Wishlist");
        setupRow(R.id.itemWallet, R.drawable.ic_credit_card, R.color.green_500, "Wallet");
        setupRow(R.id.itemAbout, R.drawable.ic_info, R.color.purple_500, "About Us");
    }

    private void setupRow(int rowId, int iconRes, int iconColor, String title) {
        ImageView ivIcon = findViewById(rowId).findViewById(R.id.ivIcon);
        TextView tvTitle = findViewById(rowId).findViewById(R.id.tvTitle);

        ivIcon.setImageResource(iconRes);
        ivIcon.setColorFilter(getResources().getColor(iconColor));
        tvTitle.setText(title);
    }

    // =================================================
    // ROW CLICKS
    // =================================================
    private void setupRowClicks() {

        findViewById(R.id.itemEditProfile).setOnClickListener(v -> {
            if (currentUserProfile == null) {
                Toast.makeText(this, "Profile not loaded yet", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent i = new Intent(this, EditProfileActivity.class);
            i.putExtra("user_profile", currentUserProfile);
            startActivity(i);
        });

        findViewById(R.id.itemOrders)
                .setOnClickListener(v -> startActivity(new Intent(this, OrdersActivity.class)));

        findViewById(R.id.itemWishlist)
                .setOnClickListener(v -> startActivity(new Intent(this, WishlistActivity.class)));

        findViewById(R.id.itemWallet)
                .setOnClickListener(v -> startActivity(new Intent(this, WalletActivity.class)));

        findViewById(R.id.itemAbout)
                .setOnClickListener(v -> startActivity(new Intent(this, AboutUsActivity.class)));

        findViewById(R.id.itemLogout)
                .setOnClickListener(v -> showLogoutDialog());
    }

    // =================================================
    // PROFILE IMAGE (CAMERA + GALLERY)
    // =================================================
    private void initImagePickers() {

        requestCameraPermission =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                        granted -> {
                            if (granted) openCamera();
                            else Toast.makeText(this,
                                    "Camera permission denied",
                                    Toast.LENGTH_SHORT).show();
                        });

        requestGalleryPermission =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                        granted -> {
                            if (granted) openGallery();
                            else Toast.makeText(this,
                                    "Gallery permission denied",
                                    Toast.LENGTH_SHORT).show();
                        });

        cameraLauncher =
                registerForActivityResult(new ActivityResultContracts.TakePicture(),
                        success -> {
                            if (success && imageUri != null) {
                                profileImage.setImageURI(imageUri);
                                saveImage(imageUri);
                            }
                        });

        galleryLauncher =
                registerForActivityResult(new ActivityResultContracts.GetContent(),
                        uri -> {
                            if (uri != null) {
                                profileImage.setImageURI(uri);
                                saveImage(uri);
                            }
                        });
    }

    private void loadSavedProfileImage() {
        Bitmap bitmap = ImagePrefManager.getImage(this);
        if (bitmap != null) profileImage.setImageBitmap(bitmap);
        profileImage.setOnClickListener(v -> showImageSourceDialog());
    }

    private void showImageSourceDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Select Profile Image")
                .setItems(new String[]{"Camera", "Gallery"}, (d, i) -> {
                    if (i == 0)
                        requestCameraPermission.launch(Manifest.permission.CAMERA);
                    else
                        requestGalleryPermission.launch(getGalleryPermission());
                })
                .show();
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Profile_Image");
        imageUri = getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        cameraLauncher.launch(imageUri);
    }

    private void openGallery() {
        galleryLauncher.launch("image/*");
    }

    private void saveImage(Uri uri) {
        try {
            Bitmap bitmap =
                    MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            ImagePrefManager.saveImage(this, bitmap);
        } catch (Exception e) {
            Log.e(TAG, "Image save failed", e);
        }
    }

    private String getGalleryPermission() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                ? Manifest.permission.READ_MEDIA_IMAGES
                : Manifest.permission.READ_EXTERNAL_STORAGE;
    }

    // =================================================
    // LOGOUT
    // =================================================
    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", (d, w) -> logout())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void logout() {
        sessionManager.logout();
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() → refreshing profile");
        fetchUserProfileFromApi();
    }
}
