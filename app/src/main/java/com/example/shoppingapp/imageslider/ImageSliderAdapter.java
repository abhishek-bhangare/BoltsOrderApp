//package com.example.shoppingapp.imageslider;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.shoppingapp.R;
//
//import java.util.List;
//
//public class ImageSliderAdapter
//        extends RecyclerView.Adapter<ImageSliderAdapter.ViewHolder> {
//
//    private final List<String> images;
//
//    public ImageSliderAdapter(List<String> images) {
//        this.images = images;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_slider_image, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Glide.with(holder.image.getContext())
//                .load(images.get(position))
//                .placeholder(R.drawable.nutbolt)   // optional
//                .error(R.drawable.nutbolt)         // optional
//                .into(holder.image);
//    }
//
//    @Override
//    public int getItemCount() {
//        return images != null ? images.size() : 0;
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView image;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            image = itemView.findViewById(R.id.imgSlider);
//        }
//    }
//}
package com.example.shoppingapp.imageslider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;

import java.util.List;

public class ImageSliderAdapter
        extends RecyclerView.Adapter<ImageSliderAdapter.ViewHolder> {

    // 🔹 Click listener interface
    public interface OnImageClickListener {
        void onImageClick(int position);
    }

    private final List<Integer> images;
    private final OnImageClickListener listener;

    // 🔹 Constructor with click listener
    public ImageSliderAdapter(List<Integer> images,
                              OnImageClickListener listener) {
        this.images = images;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_slider_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.image.setImageResource(images.get(position));

        // 🔹 Handle image click
        holder.image.setOnClickListener(v -> {
            if (listener != null) {
                listener.onImageClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images == null ? 0 : images.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgSlider);
        }
    }
}
