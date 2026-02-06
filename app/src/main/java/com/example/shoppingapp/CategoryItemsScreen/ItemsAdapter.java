//
//package com.example.shoppingapp.CategoryItemsScreen;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.shoppingapp.R;
//import com.example.shoppingapp.network.response.ItemModel;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {
//
//    private Context context;
//    private List<ItemModel> itemList;
//
//    public ItemsAdapter(Context context, List<ItemModel> itemList) {
//        this.context = context;
//        this.itemList = itemList;
//    }
//
//    @NonNull
//    @Override
//    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context)
//                .inflate(R.layout.items, parent, false);
//        return new ItemViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
//
//        ItemModel item = itemList.get(position);
//        Log.d("IMG_PATH", item.getImgPath());
//
//        // 🔹 PART NO
//        holder.tvPartNo.setText(
//                "Part No: " + safe(item.getPartNo(), "N/A")
//        );
//
//        // 🔹 PART NAME
//        holder.tvPartName.setText(
//                safe(item.getPartName(), "N/A")
//        );
//
//        // 🔹 MRP
//        holder.tvMrp.setText(
//                "MRP: ₹" + safe(item.getMrp(), "0")
//        );
//
//        // 🔹 SALE RATE
//        holder.tvSaleRate.setText(
//                "Price: ₹" + safe(item.getSaleRate(), "0")
//        );
//
//        // 🔹 IMAGE HANDLING (SAFE)
//        if (item.getImgPath() != null && !item.getImgPath().trim().isEmpty()) {
//            Glide.with(context)
//                    .load(item.getImgPath())
//                    .placeholder(R.drawable.nutbolt)
//                    .error(R.drawable.nutbolt)
//                    .into(holder.imgItem);
//        } else {
//            holder.imgItem.setImageResource(R.drawable.nutbolt);
//        }
//        // =====================================================
//        // 🔥 CLICK LISTENER (THIS WAS MISSING)
//        // =====================================================
//        holder.itemView.setOnClickListener(v -> {
//
//            // DEBUG (optional)
//            // Toast.makeText(context, "Clicked: " + item.getPartName(), Toast.LENGTH_SHORT).show();
//
//            Intent intent = new Intent(context,
//                    com.example.shoppingapp.itemdetailscreen.ItemDetailActivity.class);
//
//            intent.putExtra("part_no", item.getPartNo());
//            intent.putExtra("part_name", item.getPartName());
//            intent.putExtra("maincategory_name", item.getMainCategoryName());
//            intent.putExtra("subcategory_name", item.getSubCategoryName());
//            intent.putExtra("mrp", item.getMrp());
//            intent.putExtra("sale_rate", item.getSaleRate());
//            intent.putExtra("unit_nm", item.getUnitName());
//            intent.putExtra("avl_stock", item.getAvlStock());
//
//            // TEMP: single image → convert to list for slider
//            ArrayList<Integer> images = new ArrayList<>();
//
//            images.add(R.drawable.nutbolt);
//            images.add(R.drawable.partsimg);
//            images.add(R.drawable.nutbolt);
//            images.add(R.drawable.partsimg);
//
//// pass via intent
//            intent.putIntegerArrayListExtra("images", images);
//            context.startActivity(intent);
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return itemList == null ? 0 : itemList.size();
//    }
//
//    // 🔹 Helper to prevent null / empty values (PER FIELD)
//    private String safe(String value, String fallback) {
//        return (value != null && !value.trim().isEmpty()) ? value : fallback;
//    }
//
//    // =====================================================
//    // 🔹 VIEW HOLDER
//    // =====================================================
//    static class ItemViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView imgItem;
//        TextView tvPartNo, tvPartName, tvMrp, tvSaleRate;
//
//        ItemViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            imgItem = itemView.findViewById(R.id.imgItems);
//            tvPartNo = itemView.findViewById(R.id.tvPartNo);
//            tvPartName = itemView.findViewById(R.id.tvPartName);
//            tvMrp = itemView.findViewById(R.id.tvMrp);
//            tvSaleRate = itemView.findViewById(R.id.tvSaleRate);
//        }
//    }
//}

//api image for single or multiple
package com.example.shoppingapp.CategoryItemsScreen;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.example.shoppingapp.network.response.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private static final String TAG = "ItemsAdapter";

    private final Context context;
    private final List<ItemModel> itemList;

    public ItemsAdapter(Context context, List<ItemModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.items, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        ItemModel item = itemList.get(position);

        // =====================================================
        // 🔹 LOGGING
        // =====================================================
        Log.d(TAG, "Binding item at position: " + position);
        Log.d(TAG, "Part Name: " + safe(item.getPartName(), "N/A"));
        Log.d(TAG, "Image URL: " + safe(item.getImgPath(), "NULL"));

        // =====================================================
        // 🔹 TEXT DATA (NULL SAFE)
        // =====================================================
        holder.tvPartNo.setText("Part No: " + safe(item.getPartNo(), "N/A"));
        holder.tvPartName.setText(safe(item.getPartName(), "N/A"));
        holder.tvMrp.setText("MRP: ₹" + safe(item.getMrp(), "0"));
        holder.tvSaleRate.setText("Price: ₹" + safe(item.getSaleRate(), "0"));

        // =====================================================
        // 🔹 IMAGE HANDLING (FULL URL ONLY)
        // =====================================================
        String imageUrl = item.getImgPath();

        if (imageUrl != null && !imageUrl.trim().isEmpty()) {

            Log.d(TAG, "Loading image: " + imageUrl);

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.nutbolt)
                    .error(R.drawable.nutbolt)
                    .into(holder.imgItem);

        } else {
            Log.e(TAG, "Image URL missing, loading fallback drawable");
            holder.imgItem.setImageResource(R.drawable.nutbolt);
        }

        // =====================================================
        // 🔥 ITEM CLICK → ITEM DETAIL
        // =====================================================
        holder.itemView.setOnClickListener(v -> {

            Log.d(TAG, "Item clicked: " + safe(item.getPartName(), "N/A"));

            Intent intent = new Intent(
                    context,
                    com.example.shoppingapp.itemdetailscreen.ItemDetailActivity.class
            );

            // 🔹 PASS TEXT DATA
            intent.putExtra("part_no", safe(item.getPartNo(), ""));
            intent.putExtra("part_name", safe(item.getPartName(), ""));
            intent.putExtra("maincategory_name", safe(item.getMainCategoryName(), ""));
            intent.putExtra("subcategory_name", safe(item.getSubCategoryName(), ""));
            intent.putExtra("mrp", safe(item.getMrp(), "0"));
            intent.putExtra("sale_rate", safe(item.getSaleRate(), "0"));
            intent.putExtra("unit_nm", safe(item.getUnitName(), ""));
            intent.putExtra("avl_stock", safe(item.getAvlStock(), "0"));

            // =====================================================
            // 🔹 IMAGE LIST (SINGLE → MULTI READY)
            // =====================================================
            ArrayList<String> images = new ArrayList<>();

            if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                images.add(imageUrl);
                Log.d(TAG, "Image added to slider list");
            } else {
                Log.e(TAG, "No image added to slider list");
            }

            intent.putStringArrayListExtra("images", images);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    // =====================================================
    // 🔹 NULL SAFETY
    // =====================================================
    private String safe(String value, String fallback) {
        return (value != null && !value.trim().isEmpty())
                ? value
                : fallback;
    }

    // =====================================================
    // 🔹 VIEW HOLDER
    // =====================================================
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imgItem;
        TextView tvPartNo, tvPartName, tvMrp, tvSaleRate;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItem = itemView.findViewById(R.id.imgItems);
            tvPartNo = itemView.findViewById(R.id.tvPartNo);
            tvPartName = itemView.findViewById(R.id.tvPartName);
            tvMrp = itemView.findViewById(R.id.tvMrp);
            tvSaleRate = itemView.findViewById(R.id.tvSaleRate);
        }
    }
}
