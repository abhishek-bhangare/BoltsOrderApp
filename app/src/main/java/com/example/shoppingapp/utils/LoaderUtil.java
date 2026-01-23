package com.example.shoppingapp.utils;

import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

public class LoaderUtil {

    // ✅ Show loader only
    public static void show(LottieAnimationView loader) {
        if (loader == null) return;
        loader.setVisibility(View.VISIBLE);
        loader.playAnimation();
    }

    // ✅ Hide loader only
    public static void hide(LottieAnimationView loader) {
        if (loader == null) return;
        loader.cancelAnimation();
        loader.setVisibility(View.GONE);
    }

    // ✅ Show loader & hide content (RecyclerView / Layout)
    public static void showWithContent(LottieAnimationView loader, View contentView) {
        if (loader == null || contentView == null) return;

        loader.setVisibility(View.VISIBLE);
        loader.playAnimation();
        contentView.setVisibility(View.GONE);
    }

    // ✅ Hide loader & show content
    public static void hideWithContent(LottieAnimationView loader, View contentView) {
        if (loader == null || contentView == null) return;

        loader.cancelAnimation();
        loader.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }
}
