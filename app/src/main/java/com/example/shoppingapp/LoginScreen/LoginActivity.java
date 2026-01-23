//
//package com.example.shoppingapp.LoginScreen;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.Spannable;
//import android.text.SpannableString;
//import android.text.TextWatcher;
//import android.text.style.ForegroundColorSpan;
//import android.text.style.StyleSpan;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.shoppingapp.MainActivity;
//import com.example.shoppingapp.R;
//import com.example.shoppingapp.auth.forgotpassword.ForgotPasswordActivity;
//import com.example.shoppingapp.auth.register.RegistrationActivity;
//import com.example.shoppingapp.network.ApiClient;
//import com.example.shoppingapp.network.ApiService;
//import com.example.shoppingapp.network.request.LoginRequest;
//import com.example.shoppingapp.network.response.LoginResponse;
//import com.example.shoppingapp.network.response.MobileCheckResponse;
//import com.example.shoppingapp.utils.SessionManager;
//import com.google.android.material.button.MaterialButton;
//import com.google.android.material.textfield.TextInputEditText;
//import com.google.android.material.textfield.TextInputLayout;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class LoginActivity extends AppCompatActivity {
//
//    private TextInputEditText etPhone, etPassword;
//    private MaterialButton btnContinue;
//    private ApiService apiService;
//    private TextView forgotPassword;
//    private TextInputLayout tilPhone;
//
//    // 🔹 ADDED
//    private boolean isMobileRegistered = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_login);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        // INIT
//        tilPhone = findViewById(R.id.tilPhone);
//        etPhone = findViewById(R.id.etPhone);
//        etPassword = findViewById(R.id.etPassword);
//        btnContinue = findViewById(R.id.btnContinue);
//        forgotPassword = findViewById(R.id.forgotPassword);
//
//        apiService = ApiClient.getClient().create(ApiService.class);
//
//        btnContinue.setOnClickListener(v -> loginUser());
//        TextView tvRegister = findViewById(R.id.tvRegister);
//
//        tvRegister.setOnClickListener(v -> {
//            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
//            startActivity(intent);
//        });
//
//        String text = "New User? Register";
//        SpannableString spannableString = new SpannableString(text);
//
//        String registerWord = "Register";
//        int registerStart = text.indexOf(registerWord);
//        int registerEnd = registerStart + registerWord.length();
//
//// Grey for first part
//        spannableString.setSpan(
//                new ForegroundColorSpan(Color.GRAY),
//                0,
//                registerStart,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        );
//
//// Purple for Register
//        spannableString.setSpan(
//                new ForegroundColorSpan(Color.parseColor("#6A3DE8")),
//                registerStart,
//                registerEnd,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        );
//
//// 🔥 Bold only Register
//        spannableString.setSpan(
//                new StyleSpan(Typeface.BOLD),
//                registerStart,
//                registerEnd,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        );
//
//        tvRegister.setText(spannableString);
//
//
//        forgotPassword.setOnClickListener(v -> {
//            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
//        });
//
//        // 🔹 MOBILE LIVE CHECK (ADDED ONLY)
//        etPhone.addTextChangedListener(new TextWatcher() {
//            @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
//            @Override public void afterTextChanged(Editable s) {}
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                isMobileRegistered = false;
//                btnContinue.setEnabled(false);
//                tilPhone.setError(null); // 🔴 CHANGED: clear old error
//
//                String mobile = s.toString().trim();
//                mobile = mobile.replaceAll("\\s+", "");
//
//                if (mobile.startsWith("+91")) {
//                    mobile = mobile.substring(3);
//                } else if (mobile.startsWith("91") && mobile.length() == 12) {
//                    mobile = mobile.substring(2);
//                }
//
//                if (!mobile.matches("^[6-9]\\d{9}$")) return;
//
//                checkMobileFromApi(mobile);
//            }
//        });
//    }
//
//    // 🔹 MOBILE CHECK API (LOGIN VERSION)
//    private void checkMobileFromApi(String mobile) {
//
//        apiService.checkMobile(mobile).enqueue(new Callback<MobileCheckResponse>() {
//            @Override
//            public void onResponse(Call<MobileCheckResponse> call,
//                                   Response<MobileCheckResponse> response) {
//
//                if (response.isSuccessful() && response.body() != null) {
//
//                    if (response.body().isStatus()) {
//                        // ✅ Mobile exists
//                        isMobileRegistered = true;
//                        btnContinue.setEnabled(true);
//                        tilPhone.setError(null); // clear error
//                    } else {
//                        // ❌ Mobile not registered
//                        isMobileRegistered = false;
//                        btnContinue.setEnabled(false);
//                        tilPhone.setError("Mobile number not registered");
//
//
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MobileCheckResponse> call, Throwable t) {
//                isMobileRegistered = false;
//                btnContinue.setEnabled(false);
//                tilPhone.setError("Unable to verify mobile"); // 🔴 CHANGED
//            }
//        });
//    }
//
//    private void loginUser() {
//
//        // 🔹 ADDED CHECK ONLY
//        if (!isMobileRegistered) {
//            tilPhone.setError("Mobile number not verified");
//            return;
//        }
//
//        // ✅ READ INPUT
//        String mobile = etPhone.getText() != null
//                ? etPhone.getText().toString().trim() : "";
//
//        String password = etPassword.getText() != null
//                ? etPassword.getText().toString().trim() : "";
//
//        // EMPTY CHECK
//        if (mobile.isEmpty()) {
//            tilPhone.setError("Enter mobile number"); // 🔴 CHANGED
//            return;
//        }
//
//        if (password.isEmpty()) {
//            etPassword.setError("Enter password");
//            return;
//        }
//
//        // ✅ NORMALIZE MOBILE NUMBER
//        mobile = mobile.replaceAll("\\s+", "");
//
//        if (mobile.startsWith("+91")) {
//            mobile = mobile.substring(3);
//        } else if (mobile.startsWith("91") && mobile.length() == 12) {
//            mobile = mobile.substring(2);
//        }
//
//        // ✅ VALIDATE FINAL 10-DIGIT NUMBER
//        if (!mobile.matches("^[6-9]\\d{9}$")) {
//            tilPhone.setError("Enter valid 10-digit mobile number"); // 🔴 CHANGED
//            return;
//        }
//
//        LoginRequest request = new LoginRequest(mobile, password);
//
//        apiService.loginUser(request).enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call,
//                                   Response<LoginResponse> response) {
//
//                if (response.isSuccessful() && response.body() != null) {
//
//                    LoginResponse res = response.body();
//
//                    if (res.isStatus()) {
//
//                        SessionManager session =
//                                new SessionManager(LoginActivity.this);
//                        session.saveLogin(res.getCustId(), res.getUsername());
//
//                        Toast.makeText(LoginActivity.this,
//                                "Login successful",
//                                Toast.LENGTH_SHORT).show();
//
//                        startActivity(new Intent(
//                                LoginActivity.this, MainActivity.class));
//                        finish();
//
//                    } else {
//                        etPassword.setError("Invalid login details"); // 🔴 CHANGED
//                    }
//                } else {
//                    etPassword.setError("Unable to login. Please try again");
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                etPassword.setError("Server error"); // 🔴 CHANGED
//            }
//        });
//    }
//}


package com.example.shoppingapp.LoginScreen;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shoppingapp.MainActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.auth.forgotpassword.ForgotPasswordActivity;
import com.example.shoppingapp.auth.register.RegistrationActivity;
import com.example.shoppingapp.network.ApiClient;
import com.example.shoppingapp.network.ApiService;
import com.example.shoppingapp.network.UserApiService;
import com.example.shoppingapp.network.request.LoginRequest;
import com.example.shoppingapp.network.response.LoginResponse;
import com.example.shoppingapp.network.response.MobileCheckResponse;
import com.example.shoppingapp.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etPhone, etPassword;
    private MaterialButton btnContinue;
    private ApiService apiService;
    private UserApiService userApiService; // mobile check API

    private TextView forgotPassword;
    private TextInputLayout tilPhone;

    // 🔹 ADDED
    private boolean isMobileRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // INIT
        tilPhone = findViewById(R.id.tilPhone);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btnContinue = findViewById(R.id.btnContinue);
        forgotPassword = findViewById(R.id.forgotPassword);

        apiService = ApiClient.getClient().create(ApiService.class);
        userApiService = ApiClient.getClient().create(UserApiService.class); // mobile check

        btnContinue.setOnClickListener(v -> loginUser());
        TextView tvRegister = findViewById(R.id.tvRegister);

        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });

        String text = "New User? Register";
        SpannableString spannableString = new SpannableString(text);

        String registerWord = "Register";
        int registerStart = text.indexOf(registerWord);
        int registerEnd = registerStart + registerWord.length();

// Grey for first part
        spannableString.setSpan(
                new ForegroundColorSpan(Color.GRAY),
                0,
                registerStart,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

// Purple for Register
        spannableString.setSpan(
                new ForegroundColorSpan(Color.parseColor("#000000")),
                registerStart,
                registerEnd,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

// 🔥 Bold only Register
        spannableString.setSpan(
                new StyleSpan(Typeface.BOLD),
                registerStart,
                registerEnd,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        tvRegister.setText(spannableString);


        forgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
        });

        // 🔹 MOBILE LIVE CHECK (ADDED ONLY)
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                isMobileRegistered = false;
                btnContinue.setEnabled(false);
                tilPhone.setError(null); // 🔴 CHANGED: clear old error

                String mobile = s.toString().trim();
                mobile = mobile.replaceAll("\\s+", "");

                if (mobile.startsWith("+91")) {
                    mobile = mobile.substring(3);
                } else if (mobile.startsWith("91") && mobile.length() == 12) {
                    mobile = mobile.substring(2);
                }

                if (!mobile.matches("^[6-9]\\d{9}$")) return;

                checkMobileFromApi(mobile);
            }
        });
    }

    // 🔹 MOBILE CHECK API (LOGIN VERSION)
    private void checkMobileFromApi(String mobile) {

        userApiService.checkMobile(mobile)
                .enqueue(new Callback<MobileCheckResponse>() {

                    @Override
                    public void onResponse(Call<MobileCheckResponse> call,
                                           Response<MobileCheckResponse> response) {

                        if (!response.isSuccessful() || response.body() == null) {
                            isMobileRegistered = false;
                            btnContinue.setEnabled(false);
                            tilPhone.setError("Server error");
                            return;
                        }

                        MobileCheckResponse res = response.body();

                        if (res.isStatus()) {
                            // ✅ Mobile exists → allow login
                            isMobileRegistered = true;
                            btnContinue.setEnabled(true);
                            tilPhone.setError(null);
                        } else {
                            // ❌ Mobile not registered
                            isMobileRegistered = false;
                            btnContinue.setEnabled(false);
                            tilPhone.setError("Mobile number not registered");
                        }
                    }

                    @Override
                    public void onFailure(Call<MobileCheckResponse> call, Throwable t) {
                        isMobileRegistered = false;
                        btnContinue.setEnabled(false);
                        tilPhone.setError("Network error");
                    }
                });
    }

    private void loginUser() {

        // 🔹 ADDED CHECK ONLY
        if (!isMobileRegistered) {
            tilPhone.setError("Mobile number not verified");
            return;
        }

        // ✅ READ INPUT
        String mobile = etPhone.getText() != null
                ? etPhone.getText().toString().trim() : "";

        String password = etPassword.getText() != null
                ? etPassword.getText().toString().trim() : "";

        // EMPTY CHECK
        if (mobile.isEmpty()) {
            tilPhone.setError("Enter mobile number"); // 🔴 CHANGED
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Enter password");
            return;
        }

        // ✅ NORMALIZE MOBILE NUMBER
        mobile = mobile.replaceAll("\\s+", "");

        if (mobile.startsWith("+91")) {
            mobile = mobile.substring(3);
        } else if (mobile.startsWith("91") && mobile.length() == 12) {
            mobile = mobile.substring(2);
        }

        // ✅ VALIDATE FINAL 10-DIGIT NUMBER
        if (!mobile.matches("^[6-9]\\d{9}$")) {
            tilPhone.setError("Enter valid 10-digit mobile number"); // 🔴 CHANGED
            return;
        }

        LoginRequest request = new LoginRequest(mobile, password);

        userApiService.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call,
                                   Response<LoginResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    LoginResponse res = response.body();

                    if (res.isStatus()) {

                        SessionManager session =
                                new SessionManager(LoginActivity.this);

                        session.saveLogin(
                                res.getC_id(),        // ✅ customer id
                                res.getUnique_id(),   // ✅ profile api key
                                res.getCust_mobile()  // ✅ mobile
                        );

                        Toast.makeText(LoginActivity.this,
                                "Login successful",
                                Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(
                                LoginActivity.this, MainActivity.class));
                        finish();

                    } else {
                        etPassword.setError("Invalid login details");
                    }
                } else {
                    etPassword.setError("Unable to login. Please try again");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                etPassword.setError("Server error");
            }
        });
    }
}

