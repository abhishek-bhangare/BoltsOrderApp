
//package com.example.shoppingapp.EditProfileScreen;
//
//import android.Manifest;
//import android.app.AlertDialog;
//import android.content.ContentValues;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.text.TextUtils;
//import android.util.Patterns;
//import android.view.Window;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.shoppingapp.R;
//import com.example.shoppingapp.SimpleTextWatcher;
//import com.example.shoppingapp.network.response.UserProfile;
//import com.example.shoppingapp.utils.ImagePrefManager;
//import com.example.shoppingapp.utils.SessionManager;
//import com.google.android.material.button.MaterialButton;
//import com.google.android.material.textfield.MaterialAutoCompleteTextView;
//import com.google.android.material.textfield.TextInputEditText;
//import com.google.android.material.textfield.TextInputLayout;
//
//public class EditProfileActivity extends AppCompatActivity {
//
//    // UI
//    private ImageButton btnBack;
//    private ImageView profileImage, cameraIcon;
//    private MaterialButton btnSave;
//
//    private TextInputLayout tilName, tilMobile, tilEmail, tilAddress1, tilAddress2,
//            tilArea, tilLandmark, tilCity, tilPincode, tilState;
//
//    private TextInputEditText etName, etMobile, etEmail, etAddress1, etAddress2,
//            etArea, etLandmark, etCity, etPincode;
//
//    private MaterialAutoCompleteTextView etState;
//
//    private SessionManager sessionManager;
//
//    // Image
//    private Uri imageUri;
//    private ActivityResultLauncher<String> requestCameraPermission;
//    private ActivityResultLauncher<String> requestGalleryPermission;
//    private ActivityResultLauncher<Uri> cameraLauncher;
//    private ActivityResultLauncher<String> galleryLauncher;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_edit_profile);
//
//        Window window = getWindow();
//        window.setStatusBarColor(Color.parseColor("#696FC7"));
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        sessionManager = new SessionManager(this);
//
//        initViews();
//        setupTopBar();
//        setupStateDropdown();
//        initImagePickers();
//        loadSavedProfileImage();
//        setupValidationClear();
//        setupSaveClick();
//
//        // ✅ PREFILL FROM INTENT (NOT API)
//        loadProfileFromIntent();
//    }
//
//    // ---------------- INIT VIEWS ----------------
//    private void initViews() {
//
//        btnBack = findViewById(R.id.btnBack);
//        profileImage = findViewById(R.id.profileImage);
//        cameraIcon = findViewById(R.id.cameraIcon);
//        btnSave = findViewById(R.id.btnSave);
//
//        tilName = findViewById(R.id.tilName);
//        tilMobile = findViewById(R.id.tilMobile);
//        tilEmail = findViewById(R.id.tilEmail);
//        tilAddress1 = findViewById(R.id.tilAddress1);
//        tilAddress2 = findViewById(R.id.tilAddress2);
//        tilArea = findViewById(R.id.tilArea);
//        tilLandmark = findViewById(R.id.tilLandmark);
//        tilCity = findViewById(R.id.tilCity);
//        tilPincode = findViewById(R.id.tilPincode);
//        tilState = findViewById(R.id.tilState);
//
//        etName = findViewById(R.id.etName);
//        etMobile = findViewById(R.id.etMobile);
//        etEmail = findViewById(R.id.etEmail);
//        etAddress1 = findViewById(R.id.etAddress1);
//        etAddress2 = findViewById(R.id.etAddress2);
//        etArea = findViewById(R.id.etArea);
//        etLandmark = findViewById(R.id.etLandmark);
//        etCity = findViewById(R.id.etCity);
//        etPincode = findViewById(R.id.etPincode);
//        etState = findViewById(R.id.etState);
//    }
//
//    // ---------------- TOP BAR ----------------
//    private void setupTopBar() {
//        btnBack.setOnClickListener(v -> finish());
//        profileImage.setOnClickListener(v -> showImageSourceDialog());
//        cameraIcon.setOnClickListener(v -> showImageSourceDialog());
//    }
//
//    // ---------------- PREFILL FROM INTENT ----------------
//    private void loadProfileFromIntent() {
//        UserProfile user = getIntent().getParcelableExtra("user_profile");
//
//        if (user == null) {
//            Toast.makeText(this, "Profile data not found", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//
//        etName.setText(user.getCustName());
//        etMobile.setText(user.getCustMobile());
//        etEmail.setText(user.getEmail());
//        etAddress1.setText(user.getAddress1());
//        etAddress2.setText(user.getAddress());
//        etArea.setText(user.getArea());
//        etLandmark.setText(user.getLandmark());
//        etCity.setText(user.getCity());
//        etPincode.setText(user.getPincode());
//        etState.setText(user.getState(), false);
//    }
//
//    // ---------------- STATE DROPDOWN ----------------
//    private void setupStateDropdown() {
//        String[] states = {
//                "Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh",
//                "Goa","Gujarat","Haryana","Himachal Pradesh","Jharkhand",
//                "Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur",
//                "Meghalaya","Mizoram","Nagaland","Odisha","Punjab",
//                "Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura",
//                "Uttar Pradesh","Uttarakhand","West Bengal","Delhi",
//                "Jammu and Kashmir","Ladakh","Puducherry","Chandigarh"
//        };
//
//        etState.setAdapter(
//                new android.widget.ArrayAdapter<>(
//                        this,
//                        android.R.layout.simple_dropdown_item_1line,
//                        states
//                )
//        );
//    }
//
//    // ---------------- IMAGE ----------------
//    private void loadSavedProfileImage() {
//        Bitmap bitmap = ImagePrefManager.getImage(this);
//        if (bitmap != null) profileImage.setImageBitmap(bitmap);
//    }
//
//    private void initImagePickers() {
//
//        requestCameraPermission =
//                registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
//                    if (granted) openCamera();
//                });
//
//        requestGalleryPermission =
//                registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
//                    if (granted) openGallery();
//                });
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
//                .setTitle("Change Profile Photo")
//                .setItems(new String[]{"Camera", "Gallery"}, (d, i) -> {
//                    if (i == 0)
//                        requestCameraPermission.launch(Manifest.permission.CAMERA);
//                    else
//                        requestGalleryPermission.launch(
//                                Build.VERSION.SDK_INT >= 33
//                                        ? Manifest.permission.READ_MEDIA_IMAGES
//                                        : Manifest.permission.READ_EXTERNAL_STORAGE
//                        );
//                }).show();
//    }
//
//    private void openCamera() {
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, "Profile_Image");
//        imageUri = getContentResolver()
//                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
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
//        } catch (Exception ignored) {}
//    }
//
//    // ---------------- VALIDATION ----------------
//    private void setupValidationClear() {
//        etName.addTextChangedListener(SimpleTextWatcher.clearError(tilName));
//        etMobile.addTextChangedListener(SimpleTextWatcher.clearError(tilMobile));
//        etEmail.addTextChangedListener(SimpleTextWatcher.clearError(tilEmail));
//    }
//
//    private void setupSaveClick() {
//        btnSave.setOnClickListener(v -> {
//            if (validateForm()) {
//                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
//    }
//
//    private boolean validateForm() {
//
//        if (TextUtils.isEmpty(etName.getText().toString().trim())) {
//            tilName.setError("Name required");
//            return false;
//        }
//
//        if (TextUtils.isEmpty(etMobile.getText().toString().trim())
//                || etMobile.getText().length() != 10) {
//            tilMobile.setError("Valid mobile required");
//            return false;
//        }
//
//        if (!Patterns.EMAIL_ADDRESS.matcher(
//                etEmail.getText().toString().trim()).matches()) {
//            tilEmail.setError("Valid email required");
//            return false;
//        }
//
//        return true;
//    }
//}

package com.example.shoppingapp.EditProfileScreen;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.SimpleTextWatcher;
import com.example.shoppingapp.network.ApiClient;
import com.example.shoppingapp.network.UserApiService;
import com.example.shoppingapp.network.response.EditProfileResponse;
import com.example.shoppingapp.network.response.UserProfile;
import com.example.shoppingapp.utils.ImagePrefManager;
import com.example.shoppingapp.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EDIT_PROFILE";

    // UI
    private ImageButton btnBack;
    private ImageView profileImage, cameraIcon;
    private MaterialButton btnSave;

    private TextInputLayout tilName, tilMobile, tilEmail, tilAddress1, tilAddress2,
            tilArea, tilLandmark, tilCity, tilPincode, tilState;

    private TextInputEditText etName, etMobile, etEmail, etAddress1, etAddress2,
            etArea, etLandmark, etCity, etPincode;

    private MaterialAutoCompleteTextView etState;

    // Data
    private SessionManager sessionManager;
    private UserApiService apiService;
    private UserProfile userProfile;

    // Image
    private Uri imageUri;
    private ActivityResultLauncher<String> requestCameraPermission;
    private ActivityResultLauncher<String> requestGalleryPermission;
    private ActivityResultLauncher<Uri> cameraLauncher;
    private ActivityResultLauncher<String> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);

        Log.d(TAG, "onCreate started");

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.topBar));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                            | WindowInsetsCompat.Type.ime()
            );

            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );

            return WindowInsetsCompat.CONSUMED;
        });

        sessionManager = new SessionManager(this);
        apiService = ApiClient.getClient().create(UserApiService.class);

        initViews();
        setupTopBar();
        setupStateDropdown();
        initImagePickers();
        loadSavedProfileImage();
        setupValidationClear();

        loadProfileFromIntent();   // PREFILL
        setupSaveClick();          // API CALL
    }

    // ---------------- INIT ----------------
    private void initViews() {
        Log.d(TAG, "initViews");

        btnBack = findViewById(R.id.btnBack);
        profileImage = findViewById(R.id.profileImage);
        cameraIcon = findViewById(R.id.cameraIcon);
        btnSave = findViewById(R.id.btnSave);

        tilName = findViewById(R.id.tilName);
        tilMobile = findViewById(R.id.tilMobile);
        tilEmail = findViewById(R.id.tilEmail);
        tilAddress1 = findViewById(R.id.tilAddress1);
        tilAddress2 = findViewById(R.id.tilAddress2);
        tilArea = findViewById(R.id.tilArea);
        tilLandmark = findViewById(R.id.tilLandmark);
        tilCity = findViewById(R.id.tilCity);
        tilPincode = findViewById(R.id.tilPincode);
        tilState = findViewById(R.id.tilState);

        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        etEmail = findViewById(R.id.etEmail);
        etAddress1 = findViewById(R.id.etAddress1);
        etAddress2 = findViewById(R.id.etAddress2);
        etArea = findViewById(R.id.etArea);
        etLandmark = findViewById(R.id.etLandmark);
        etCity = findViewById(R.id.etCity);
        etPincode = findViewById(R.id.etPincode);
        etState = findViewById(R.id.etState);

        NestedScrollView scrollView = findViewById(R.id.scrollContent);

        int extraOffset =
                (int) (24 * getResources().getDisplayMetrics().density);

        View.OnFocusChangeListener focusListener = (v, hasFocus) -> {
            if (hasFocus) {
                scrollView.post(() ->
                        scrollView.smoothScrollTo(0, v.getBottom() + extraOffset)
                );
            }
        };


// Attach to problematic fields
        etCity.setOnFocusChangeListener(focusListener);
        etPincode.setOnFocusChangeListener(focusListener);
        etArea.setOnFocusChangeListener(focusListener);
        etState.setOnFocusChangeListener(focusListener);

    }

    // ---------------- TOP BAR ----------------
    private void setupTopBar() {
        Log.d(TAG, "setupTopBar");

        btnBack.setOnClickListener(v -> finish());
        profileImage.setOnClickListener(v -> showImageSourceDialog());
        cameraIcon.setOnClickListener(v -> showImageSourceDialog());
    }

    // ---------------- PREFILL FROM INTENT ----------------
    private void loadProfileFromIntent() {
        Log.d(TAG, "loadProfileFromIntent");

        userProfile = getIntent().getParcelableExtra("user_profile");

        if (userProfile == null) {
            Log.e(TAG, "UserProfile is NULL from intent");
            Toast.makeText(this, "Profile data not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Log.d(TAG, "Prefilling data for userId=" + userProfile.getuId());

        etName.setText(userProfile.getCustName());
        etMobile.setText(userProfile.getCustMobile());
        etEmail.setText(userProfile.getEmail());
        etAddress1.setText(userProfile.getAddress1());
        etAddress2.setText(userProfile.getAddress());
        etArea.setText(userProfile.getArea());
        etLandmark.setText(userProfile.getLandmark());
        etCity.setText(userProfile.getCity());
        etPincode.setText(userProfile.getPincode());
        etState.setText(userProfile.getState(), false);
    }

    // ---------------- STATE DROPDOWN ----------------
    private void setupStateDropdown() {
        Log.d(TAG, "setupStateDropdown");

        String[] states = {
                "Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh",
                "Goa","Gujarat","Haryana","Himachal Pradesh","Jharkhand",
                "Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur",
                "Meghalaya","Mizoram","Nagaland","Odisha","Punjab",
                "Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura",
                "Uttar Pradesh","Uttarakhand","West Bengal","Delhi",
                "Jammu and Kashmir","Ladakh","Puducherry","Chandigarh"
        };

        etState.setAdapter(new android.widget.ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                states
        ));
    }

    // ---------------- SAVE → API ----------------
    private void setupSaveClick() {
        btnSave.setOnClickListener(v -> {

            Log.d(TAG, "Save button clicked");

            if (!validateForm()) {
                Log.w(TAG, "Validation failed");
                return;
            }

            String editedOn = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss",
                    Locale.getDefault()
            ).format(new Date());

            Log.d(TAG, "Calling updateUserProfile API | edited_on=" + editedOn);

            Call<EditProfileResponse> call =
                    apiService.updateUserProfile(
                            sessionManager.getUniqueId(),
                            userProfile.getuId(),
                            etName.getText().toString().trim(),
                            etMobile.getText().toString().trim(),
                            etEmail.getText().toString().trim(),
                            etAddress1.getText().toString().trim(),
                            etAddress2.getText().toString().trim(),
                            etCity.getText().toString().trim(),
                            etArea.getText().toString().trim(),
                            etLandmark.getText().toString().trim(),
                            etState.getText().toString().trim(),
                            etPincode.getText().toString().trim(),
                            editedOn   // ✅ FIX
                    );

            call.enqueue(new Callback<EditProfileResponse>() {
                @Override
                public void onResponse(Call<EditProfileResponse> call,
                                       Response<EditProfileResponse> response) {

                    Log.d(TAG, "API Response received");

                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "Status=" + response.body().getStatus()
                                + " Message=" + response.body().getMessage());

                        if ("success".equalsIgnoreCase(response.body().getStatus())) {
                            Toast.makeText(EditProfileActivity.this,
                                    "Profile updated successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(EditProfileActivity.this,
                                    response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e(TAG, "API failed or empty body");
                        Toast.makeText(EditProfileActivity.this,
                                "Update failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<EditProfileResponse> call, Throwable t) {
                    Log.e(TAG, "API Error", t);
                    Toast.makeText(EditProfileActivity.this,
                            "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
    // ---------------- VALIDATION ----------------
    private void setupValidationClear() {
        etName.addTextChangedListener(SimpleTextWatcher.clearError(tilName));
        etMobile.addTextChangedListener(SimpleTextWatcher.clearError(tilMobile));
        etEmail.addTextChangedListener(SimpleTextWatcher.clearError(tilEmail));
    }

    private boolean validateForm() {
        if (TextUtils.isEmpty(etName.getText())) {
            tilName.setError("Name required");
            return false;
        }
        if (TextUtils.isEmpty(etMobile.getText()) || etMobile.getText().length() != 10) {
            tilMobile.setError("Valid mobile required");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            tilEmail.setError("Valid email required");
            return false;
        }
        return true;
    }

    // ---------------- IMAGE ----------------
    private void loadSavedProfileImage() {
        Bitmap bitmap = ImagePrefManager.getImage(this);
        if (bitmap != null) profileImage.setImageBitmap(bitmap);
    }

    private void initImagePickers() {

        requestCameraPermission =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                        granted -> { if (granted) openCamera(); });

        requestGalleryPermission =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                        granted -> { if (granted) openGallery(); });

        cameraLauncher =
                registerForActivityResult(new ActivityResultContracts.TakePicture(),
                        success -> { if (success && imageUri != null) saveImage(imageUri); });

        galleryLauncher =
                registerForActivityResult(new ActivityResultContracts.GetContent(),
                        uri -> { if (uri != null) saveImage(uri); });
    }

    private void showImageSourceDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Change Profile Photo")
                .setItems(new String[]{"Camera", "Gallery"}, (d, i) -> {
                    if (i == 0)
                        requestCameraPermission.launch(Manifest.permission.CAMERA);
                    else
                        requestGalleryPermission.launch(
                                Build.VERSION.SDK_INT >= 33
                                        ? Manifest.permission.READ_MEDIA_IMAGES
                                        : Manifest.permission.READ_EXTERNAL_STORAGE
                        );
                }).show();
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
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            ImagePrefManager.saveImage(this, bitmap);
        } catch (Exception e) {
            Log.e(TAG, "Image save failed", e);
        }
    }
}
