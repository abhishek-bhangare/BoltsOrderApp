//
//package com.example.shoppingapp.imageslider;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.shoppingapp.R;
//import com.github.chrisbanes.photoview.PhotoView;
//
//import java.util.List;
//
//public class ImageSliderAdapter
//        extends RecyclerView.Adapter<ImageSliderAdapter.ViewHolder> {
//
//    // 🔹 Click listener interface (UNCHANGED)
//    public interface OnImageClickListener {
//        void onImageClick(int position);
//    }
//
//    private final List<Integer> images;
//    private final OnImageClickListener listener;
//
//    // 🔹 Constructor (UNCHANGED)
//    public ImageSliderAdapter(List<Integer> images,
//                              OnImageClickListener listener) {
//        this.images = images;
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(
//            @NonNull ViewGroup parent, int viewType) {
//
//        // 🔹 ONLY layout changed (for PhotoView)
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_fullscreen_image, parent, false);
//
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(
//            @NonNull ViewHolder holder, int position) {
//
//        holder.image.setImageResource(images.get(position));
//
//        // 🔹 Click logic (UNCHANGED)
//        holder.image.setOnClickListener(v -> {
//            if (listener != null) {
//                listener.onImageClick(position);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return images == null ? 0 : images.size();
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//
//        // 🔹 ImageView → PhotoView (ONLY CHANGE)
//        PhotoView image;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            image = itemView.findViewById(R.id.imgSlider);
//        }
//    }
//}
//
//
//package com.example.shoppingapp.imageslider;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.shoppingapp.R;
//import com.github.chrisbanes.photoview.PhotoView;
//
//import java.util.List;
//
//public class ImageSliderAdapter
//        extends RecyclerView.Adapter<ImageSliderAdapter.ViewHolder> {
//
//    private static final String TAG = "ImageSliderAdapter";
//
//    // 🔹 Click listener (optional)
//    public interface OnImageClickListener {
//        void onImageClick(int position);
//    }
//
//    // 🔹 DATA (only ONE will be non-null)
//    private final List<String> imageUrls;        // API images
//    private final List<Integer> drawableImages;  // fallback images
//    private final OnImageClickListener listener;
//
//    // =====================================================
//    // 🔹 CONSTRUCTOR – API IMAGE URLS
//    // =====================================================
//    public ImageSliderAdapter(
//            @NonNull List<String> imageUrls,
//            OnImageClickListener listener
//    ) {
//        this.imageUrls = imageUrls;
//        this.drawableImages = null;
//        this.listener = listener;
//
//        Log.d(TAG, "Adapter initialized with URL images. Count = " + imageUrls.size());
//    }
//
//    // =====================================================
//    // 🔹 CONSTRUCTOR – DRAWABLE FALLBACK IMAGES
//    // =====================================================
//    public ImageSliderAdapter(
//            @NonNull List<Integer> drawableImages,
//            boolean isDrawable,
//            OnImageClickListener listener
//    ) {
//        this.drawableImages = drawableImages;
//        this.imageUrls = null;
//        this.listener = listener;
//
//        Log.d(TAG, "Adapter initialized with drawable images. Count = " + drawableImages.size());
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(
//            @NonNull ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_fullscreen_image, parent, false);
//
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(
//            @NonNull ViewHolder holder, int position) {
//
//        // =====================================================
//        // 🔹 LOAD API IMAGE (URL)
//        // =====================================================
//        if (imageUrls != null && position < imageUrls.size()) {
//
//            String url = imageUrls.get(position);
//            Log.d(TAG, "Loading URL image [" + position + "]: " + url);
//
//            Glide.with(holder.image.getContext())
//                    .load(url)
//                    .placeholder(R.drawable.nutbolt)
//                    .error(R.drawable.nutbolt)
//                    .into(holder.image);
//        }
//        // =====================================================
//        // 🔹 LOAD DRAWABLE IMAGE (FALLBACK)
//        // =====================================================
//        else if (drawableImages != null && position < drawableImages.size()) {
//
//            int resId = drawableImages.get(position);
//            Log.d(TAG, "Loading drawable image [" + position + "]: " + resId);
//
//            holder.image.setImageResource(resId);
//        }
//        // =====================================================
//        // 🔴 SAFETY FALLBACK (SHOULD NEVER HAPPEN)
//        // =====================================================
//        else {
//            Log.e(TAG, "No image found at position " + position);
//            holder.image.setImageResource(R.drawable.nutbolt);
//        }
//
//        // =====================================================
//        // 🔹 CLICK HANDLING (OPTIONAL)
//        // =====================================================
//        holder.image.setOnClickListener(v -> {
//            if (listener != null) {
//                listener.onImageClick(position);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        if (imageUrls != null) return imageUrls.size();
//        if (drawableImages != null) return drawableImages.size();
//        return 0;
//    }
//
//    // =====================================================
//    // 🔹 VIEW HOLDER
//    // =====================================================
//    static class ViewHolder extends RecyclerView.ViewHolder {
//
//        PhotoView image;
//
//        ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            image = itemView.findViewById(R.id.imgSlider);
//        }
//    }
//}

//image view
package com.example.shoppingapp.imageslider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;

import java.util.List;

public class ImageSliderAdapter
        extends RecyclerView.Adapter<ImageSliderAdapter.ViewHolder> {

    private static final String TAG = "ImageSliderAdapter";

    // 🔹 Click listener
    public interface OnImageClickListener {
        void onImageClick(int position);
    }

    // 🔹 DATA (only ONE will be non-null)
    private final List<String> imageUrls;        // API images
    private final List<Integer> drawableImages;  // fallback images
    private final OnImageClickListener listener;

    // =====================================================
    // 🔹 CONSTRUCTOR – API IMAGE URLS
    // =====================================================
    public ImageSliderAdapter(
            @NonNull List<String> imageUrls,
            OnImageClickListener listener
    ) {
        this.imageUrls = imageUrls;
        this.drawableImages = null;
        this.listener = listener;

        Log.d(TAG, "Adapter initialized with URL images. Count = " + imageUrls.size());
    }

    // =====================================================
    // 🔹 CONSTRUCTOR – DRAWABLE FALLBACK IMAGES
    // =====================================================
    public ImageSliderAdapter(
            @NonNull List<Integer> drawableImages,
            boolean isDrawable,
            OnImageClickListener listener
    ) {
        this.drawableImages = drawableImages;
        this.imageUrls = null;
        this.listener = listener;

        Log.d(TAG, "Adapter initialized with drawable images. Count = " + drawableImages.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        // ✅ USE NORMAL IMAGE VIEW LAYOUT
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_slider_image, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder, int position) {

        // =====================================================
        // 🔹 LOAD API IMAGE (URL)
        // =====================================================
        if (imageUrls != null && position < imageUrls.size()) {

            String url = imageUrls.get(position);
            Log.d(TAG, "Loading URL image [" + position + "]: " + url);

            Glide.with(holder.image.getContext())
                    .load(url)
                    .placeholder(R.drawable.nutbolt)
                    .error(R.drawable.nutbolt)
                    .into(holder.image);
        }
        // =====================================================
        // 🔹 LOAD DRAWABLE IMAGE (FALLBACK)
        // =====================================================
        else if (drawableImages != null && position < drawableImages.size()) {

            int resId = drawableImages.get(position);
            Log.d(TAG, "Loading drawable image [" + position + "]: " + resId);

            holder.image.setImageResource(resId);
        }
        // =====================================================
        // 🔴 SAFETY FALLBACK
        // =====================================================
        else {
            Log.e(TAG, "No image found at position " + position);
            holder.image.setImageResource(R.drawable.nutbolt);
        }

        // =====================================================
        // 🔹 CLICK HANDLING (FULLSCREEN OPENED BY ImageSliderView)
        // =====================================================
        holder.image.setOnClickListener(v -> {
            if (listener != null) {
                listener.onImageClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (imageUrls != null) return imageUrls.size();
        if (drawableImages != null) return drawableImages.size();
        return 0;
    }

    // =====================================================
    // 🔹 VIEW HOLDER (NO ZOOM)
    // =====================================================
    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image; // ✅ NO PhotoView

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgSlider);
        }
    }
}
