//
//package com.example.shoppingapp.auth.forgotpassword;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.Window;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.shoppingapp.R;
//import com.example.shoppingapp.network.ApiClient;
//import com.example.shoppingapp.network.ApiService;
//import com.example.shoppingapp.network.OtpApiService;
//import com.example.shoppingapp.network.response.ForgotPassResponse;
//import com.example.shoppingapp.network.response.MobileCheckResponse;
//import com.example.shoppingapp.network.response.OtpResponse;
//import com.example.shoppingapp.network.response.OtpVerifyResponse;
//import com.google.android.material.button.MaterialButton;
//import com.google.android.material.dialog.MaterialAlertDialogBuilder;
//import com.google.android.material.textfield.TextInputEditText;
//import com.google.android.material.textfield.TextInputLayout;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class ForgotPasswordActivity extends AppCompatActivity {
//
//    private static final String TAG = "ForgotPasswordAPI";
//
//    /* -------- UI -------- */
//    private ImageButton backButton;
//    private TextInputLayout tilMobile,tilNewPassword, tilConfirmPassword;
//    private TextInputEditText etMobile;
//    private MaterialButton btnSendOtp;
//    private View otpCard;
//    private EditText[] otpFields;
//    private MaterialButton verifyOtpBtn, resendOtpBtn;
//
//    /* -------- RESET PASSWORD UI -------- */
//    private View resetPassCard;
//    private TextInputEditText etNewPassword, etConfirmPassword;
//    private MaterialButton btnResetPassword;
//
//    /* -------- FLAGS -------- */
//    private boolean isOtpVerified = false;
//
//    /* -------- DATA -------- */
//    private String custId;
//
//    /* -------- API -------- */
//    private ApiService apiService;
//    private OtpApiService otpApiService;
//    /* -------- RESEND TIMER -------- */
//    private CountDownTimer resendTimer;
//    private static final int RESEND_DELAY_SEC = 30;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        Window window = getWindow();
//        // Status bar color
//        window.setStatusBarColor(Color.parseColor("#696FC7"));
//        setContentView(R.layout.activity_forgotpassword);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
//            return insets;
//        });
//
//        apiService = ApiClient.getClient().create(ApiService.class);
//        otpApiService = ApiClient.getClient().create(OtpApiService.class);
//
//        initViews();
//        setupMobileValidation();
//        setupPasswordValidation();
//        setupListeners();
//
//        Log.d(TAG, "ForgotPasswordActivity initialized");
//    }
//
//    /* -------- INIT -------- */
//    private void initViews() {
//
//        backButton = findViewById(R.id.backButton);
//        tilMobile = findViewById(R.id.tilMobile);
//        tilNewPassword = findViewById(R.id.tilNewPassword);
//        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);
//        etMobile = findViewById(R.id.etMobile);
//        btnSendOtp = findViewById(R.id.btnSendOtp);
//
//
//        otpCard = findViewById(R.id.otpCard);
//        otpCard.setVisibility(View.GONE);
//
//        otpFields = new EditText[]{
//                otpCard.findViewById(R.id.otp1),
//                otpCard.findViewById(R.id.otp2),
//                otpCard.findViewById(R.id.otp3),
//                otpCard.findViewById(R.id.otp4),
//                otpCard.findViewById(R.id.otp5),
//                otpCard.findViewById(R.id.otp6)
//        };
//
//        verifyOtpBtn = otpCard.findViewById(R.id.verifyOtpBtn);
//        resendOtpBtn = otpCard.findViewById(R.id.resendOtpBtn);
//
//        btnSendOtp.setVisibility(View.GONE);
//
//        resetPassCard = findViewById(R.id.resetPassCard);
//        etNewPassword = findViewById(R.id.etNewPassword);
//        etConfirmPassword = findViewById(R.id.etConfirmPassword);
//        btnResetPassword = findViewById(R.id.btnResetPassword);
//        btnResetPassword.setEnabled(false);   // ✅ SAFE
//
//        resetPassCard.setVisibility(View.GONE);
//
//        setOtpFieldBehaviors();
//    }
//
//    /* -------- MOBILE VALIDATION -------- */
//    private void setupMobileValidation() {
//
//        etMobile.addTextChangedListener(simpleWatcher(() -> {
//
//            isOtpVerified = false;
//            otpCard.setVisibility(View.GONE);
//            resetPassCard.setVisibility(View.GONE);
//
//
//            // 🔥 RESET RESEND TIMER WHEN MOBILE CHANGES
//            if (resendTimer != null) {
//                resendTimer.cancel();
//                resendTimer = null;
//            }
//
//            String mobile = etMobile.getText().toString().trim();
//            Log.d(TAG, "Mobile input changed: " + mobile);
//
//            if (!mobile.matches("^[6-9][0-9]{9}$")) {
//                showError(tilMobile, "Enter valid 10-digit mobile number");
//                btnSendOtp.setVisibility(View.GONE);
//                return;
//            }
//
//            clearError(tilMobile);
//            checkMobileExistsApi(mobile);
//        }));
//    }
//
//    /* -------- PASSWORD VALIDATION -------- */
//    private void setupPasswordValidation() {
//
//        etNewPassword.addTextChangedListener(
//                simpleWatcher(this::validatePasswords)
//        );
//
//        etConfirmPassword.addTextChangedListener(
//                simpleWatcher(this::validatePasswords)
//        );
//    }
//
//    private void validatePasswords() {
//
//        String pass = etNewPassword.getText().toString();
//        String confirm = etConfirmPassword.getText().toString();
//
//        // 1️⃣ If confirm is typed before password
//        if (!TextUtils.isEmpty(confirm) && TextUtils.isEmpty(pass)) {
//            showError(tilConfirmPassword, "Enter password first");
//            btnResetPassword.setEnabled(false);
//            return;
//        } else {
//            clearError(tilConfirmPassword);
//        }
//
//        // 2️⃣ Password length validation
//        if (!TextUtils.isEmpty(pass) && pass.length() < 6) {
//            showError(tilNewPassword, "Minimum 6 characters required");
//            btnResetPassword.setEnabled(false);
//            return;
//        } else {
//            clearError(tilNewPassword);
//        }
//
//        // 3️⃣ Confirm password empty
//        if (!TextUtils.isEmpty(pass) && TextUtils.isEmpty(confirm)) {
//            showError(tilConfirmPassword, "Confirm your password");
//            btnResetPassword.setEnabled(false);
//            return;
//        }
//
//        // 4️⃣ Passwords match check
//        if (!TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirm)
//                && !pass.equals(confirm)) {
//            showError(tilConfirmPassword, "Passwords do not match");
//            btnResetPassword.setEnabled(false);
//            return;
//        }
//
//        // 5️⃣ Everything valid
//        clearError(tilConfirmPassword);
//        btnResetPassword.setEnabled(isOtpVerified);
//    }
//
//
//    /* -------- LISTENERS -------- */
//    private void setupListeners() {
//
//        backButton.setOnClickListener(v -> finish());
//
//        btnSendOtp.setOnClickListener(v -> sendOtp());
//        verifyOtpBtn.setOnClickListener(v -> verifyOtpApi());
//        resendOtpBtn.setOnClickListener(v -> sendOtp());
//
//        btnResetPassword.setOnClickListener(v -> resetPassword());
//    }
//
//    /* -------- CHECK MOBILE -------- */
//    private void checkMobileExistsApi(String mobile) {
//
//        apiService.checkMobile(mobile).enqueue(new Callback<MobileCheckResponse>() {
//            @Override
//            public void onResponse(Call<MobileCheckResponse> call,
//                                   Response<MobileCheckResponse> response) {
//
//                if (response.isSuccessful() && response.body() != null
//                        && response.body().isStatus()) {
//
//                    custId = response.body().getData().getId();
//                    btnSendOtp.setVisibility(View.VISIBLE);
//
//                } else {
//                    showError(tilMobile, "Mobile not registered");
//                    btnSendOtp.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MobileCheckResponse> call, Throwable t) {
//                showError(tilMobile, "Unable to verify mobile");
//            }
//        });
//    }
//
//    /* -------- SEND OTP (91 REQUIRED) -------- */
//    private void sendOtp() {
//
//        String fullMobile = "91" + etMobile.getText().toString().trim();
//        resendOtpBtn.setVisibility(View.VISIBLE);
//        resendOtpBtn.setEnabled(false);
//
//        otpApiService.generateOtp(fullMobile).enqueue(new Callback<OtpResponse>() {
//            @Override
//            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
//                if (response.isSuccessful() && response.body() != null
//                        && "success".equalsIgnoreCase(response.body().getStatus())) {
//                    // 🔥 SHOW OTP SENT POPUP
//                    showOtpSentPopup();
//
//                    btnSendOtp.setVisibility(View.GONE);
//
//                    otpCard.setVisibility(View.VISIBLE);
//                    clearOtpFields();
//                    otpFields[0].requestFocus();
//                    startResendTimer();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OtpResponse> call, Throwable t) {
//                Toast.makeText(ForgotPasswordActivity.this,
//                        "OTP failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    /* -------- RESEND OTP TIMER -------- */
//    private void startResendTimer() {
//
//        if (resendTimer != null) {
//            resendTimer.cancel();
//            resendTimer = null;
//        }
//
//        resendOtpBtn.setEnabled(false);
//        resendOtpBtn.setText("Resend OTP in 30s");
//
//        resendTimer = new CountDownTimer(RESEND_DELAY_SEC * 1000L, 1000) {
//
//            @Override
//            public void onTick(long millisUntilFinished) {
//                resendOtpBtn.setText(
//                        "Resend OTP in " + (millisUntilFinished / 1000) + "s"
//                );
//            }
//
//            @Override
//            public void onFinish() {
//                resendOtpBtn.setEnabled(true);
//                resendOtpBtn.setText("Resend OTP");
//            }
//        }.start();
//    }
//    /* -------- OTP SENT DIALOG -------- */
//    private void showOtpSentPopup() {
//        View dialogView = getLayoutInflater()
//                .inflate(R.layout.dialog_otp_sent, null, false);
//
//        MaterialAlertDialogBuilder builder =
//                new MaterialAlertDialogBuilder(this)
//                        .setView(dialogView)
//                        .setCancelable(false);
//
//        androidx.appcompat.app.AlertDialog dialog = builder.create();
//
//        if (dialog.getWindow() != null)
//            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//        dialogView.findViewById(R.id.okBtn)
//                .setOnClickListener(v -> dialog.dismiss());
//
//        dialog.show();
//    }
//    /* -------- OTP VERIFIED SUCCESS DIALOG -------- */
//    private void showOtpVerifiedPopup() {
//        View dialogView = getLayoutInflater()
//                .inflate(R.layout.dialog_otp_verified, null, false);
//
//        MaterialAlertDialogBuilder builder =
//                new MaterialAlertDialogBuilder(this)
//                        .setView(dialogView)
//                        .setCancelable(false);
//
//        androidx.appcompat.app.AlertDialog dialog = builder.create();
//
//        if (dialog.getWindow() != null)
//            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//        dialogView.findViewById(R.id.okBtn)
//                .setOnClickListener(v -> dialog.dismiss());
//
//        dialog.show();
//    }
//
//
//    /* -------- VERIFY OTP (91 REQUIRED) -------- */
//    private void verifyOtpApi() {
//
//        StringBuilder otpBuilder = new StringBuilder();
//        for (EditText e : otpFields) otpBuilder.append(e.getText().toString().trim());
//
//        if (otpBuilder.length() != 6) {
//            Toast.makeText(this, "Enter full OTP", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String mobile = "91" + etMobile.getText().toString().trim();
//        String otp = otpBuilder.toString();
//        String datetime = new SimpleDateFormat(
//                "yyyy-MM-dd HH:mm:ss", Locale.getDefault()
//        ).format(new Date());
//
//        otpApiService.verifyOtp(mobile, otp, datetime)
//                .enqueue(new Callback<OtpVerifyResponse>() {
//                    @Override
//                    public void onResponse(Call<OtpVerifyResponse> call,
//                                           Response<OtpVerifyResponse> response) {
//
//                        if (response.isSuccessful() && response.body() != null
//                                && "success".equalsIgnoreCase(response.body().getStatus())) {
//                            isOtpVerified = true;
//                            showOtpVerifiedPopup();
//                            // 🔥 STOP TIMER AFTER OTP VERIFIED
//                            if (resendTimer != null) {
//                                resendTimer.cancel();
//                                resendTimer = null;
//                            }
//                            otpCard.setVisibility(View.GONE);
//                            resetPassCard.setVisibility(View.VISIBLE);
//
//                            validatePasswords(); // 🔥 IMPORTANT
//                        } else {
//                            // ❌ WRONG OTP
//                            showOtpError();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<OtpVerifyResponse> call, Throwable t) {
//                        Toast.makeText(ForgotPasswordActivity.this,
//                                "OTP verification failed",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    /* -------- RESET PASSWORD (NO 91 / +91) -------- */
//    private void resetPassword() {
//
//        if (!btnResetPassword.isEnabled()) {
//            Toast.makeText(this, "Fix password errors", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String newPass = etNewPassword.getText().toString().trim();
//        String mobile = etMobile.getText().toString().trim(); // ✅ plain 10-digit
//
//
//        otpApiService.forgotPassword(mobile, custId, newPass)
//                .enqueue(new Callback<ForgotPassResponse>() {
//                    @Override
//                    public void onResponse(Call<ForgotPassResponse> call,
//                                           Response<ForgotPassResponse> response) {
//
//                        if (response.isSuccessful()
//                                && response.body() != null
//                                && response.body().isStatus()) {
//
//                            Toast.makeText(ForgotPasswordActivity.this,
//                                    response.body().getMessage(),
//                                    Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ForgotPassResponse> call, Throwable t) {
//                        Toast.makeText(ForgotPasswordActivity.this,
//                                "Password update failed",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    /* -------- HELPERS -------- */
//    private void showError(TextInputLayout til, String msg) {
//        til.setError(msg);
//        til.setErrorEnabled(true);
//    }
//
//    private void clearError(TextInputLayout til) {
//        til.setError(null);
//        til.setErrorEnabled(false);
//    }
//
//    private TextWatcher simpleWatcher(Runnable r) {
//        return new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int a, int b, int c) {
//            }
//
//            public void onTextChanged(CharSequence s, int a, int b, int c) {
//                r.run();
//            }
//
//            public void afterTextChanged(Editable s) {
//            }
//        };
//    }
//
//    private void clearOtpFields() {
//        for (EditText e : otpFields) e.setText("");
//    }
//
//    private void setOtpFieldBehaviors() {
//
//        for (int i = 0; i < otpFields.length; i++) {
//            final int index = i;
//
//            // 👉 Move forward when digit entered
//            otpFields[i].addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    if (s.length() == 1 && index < otpFields.length - 1) {
//                        otpFields[index + 1].requestFocus();
//                    }
//                }
//            });
//
//            // 👉 Handle BACKSPACE / CUT
//            otpFields[i].setOnKeyListener((v, keyCode, event) -> {
//                if (event.getAction() == KeyEvent.ACTION_DOWN &&
//                        keyCode == KeyEvent.KEYCODE_DEL) {
//
//                    if (otpFields[index].getText().toString().isEmpty()
//                            && index > 0) {
//
//                        otpFields[index - 1].requestFocus();
//                        otpFields[index - 1].setText("");
//                    }
//                }
//                return false;
//            });
//        }
//    }
//    private void showOtpError() {
//        Toast.makeText(this, "Invalid OTP. Please try again.", Toast.LENGTH_SHORT).show();
//        clearOtpFields();
//        otpFields[0].requestFocus();
//    }
//    /* -------- CLEANUP -------- */
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (resendTimer != null) resendTimer.cancel(); // 🔥 ADDED
//    }
//}



package com.example.shoppingapp.auth.forgotpassword;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shoppingapp.R;
import com.example.shoppingapp.network.ApiClient;
import com.example.shoppingapp.network.ApiService;
import com.example.shoppingapp.network.OtpApiService;
import com.example.shoppingapp.network.UserApiService;
import com.example.shoppingapp.network.response.ForgotPassResponse;
import com.example.shoppingapp.network.response.MobileCheckResponse;
import com.example.shoppingapp.network.response.OtpGenerationResponse;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private static final String TAG = "ForgotPasswordAPI";

    /* -------- UI -------- */
    private ImageButton backButton;
    private TextInputLayout tilMobile,tilNewPassword, tilConfirmPassword;
    private TextInputEditText etMobile;
    private MaterialButton btnSendOtp;
    private View otpCard;
    private EditText[] otpFields;
    private MaterialButton verifyOtpBtn, resendOtpBtn;

    /* -------- RESET PASSWORD UI -------- */
    private View resetPassCard;
    private TextInputEditText etNewPassword, etConfirmPassword;
    private MaterialButton btnResetPassword;

    /* -------- FLAGS -------- */
    private boolean isOtpVerified = false;

    /* -------- DATA -------- */
    private String custId;

    /* -------- API -------- */
    private ApiService apiService;
    private OtpApiService otpApiService;
    private UserApiService userApiService;

    /* -------- RESEND TIMER -------- */
    private CountDownTimer resendTimer;
    private static final int RESEND_DELAY_SEC = 30;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Window window = getWindow();
        // Status bar color
        window.setStatusBarColor(Color.parseColor("#696FC7"));
        setContentView(R.layout.activity_forgotpassword);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        apiService = ApiClient.getClient().create(ApiService.class);
        otpApiService = ApiClient.getClient().create(OtpApiService.class);
        userApiService = ApiClient.getClient().create(UserApiService.class);


        initViews();
        setupMobileValidation();
        setupPasswordValidation();
        setupListeners();

        Log.d(TAG, "ForgotPasswordActivity initialized");
    }

    /* -------- INIT -------- */
    private void initViews() {

        backButton = findViewById(R.id.backButton);
        tilMobile = findViewById(R.id.tilMobile);
        tilNewPassword = findViewById(R.id.tilNewPassword);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);
        etMobile = findViewById(R.id.etMobile);
        btnSendOtp = findViewById(R.id.btnSendOtp);


        otpCard = findViewById(R.id.otpCard);
        otpCard.setVisibility(View.GONE);

        otpFields = new EditText[]{
                otpCard.findViewById(R.id.otp1),
                otpCard.findViewById(R.id.otp2),
                otpCard.findViewById(R.id.otp3),
                otpCard.findViewById(R.id.otp4),
                otpCard.findViewById(R.id.otp5),
                otpCard.findViewById(R.id.otp6)
        };

        verifyOtpBtn = otpCard.findViewById(R.id.verifyOtpBtn);
        resendOtpBtn = otpCard.findViewById(R.id.resendOtpBtn);

        btnSendOtp.setVisibility(View.GONE);

        resetPassCard = findViewById(R.id.resetPassCard);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        btnResetPassword.setEnabled(false);   // ✅ SAFE

        resetPassCard.setVisibility(View.GONE);

        setOtpFieldBehaviors();
    }

    /* -------- MOBILE VALIDATION -------- */
    private void setupMobileValidation() {

        etMobile.addTextChangedListener(simpleWatcher(() -> {

            isOtpVerified = false;
            otpCard.setVisibility(View.GONE);
            resetPassCard.setVisibility(View.GONE);


            // 🔥 RESET RESEND TIMER WHEN MOBILE CHANGES
            if (resendTimer != null) {
                resendTimer.cancel();
                resendTimer = null;
            }

            String mobile = etMobile.getText().toString().trim();
            Log.d(TAG, "Mobile input changed: " + mobile);

            if (!mobile.matches("^[6-9][0-9]{9}$")) {
                showError(tilMobile, "Enter valid 10-digit mobile number");
                btnSendOtp.setVisibility(View.GONE);
                return;
            }

            clearError(tilMobile);
            checkMobileExistsApi(mobile);
        }));
    }

    /* -------- PASSWORD VALIDATION -------- */
    private void setupPasswordValidation() {

        etNewPassword.addTextChangedListener(
                simpleWatcher(this::validatePasswords)
        );

        etConfirmPassword.addTextChangedListener(
                simpleWatcher(this::validatePasswords)
        );
    }

    private void validatePasswords() {

        String pass = etNewPassword.getText().toString();
        String confirm = etConfirmPassword.getText().toString();

        // 1️⃣ If confirm is typed before password
        if (!TextUtils.isEmpty(confirm) && TextUtils.isEmpty(pass)) {
            showError(tilConfirmPassword, "Enter password first");
            btnResetPassword.setEnabled(false);
            return;
        } else {
            clearError(tilConfirmPassword);
        }

        // 2️⃣ Password length validation
        if (!TextUtils.isEmpty(pass) && pass.length() < 6) {
            showError(tilNewPassword, "Minimum 6 characters required");
            btnResetPassword.setEnabled(false);
            return;
        } else {
            clearError(tilNewPassword);
        }

        // 3️⃣ Confirm password empty
        if (!TextUtils.isEmpty(pass) && TextUtils.isEmpty(confirm)) {
            showError(tilConfirmPassword, "Confirm your password");
            btnResetPassword.setEnabled(false);
            return;
        }

        // 4️⃣ Passwords match check
        if (!TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirm)
                && !pass.equals(confirm)) {
            showError(tilConfirmPassword, "Passwords do not match");
            btnResetPassword.setEnabled(false);
            return;
        }

        // 5️⃣ Everything valid
        clearError(tilConfirmPassword);
        btnResetPassword.setEnabled(isOtpVerified);
    }


    /* -------- LISTENERS -------- */
    private void setupListeners() {

        backButton.setOnClickListener(v -> finish());

        btnSendOtp.setOnClickListener(v -> {
            if (TextUtils.isEmpty(custId)) {
                Toast.makeText(
                        ForgotPasswordActivity.this,
                        "Mobile not registered",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }
            sendOtp();
        });

        verifyOtpBtn.setOnClickListener(v -> verifyOtpApi());
        resendOtpBtn.setOnClickListener(v -> sendOtp());

        btnResetPassword.setOnClickListener(v -> resetPassword());
    }

    /* -------- CHECK MOBILE -------- */
    private void checkMobileExistsApi(String mobile) {

        userApiService.checkMobile(mobile)
                .enqueue(new Callback<MobileCheckResponse>() {

                    @Override
                    public void onResponse(Call<MobileCheckResponse> call,
                                           Response<MobileCheckResponse> response) {

                        if (!response.isSuccessful() || response.body() == null) {
                            showError(tilMobile, "Server error");
                            btnSendOtp.setVisibility(View.GONE);
                            return;
                        }

                        MobileCheckResponse res = response.body();

                        Log.d(TAG, "MobileCheck -> status=" + res.isStatus());

                        if (res.isStatus()) {
                            // ✅ Mobile exists → allow OTP
                            clearError(tilMobile);
                            custId = res.getUniqueId();   // ✅ FIXED
                            btnSendOtp.setVisibility(View.VISIBLE);
                        } else {
                            // ❌ Mobile not registered
                            showError(tilMobile, "Mobile not registered");
                            btnSendOtp.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<MobileCheckResponse> call, Throwable t) {
                        showError(tilMobile, "Network error");
                        btnSendOtp.setVisibility(View.GONE);
                    }
                });
    }


    /* -------- SEND OTP (91 REQUIRED) -------- */
    private void sendOtp() {

        String fullMobile = "91" + etMobile.getText().toString().trim();

        resendOtpBtn.setVisibility(View.VISIBLE);
        resendOtpBtn.setEnabled(false);

        otpApiService.sendOtp(fullMobile)
                .enqueue(new Callback<OtpGenerationResponse>() {
                    @Override
                    public void onResponse(Call<OtpGenerationResponse> call,
                                           Response<OtpGenerationResponse> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && ("1".equals(response.body().getStatus())
                                || "success".equalsIgnoreCase(response.body().getStatus()))) {

                            showOtpSentPopup();
                            btnSendOtp.setVisibility(View.GONE);

                            otpCard.setVisibility(View.VISIBLE);
                            clearOtpFields();
                            otpFields[0].requestFocus();
                            startResendTimer();

                        } else {
                            Toast.makeText(
                                    ForgotPasswordActivity.this,
                                    response.body() != null
                                            ? response.body().getMessage()
                                            : "OTP failed",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OtpGenerationResponse> call, Throwable t) {
                        Toast.makeText(
                                ForgotPasswordActivity.this,
                                "OTP request failed",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });

    }

    /* -------- RESEND OTP TIMER -------- */
    private void startResendTimer() {

        if (resendTimer != null) {
            resendTimer.cancel();
            resendTimer = null;
        }

        resendOtpBtn.setEnabled(false);
        resendOtpBtn.setText("Resend OTP in 30s");

        resendTimer = new CountDownTimer(RESEND_DELAY_SEC * 1000L, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                resendOtpBtn.setText(
                        "Resend OTP in " + (millisUntilFinished / 1000) + "s"
                );
            }

            @Override
            public void onFinish() {
                resendOtpBtn.setEnabled(true);
                resendOtpBtn.setText("Resend OTP");
            }
        }.start();
    }
    /* -------- OTP SENT DIALOG -------- */
    private void showOtpSentPopup() {
        View dialogView = getLayoutInflater()
                .inflate(R.layout.dialog_otp_sent, null, false);

        MaterialAlertDialogBuilder builder =
                new MaterialAlertDialogBuilder(this)
                        .setView(dialogView)
                        .setCancelable(false);

        androidx.appcompat.app.AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialogView.findViewById(R.id.okBtn)
                .setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
    /* -------- OTP VERIFIED SUCCESS DIALOG -------- */
    private void showOtpVerifiedPopup() {
        View dialogView = getLayoutInflater()
                .inflate(R.layout.dialog_otp_verified, null, false);

        MaterialAlertDialogBuilder builder =
                new MaterialAlertDialogBuilder(this)
                        .setView(dialogView)
                        .setCancelable(false);

        androidx.appcompat.app.AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialogView.findViewById(R.id.okBtn)
                .setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }


    /* -------- VERIFY OTP (91 REQUIRED) -------- */
    private void verifyOtpApi() {

        StringBuilder otpBuilder = new StringBuilder();
        for (EditText e : otpFields) {
            otpBuilder.append(e.getText().toString().trim());
        }

        if (otpBuilder.length() != 6) {
            Toast.makeText(this, "Enter full OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        String mobile = "91" + etMobile.getText().toString().trim();
        String otp = otpBuilder.toString();
        String datetime = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault()
        ).format(new Date());

        otpApiService.validateOtp(mobile, otp, datetime)
                .enqueue(new Callback<OtpGenerationResponse>() {
                    @Override
                    public void onResponse(Call<OtpGenerationResponse> call,
                                           Response<OtpGenerationResponse> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && ("1".equals(response.body().getStatus())
                                || "success".equalsIgnoreCase(response.body().getStatus()))) {

                            isOtpVerified = true;
                            showOtpVerifiedPopup();

                            if (resendTimer != null) {
                                resendTimer.cancel();
                                resendTimer = null;
                            }

                            otpCard.setVisibility(View.GONE);
                            resetPassCard.setVisibility(View.VISIBLE);
                            validatePasswords(); // 🔥 IMPORTANT

                        } else {
                            showOtpError();
                        }
                    }

                    @Override
                    public void onFailure(Call<OtpGenerationResponse> call, Throwable t) {
                        Toast.makeText(
                                ForgotPasswordActivity.this,
                                "OTP verification failed",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }


    /* -------- RESET PASSWORD (NO 91 / +91) -------- */
    private void resetPassword() {

        if (!btnResetPassword.isEnabled()) {
            Toast.makeText(this, "Fix password errors", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = etNewPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim(); // 10-digit
        String companyId = custId; // unique_id from mobile check

        otpApiService.forgotPassword(
                mobile,
                companyId,
                password,
                confirmPassword
        ).enqueue(new Callback<ForgotPassResponse>() {
            @Override
            public void onResponse(Call<ForgotPassResponse> call,
                                   Response<ForgotPassResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    if (response.body().isStatus()) {

                        Toast.makeText(
                                ForgotPasswordActivity.this,
                                response.body().getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                        finish(); // back to login

                    } else {
                        Toast.makeText(
                                ForgotPasswordActivity.this,
                                response.body().getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotPassResponse> call, Throwable t) {
                Toast.makeText(
                        ForgotPasswordActivity.this,
                        "Password update failed",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    /* -------- HELPERS -------- */
    private void showError(TextInputLayout til, String msg) {
        til.setError(msg);
        til.setErrorEnabled(true);
    }

    private void clearError(TextInputLayout til) {
        til.setError(null);
        til.setErrorEnabled(false);
    }

    private TextWatcher simpleWatcher(Runnable r) {
        return new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int a, int b, int c) {
            }

            public void onTextChanged(CharSequence s, int a, int b, int c) {
                r.run();
            }

            public void afterTextChanged(Editable s) {
            }
        };
    }

    private void clearOtpFields() {
        for (EditText e : otpFields) e.setText("");
    }

    private void setOtpFieldBehaviors() {

        for (int i = 0; i < otpFields.length; i++) {
            final int index = i;

            // 👉 Move forward when digit entered
            otpFields[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1 && index < otpFields.length - 1) {
                        otpFields[index + 1].requestFocus();
                    }
                }
            });

            // 👉 Handle BACKSPACE / CUT
            otpFields[i].setOnKeyListener((v, keyCode, event) -> {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_DEL) {

                    if (otpFields[index].getText().toString().isEmpty()
                            && index > 0) {

                        otpFields[index - 1].requestFocus();
                        otpFields[index - 1].setText("");
                    }
                }
                return false;
            });
        }
    }
    private void showOtpError() {
        Toast.makeText(this, "Invalid OTP. Please try again.", Toast.LENGTH_SHORT).show();
        clearOtpFields();
        otpFields[0].requestFocus();
    }
    /* -------- CLEANUP -------- */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (resendTimer != null) resendTimer.cancel(); // 🔥 ADDED
    }
}
