package com.example.shoppingapp.HomeScreen;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.modelclass.CategoryModel;
import com.example.shoppingapp.subcategory.SubCategoryActivity;

import java.util.List;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.ViewHolder> {

    private final List<CategoryModel> list;

    public GroceryAdapter(List<CategoryModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grocery_homecategory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CategoryModel model = list.get(position);

        // Category name from API
        holder.tvName.setText(model.getCatName());

        // Temporary static icon (API has no image yet)
        holder.imgCategory.setImageResource(R.drawable.nutbolt);
        // 🔥 CLICK → OPEN SUBCATEGORY SCREEN
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(
                    v.getContext(),
                    SubCategoryActivity.class
            );

            // ✅ Convert String → int
            intent.putExtra(
                    "category_id",
                    Integer.parseInt(model.getCatId())
            );

            intent.putExtra("category_name", model.getCatName());

            v.getContext().startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
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
