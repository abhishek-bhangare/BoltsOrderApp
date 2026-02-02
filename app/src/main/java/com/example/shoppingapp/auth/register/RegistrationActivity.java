
package com.example.shoppingapp.auth.register;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shoppingapp.LoginScreen.LoginActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.network.ApiClient;
import com.example.shoppingapp.network.ApiService;
import com.example.shoppingapp.network.OtpApiService;
import com.example.shoppingapp.network.UserApiService;

import com.example.shoppingapp.network.request.MobileCheckRequest;
import com.example.shoppingapp.network.response.MobileCheckResponse;
import com.example.shoppingapp.network.response.OtpGenerationResponse;

import com.example.shoppingapp.network.response.RegisterResponse;

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

public class RegistrationActivity extends AppCompatActivity {

    /* ---------------- BASIC UI ---------------- */
    private TextInputLayout tilName,tilCity, tilMobile, tilPassword, tilConfirmPassword;
    private TextInputEditText etName,etCity, etMobile, etPassword, etConfirmPassword;
    private MaterialButton btnRegister, btnSendOtp;
    private TextView tvLogin;

    /* ---------------- OTP UI ---------------- */
    private View otpView;
    private EditText[] otpFields;
    private MaterialButton verifyOtpBtn, resendOtpBtn;

    /* ---------------- FLAGS ---------------- */
    private boolean isOtpVerified = false;
    private boolean isMobileAvailable = false;

    /* ---------------- TIMER ---------------- */
    private CountDownTimer resendTimer;
    private static final int RESEND_DELAY_SEC = 30;

    /* ---------------- API ---------------- */
    private ApiService apiService;
    private OtpApiService otpApiService;
    private UserApiService userApiService;

    private static final String TAG = "REGISTER_FLOW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
                (v, insets) -> {
                    Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
                    return insets;
                });

        apiService = ApiClient.getClient().create(ApiService.class);
        otpApiService = ApiClient.getClient().create(OtpApiService.class);
        userApiService = ApiClient.getClient().create(UserApiService.class);


        initViews();
        setupLiveValidation();
        setupListeners();
    }

    /* ---------------- INIT VIEWS ---------------- */
    private void initViews() {

        tilName = findViewById(R.id.tilName);
        tilCity = findViewById(R.id.tilCity);
        tilMobile = findViewById(R.id.tilMobile);
        tilPassword = findViewById(R.id.tilPassword);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);

        etName = findViewById(R.id.etName);
        etCity = findViewById(R.id.etCity);
        etMobile = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);
        btnSendOtp = findViewById(R.id.btnSendOtp);
        tvLogin = findViewById(R.id.tvLogin);

        otpView = findViewById(R.id.otpCard);
        otpView.setVisibility(View.GONE);

        otpFields = new EditText[]{
                otpView.findViewById(R.id.otp1),
                otpView.findViewById(R.id.otp2),
                otpView.findViewById(R.id.otp3),
                otpView.findViewById(R.id.otp4),
                otpView.findViewById(R.id.otp5),
                otpView.findViewById(R.id.otp6)
        };

        verifyOtpBtn = otpView.findViewById(R.id.verifyOtpBtn);
        resendOtpBtn = otpView.findViewById(R.id.resendOtpBtn);

        btnRegister.setEnabled(false);
        btnSendOtp.setVisibility(View.GONE);

        String text = "Already have an account? Login";
        SpannableString spannableString = new SpannableString(text);

        String loginWord = "Login";
        int loginStart = text.indexOf(loginWord);
        int loginEnd = loginStart + loginWord.length();

// Grey for first part
        spannableString.setSpan(
                new ForegroundColorSpan(Color.GRAY),
                0,
                loginStart,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

// Purple for Login
        spannableString.setSpan(
                new ForegroundColorSpan(Color.parseColor("#6A3DE8")),
                loginStart,
                loginEnd,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

// 🔥 Bold only Login
        spannableString.setSpan(
                new StyleSpan(Typeface.BOLD),
                loginStart,
                loginEnd,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        tvLogin.setText(spannableString);

        setOtpFieldBehaviors();
    }

    /* ---------------- LIVE VALIDATION ---------------- */

    private void setupLiveValidation() {

        etName.addTextChangedListener(simpleWatcher(() -> {
            if (TextUtils.isEmpty(etName.getText())) {
                showError(tilName, "Name is required");
            } else {
                clearError(tilName);
            }
        }));
        etCity.addTextChangedListener(simpleWatcher(() -> {
            if (TextUtils.isEmpty(etCity.getText())) {
                showError(tilCity, "City is required");
            } else {
                clearError(tilCity);
            }
        }));
        etMobile.addTextChangedListener(simpleWatcher(() -> {

            // 🔴 RESET OTP FLOW WHEN MOBILE CHANGES
            isOtpVerified = false;
            btnRegister.setEnabled(false);
            otpView.setVisibility(View.GONE);
            // 🔥 RESET TIMER (ADDED)
            if (resendTimer != null) {
                resendTimer.cancel();
                resendTimer = null;
            }

            String mobile = etMobile.getText().toString().trim();

            if (!mobile.matches("^[6-9][0-9]{9}$")) {
                showError(tilMobile, "Enter valid 10-digit mobile number");
                btnSendOtp.setVisibility(View.GONE);
                return;
            }

            clearError(tilMobile);
            checkMobileFromApi(mobile);
        }));

        etPassword.addTextChangedListener(simpleWatcher(() -> {
            if (etPassword.getText().length() < 6) {
                showError(tilPassword, "Minimum 6 characters required");
            } else {
                clearError(tilPassword);
            }
            // 🔥 re-check confirm password when password changes
            validateConfirmPassword();
        }));
        etConfirmPassword.addTextChangedListener(
                simpleWatcher(this::validateConfirmPassword)
        );
    }
    /* ---------------- CONFIRM PASSWORD VALIDATION ---------------- */
    private void validateConfirmPassword() {

        String pass = etPassword.getText().toString();
        String confirm = etConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(confirm)) {
            showError(tilConfirmPassword, "Confirm your password");
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            showError(tilConfirmPassword, "Enter password first");
            return;
        }

        if (!pass.equals(confirm)) {
            showError(tilConfirmPassword, "Passwords do not match");
        } else {
            clearError(tilConfirmPassword);
        }

        // 🔥 Enable register dynamically
        btnRegister.setEnabled(allFieldsValid());
    }



    /* ---------------- LISTENERS ---------------- */
    private void setupListeners() {

        btnSendOtp.setOnClickListener(v -> {
            if (!isMobileAvailable) {
                Toast.makeText(
                        RegistrationActivity.this,
                        "Mobile already registered",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }
            sendOtp();
        });

        resendOtpBtn.setOnClickListener(v -> sendOtp());
        verifyOtpBtn.setOnClickListener(v -> verifyOtpApi());

        btnRegister.setOnClickListener(v -> {

            Log.d(TAG,
                    "CLICK REGISTER -> OTP=" + isOtpVerified
                            + ", nameErr=" + tilName.isErrorEnabled()
                            + ", passErr=" + tilPassword.isErrorEnabled()
            );

            if (allFieldsValid()) {
                callRegisterApi();
            } else {
                Toast.makeText(this, "Please fix errors", Toast.LENGTH_SHORT).show();
            }
        });

        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    /* ---------------- SEND OTP (API) ---------------- */
    private void sendOtp() {

        String mobile = etMobile.getText().toString().trim();
        String fullMobile = "91" + mobile;

        btnSendOtp.setVisibility(View.GONE);
        resendOtpBtn.setVisibility(View.VISIBLE);

        otpApiService.sendOtp(fullMobile)
                .enqueue(new Callback<OtpGenerationResponse>() {
                    @Override
                    public void onResponse(Call<OtpGenerationResponse> call,
                                           Response<OtpGenerationResponse> response) {

                        if (response.isSuccessful() && response.body() != null &&
                                ("1".equals(response.body().getStatus())
                                        || "success".equalsIgnoreCase(response.body().getStatus()))) {

                            // 🔥 THIS WAS NEVER RUNNING BEFORE
                            showOtpSentPopup();
                            otpView.setVisibility(View.VISIBLE);
                            clearOtpFields();
                            otpFields[0].requestFocus();
                            startResendTimer();

                        } else {
                            btnSendOtp.setVisibility(View.VISIBLE);
                            Toast.makeText(
                                    RegistrationActivity.this,
                                    response.body() != null
                                            ? response.body().getMessage()
                                            : "OTP failed",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OtpGenerationResponse> call, Throwable t) {
                        btnSendOtp.setVisibility(View.VISIBLE);
                        Toast.makeText(
                                RegistrationActivity.this,
                                "OTP request failed",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }



    /* ---------------- RESEND TIMER ---------------- */
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
    /* ---------------- OTP SENT DIALOG ---------------- */
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


    /* ---------------- VERIFY OTP API ---------------- */
    private void verifyOtpApi() {

        StringBuilder otpBuilder = new StringBuilder();
        for (EditText e : otpFields) {
            otpBuilder.append(e.getText().toString().trim());
        }

        if (otpBuilder.length() != 6) {
            Toast.makeText(this, "Enter full OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        String otp = otpBuilder.toString();
        String mobile = "91" + etMobile.getText().toString().trim();
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

                            // ✅ OTP VERIFIED
                            isOtpVerified = true;
                            clearError(tilMobile);
                            showOtpVerifiedPopup();

                            if (resendTimer != null) {
                                resendTimer.cancel();
                                resendTimer = null;
                            }

                            btnSendOtp.setVisibility(View.GONE);
                            otpView.setVisibility(View.GONE);
                            etMobile.setEnabled(false);
                            btnRegister.setEnabled(allFieldsValid());

                        } else {
                            // ❌ WRONG OTP
                            showOtpError();
                        }
                    }

                    @Override
                    public void onFailure(Call<OtpGenerationResponse> call, Throwable t) {
                        Toast.makeText(
                                RegistrationActivity.this,
                                "OTP verification failed",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    /* ---------------- MOBILE CHECK API ---------------- */
//    private void checkMobileFromApi(String mobile) {
//
//        userApiService.checkMobile(mobile)
//                .enqueue(new Callback<MobileCheckResponse>() {
//
//                    @Override
//                    public void onResponse(Call<MobileCheckResponse> call,
//                                           Response<MobileCheckResponse> response) {
//
//                        if (!response.isSuccessful() || response.body() == null) {
//                            showError(tilMobile, "Server error");
//                            btnSendOtp.setVisibility(View.GONE);
//                            return;
//                        }
//
//                        MobileCheckResponse res = response.body();
//
//                        if (res.isStatus()) {
//                            // ❌ Mobile already exists
//                            showError(tilMobile, "Mobile already registered");
//                            isMobileAvailable = false;
//                            btnSendOtp.setVisibility(View.GONE);
//                        } else {
//                            // ✅ Mobile is new
//                            clearError(tilMobile);
//                            isMobileAvailable = true;
//                            btnSendOtp.setVisibility(View.VISIBLE);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<MobileCheckResponse> call, Throwable t) {
//                        showError(tilMobile, "Network error");
//                        btnSendOtp.setVisibility(View.GONE);
//                    }
//                });
//    }

    private void checkMobileFromApi(String mobile) {

        Log.d(TAG, "checkMobileFromApi() called with mobile = " + mobile);

        MobileCheckRequest request = new MobileCheckRequest(mobile);

        userApiService.checkMobile(request)
                .enqueue(new Callback<MobileCheckResponse>() {

                    @Override
                    public void onResponse(Call<MobileCheckResponse> call,
                                           Response<MobileCheckResponse> response) {

                        Log.d(TAG, "MobileCheck HTTP Code = " + response.code());

                        if (!response.isSuccessful()) {
                            Log.e(TAG, "MobileCheck failed. ErrorBody = " + response.errorBody());
                            showError(tilMobile, "Server error");
                            btnSendOtp.setVisibility(View.GONE);
                            isMobileAvailable = false;
                            return;
                        }

                        if (response.body() == null) {
                            Log.e(TAG, "MobileCheck response body is NULL");
                            showError(tilMobile, "Empty response");
                            btnSendOtp.setVisibility(View.GONE);
                            isMobileAvailable = false;
                            return;
                        }

                        MobileCheckResponse res = response.body();

                        Log.d(TAG,
                                "MobileCheck Response → " +
                                        "mobile=" + res.getCustMobNo() +
                                        ", uniqueId=" + res.getUniqueId() +
                                        ", status=" + res.isStatus()
                        );

                        if (res.isStatus()) {
                            // ❌ Mobile already exists
                            showError(tilMobile, "Mobile already registered");
                            isMobileAvailable = false;
                            btnSendOtp.setVisibility(View.GONE);

                            Log.d(TAG, "Mobile already REGISTERED ❌");

                        } else {
                            // ✅ Mobile is new
                            clearError(tilMobile);
                            isMobileAvailable = true;
                            btnSendOtp.setVisibility(View.VISIBLE);

                            Log.d(TAG, "Mobile AVAILABLE ✅ , OTP allowed");
                        }
                    }

                    @Override
                    public void onFailure(Call<MobileCheckResponse> call, Throwable t) {
                        Log.e(TAG, "MobileCheck API FAILURE", t);
                        showError(tilMobile, "Network error");
                        btnSendOtp.setVisibility(View.GONE);
                        isMobileAvailable = false;
                    }
                });
    }



    /* ---------------- REGISTER API ---------------- */
    private void callRegisterApi() {

        String name = etName.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        String date = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault()
        ).format(new Date());

        userApiService.registerUser(
                name,
                city,
                mobile,
                password,
                date
        ).enqueue(new Callback<RegisterResponse>() {

            @Override
            public void onResponse(Call<RegisterResponse> call,
                                   Response<RegisterResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    if ("success".equalsIgnoreCase(response.body().getStatus())
                            || "1".equals(response.body().getStatus())) {

                        Toast.makeText(
                                RegistrationActivity.this,
                                response.body().getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                        startActivity(
                                new Intent(
                                        RegistrationActivity.this,
                                        LoginActivity.class
                                )
                        );
                        finish();

                    } else {
                        Toast.makeText(
                                RegistrationActivity.this,
                                response.body().getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(
                        RegistrationActivity.this,
                        "Registration failed",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }


    /* ---------------- HELPERS ---------------- */
    private boolean allFieldsValid() {
        return !tilName.isErrorEnabled()
                && !tilCity.isErrorEnabled()
                && !tilPassword.isErrorEnabled()
                && !tilConfirmPassword.isErrorEnabled()
                && isOtpVerified;
    }

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
            public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            public void onTextChanged(CharSequence s, int a, int b, int c) { r.run(); }
            public void afterTextChanged(Editable s) {}
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
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void afterTextChanged(Editable s) {}

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (resendTimer != null) resendTimer.cancel();
    }
    private void showOtpError() {
        Toast.makeText(this, "Invalid OTP. Please try again.", Toast.LENGTH_SHORT).show();
        clearOtpFields();
        otpFields[0].requestFocus();
    }

}
