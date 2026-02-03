//package com.example.shoppingapp.CategoryItemsScreen;
//
//import android.content.Context;
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
//
//        // 🔹 Safe text binding
//        holder.tvItemName.setText(safe(item.getPartName(), "N/A"));
//        holder.tvPrice.setText("₹ " + safe(item.getSaleRate(), "0"));
//        holder.tvUnit.setText(safe(item.getUnitName(), "-"));
//
//        // 🔹 Stock handling
//        String stock = safe(item.getAvlStock(), "0");
//        if ("0".equals(stock)) {
//            holder.tvStock.setText("Out of stock");
//            holder.tvStock.setTextColor(
//                    context.getResources().getColor(android.R.color.holo_red_dark)
//            );
//        } else {
//            holder.tvStock.setText("Stock: " + stock);
//            holder.tvStock.setTextColor(
//                    context.getResources().getColor(android.R.color.darker_gray)
//            );
//        }
//
//        // 🔹 Image handling
//        if (item.getImgPath() != null && !item.getImgPath().trim().isEmpty()) {
//            Glide.with(context)
//                    .load(item.getImgPath())
//                    .placeholder(R.drawable.nutbolt)
//                    .into(holder.imgItem);
//        } else {
//            holder.imgItem.setImageResource(R.drawable.nutbolt);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return itemList == null ? 0 : itemList.size();
//    }
//
//    // 🔹 Helper to prevent empty fields
//    private String safe(String value, String fallback) {
//        return (value != null && !value.trim().isEmpty()) ? value : fallback;
//    }
//
//    static class ItemViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView imgItem;
//        TextView tvItemName, tvPrice, tvStock, tvUnit;
//
//        ItemViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imgItem = itemView.findViewById(R.id.imgItems);
//            tvItemName = itemView.findViewById(R.id.tvItemsName);
//            tvPrice = itemView.findViewById(R.id.tvPrice);
//            tvStock = itemView.findViewById(R.id.tvStock);
//            tvUnit = itemView.findViewById(R.id.tvUnit);
//        }
//    }
//}
package com.example.shoppingapp.CategoryItemsScreen;

import android.content.Context;
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

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private Context context;
    private List<ItemModel> itemList;

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

        // 🔹 PART NO
        holder.tvPartNo.setText(
                "Part No: " + safe(item.getPartNo(), "N/A")
        );

        // 🔹 PART NAME
        holder.tvPartName.setText(
                safe(item.getPartName(), "N/A")
        );

        // 🔹 MRP
        holder.tvMrp.setText(
                "MRP: ₹" + safe(item.getMrp(), "0")
        );

        // 🔹 SALE RATE
        holder.tvSaleRate.setText(
                "Price: ₹" + safe(item.getSaleRate(), "0")
        );

        // 🔹 IMAGE HANDLING (SAFE)
        if (item.getImgPath() != null && !item.getImgPath().trim().isEmpty()) {
            Glide.with(context)
                    .load(item.getImgPath())
                    .placeholder(R.drawable.nutbolt)
                    .error(R.drawable.nutbolt)
                    .into(holder.imgItem);
        } else {
            holder.imgItem.setImageResource(R.drawable.nutbolt);
        }
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    // 🔹 Helper to prevent null / empty values (PER FIELD)
    private String safe(String value, String fallback) {
        return (value != null && !value.trim().isEmpty()) ? value : fallback;
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
