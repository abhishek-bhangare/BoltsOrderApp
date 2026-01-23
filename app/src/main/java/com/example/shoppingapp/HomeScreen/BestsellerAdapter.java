package com.example.shoppingapp.HomeScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;

import java.util.List;

public class BestsellerAdapter extends RecyclerView.Adapter<BestsellerAdapter.ViewHolder> {

    private final List<HomeCategoryModel> list;

    public BestsellerAdapter(List<HomeCategoryModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bestseller_homecategory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeCategoryModel model = list.get(position);
        holder.tvName.setText(model.getCategoryName());
        holder.imgCategory.setImageResource(model.getCategoryImage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // 🔹 ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCategory;
        TextView tvName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.imgCategory);
            tvName = itemView.findViewById(R.id.tvCategoryName);
        }
    }
}
