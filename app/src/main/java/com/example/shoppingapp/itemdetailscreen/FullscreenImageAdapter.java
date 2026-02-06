package com.example.shoppingapp.itemdetailscreen;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

public class FullscreenImageAdapter
        extends RecyclerView.Adapter<FullscreenImageAdapter.ViewHolder> {

    private static final String TAG = "FullscreenImageAdapter";

    // 🔹 Only ONE will be non-null
    private final List<String> imageUrls;
    private final List<Integer> drawableImages;

    // =====================================================
    // 🔹 CONSTRUCTOR – API IMAGE URLS
    // =====================================================
    public FullscreenImageAdapter(@NonNull List<String> imageUrls) {
        this.imageUrls = imageUrls;
        this.drawableImages = null;

        Log.d(TAG, "Fullscreen adapter (URL) size = " + imageUrls.size());
    }

    // =====================================================
    // 🔹 CONSTRUCTOR – DRAWABLE FALLBACK IMAGES
    // =====================================================
    public FullscreenImageAdapter(
            @NonNull List<Integer> drawableImages,
            boolean isDrawable
    ) {
        this.drawableImages = drawableImages;
        this.imageUrls = null;

        Log.d(TAG, "Fullscreen adapter (drawable) size = " + drawableImages.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        // ✅ FULLSCREEN LAYOUT WITH PhotoView
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fullscreen_image, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {

        // =====================================================
        // 🔹 LOAD API IMAGE (URL)
        // =====================================================
        if (imageUrls != null && position < imageUrls.size()) {

            String url = imageUrls.get(position);
            Log.d(TAG, "Fullscreen loading URL [" + position + "]: " + url);

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
            Log.d(TAG, "Fullscreen loading drawable [" + position + "]: " + resId);

            holder.image.setImageResource(resId);
        }
        // =====================================================
        // 🔴 SAFETY FALLBACK
        // =====================================================
        else {
            Log.e(TAG, "No image found at position " + position);
            holder.image.setImageResource(R.drawable.nutbolt);
        }
    }

    @Override
    public int getItemCount() {
        if (imageUrls != null) return imageUrls.size();
        if (drawableImages != null) return drawableImages.size();
        return 0;
    }

    // =====================================================
    // 🔹 VIEW HOLDER (ZOOM ENABLED)
    // =====================================================
    static class ViewHolder extends RecyclerView.ViewHolder {

        PhotoView image; // ✅ ZOOM ENABLED

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgSlider);
        }
    }
}
