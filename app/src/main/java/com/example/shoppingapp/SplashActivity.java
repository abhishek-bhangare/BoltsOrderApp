//package com.example.shoppingapp;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.airbnb.lottie.LottieAnimationView;
//import com.example.shoppingapp.LoginScreen.LoginActivity;
//import com.example.shoppingapp.auth.register.RegistrationActivity;
//import com.example.shoppingapp.utils.LoaderUtil;
//
//public class SplashActivity extends AppCompatActivity {
//
//    LottieAnimationView splashLoader;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_splash);
//
//        splashLoader = findViewById(R.id.splashLoader);
//
//        // Keep your EdgeToEdge logic
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        // ✅ Show loader
//        LoaderUtil.show(splashLoader);
//
//        // ✅ Move to next screen
//        new Handler(Looper.getMainLooper()).postDelayed(() -> {
//            LoaderUtil.hide(splashLoader);
//            startActivity(new Intent(SplashActivity.this, RegistrationActivity.class));
//            finish();
//        }, 4000);
//    }
//}
package com.example.shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.example.shoppingapp.LoginScreen.LoginActivity;
import com.example.shoppingapp.utils.LoaderUtil;
import com.example.shoppingapp.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {

    LottieAnimationView splashLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        splashLoader = findViewById(R.id.splashLoader);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LoaderUtil.show(splashLoader);

        // ✅ SESSION CHECK
        SessionManager sessionManager = new SessionManager(this);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            LoaderUtil.hide(splashLoader);

            if (sessionManager.isLoggedIn()) {
                // ✅ User already logged in → HOME
                startActivity(new Intent(this, MainActivity.class));
            } else {
                // ❌ Not logged in → LOGIN
                startActivity(new Intent(this, LoginActivity.class));
            }

            finish();

        }, 3000); // 3 sec is enough
    }
}
