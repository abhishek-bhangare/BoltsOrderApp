package com.example.shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OtpVerification extends AppCompatActivity {

    // UI components
    private TextView tvSubtitle;  // Displays message about OTP sent
    private EditText etOtp1, etOtp2, etOtp3, etOtp4; // 4 OTP input fields
    private Button btnVerify; // Button to verify OTP
    private Toolbar toolbar; // Toolbar with back arrow
    private TextView tvResendOtp; // TextView to show "Resend OTP in xx s"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this); // Enable edge-to-edge layout
        setContentView(R.layout.activity_otp_verification); // Load layout XML

        // Handle system bars padding (status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        toolbar = findViewById(R.id.toolbar);
        tvSubtitle = findViewById(R.id.tvSubtitle);
        etOtp1 = findViewById(R.id.etOtp1);
        etOtp2 = findViewById(R.id.etOtp2);
        etOtp3 = findViewById(R.id.etOtp3);
        etOtp4 = findViewById(R.id.etOtp4);
        btnVerify = findViewById(R.id.btnVerify);
        tvResendOtp = findViewById(R.id.tvResendOtp); // Initialize Resend OTP TextView

        // Setup toolbar with back arrow
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back arrow
            getSupportActionBar().setDisplayShowTitleEnabled(true); // Show title
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed()); // Handle back click

        // Set mobile number subtitle (centered)
        String mobile = getIntent().getStringExtra("mobile");
        if (mobile != null && !mobile.isEmpty()) {
            tvSubtitle.setText("We have sent a verification code to\n+91 " + mobile);
            tvSubtitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        // Setup OTP input behavior (forward & backspace)
        setupOtpInputs();

        // Automatically focus first OTP field and show keyboard
        etOtp1.requestFocus();
        etOtp1.postDelayed(() -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(etOtp1, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 200);

        // Verify button click listener
        btnVerify.setOnClickListener(v -> {
            String otp = etOtp1.getText().toString().trim() +
                    etOtp2.getText().toString().trim() +
                    etOtp3.getText().toString().trim() +
                    etOtp4.getText().toString().trim();

            if (otp.length() == 4) {
                Toast.makeText(OtpVerification.this, "OTP Verified: " + otp, Toast.LENGTH_SHORT).show();
                // TODO: Proceed to next
                // ✅ Open HomeActivity
                Intent intent = new Intent(OtpVerification.this, MainActivity.class);
                startActivity(intent);
                // Close OTP screen so user can’t go back
                finish();
            } else {
                Toast.makeText(OtpVerification.this, "Enter valid 4-digit OTP", Toast.LENGTH_SHORT).show();
            }
        });

        // Start resend OTP countdown
        startResendCountdown(tvResendOtp, 30);

        // Handle resend OTP click
        tvResendOtp.setOnClickListener(v -> {
            // TODO: Call your API to resend OTP
            Toast.makeText(OtpVerification.this, "OTP Resent", Toast.LENGTH_SHORT).show();
            tvResendOtp.setEnabled(false); // Disable until countdown finishes
            startResendCountdown(tvResendOtp, 30); // Restart countdown
        });
    }

    // Setup automatic next-field focus and backspace handling
    private void setupOtpInputs() {
        setupOtpField(etOtp1, etOtp2);
        setupOtpField(etOtp2, etOtp3, etOtp1);
        setupOtpField(etOtp3, etOtp4, etOtp2);
        setupOtpField(etOtp4, null, etOtp3);
    }

    // Forward-only helper
    private void setupOtpField(EditText current, EditText next) {
        setupOtpField(current, next, null);
    }

    // Forward & backspace helper
    private void setupOtpField(EditText current, EditText next, EditText previous) {
        current.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1 && next != null) {
                    next.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // Handle backspace
        current.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL
                    && event.getAction() == KeyEvent.ACTION_DOWN
                    && current.getText().toString().isEmpty()
                    && previous != null) {
                previous.requestFocus();
                previous.setSelection(previous.getText().length());
                return true;
            }
            return false;
        });
    }

    // Resend OTP countdown method
    private void startResendCountdown(TextView tv, int seconds) {
        tv.setEnabled(false); // Disable clicking during countdown
        new CountDownTimer(seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv.setText("Resend OTP in " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                tv.setText("Resend OTP");
                tv.setEnabled(true); // Enable click after timer finishes
            }
        }.start();
    }
}

