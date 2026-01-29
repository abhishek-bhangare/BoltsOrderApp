//package com.example.shoppingapp.HomeScreen;
//
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.shoppingapp.R;
//import com.example.shoppingapp.modelclass.CategoryModel;
//import com.example.shoppingapp.subcategory.SubCategoryActivity;
//
//import java.util.List;
//
//public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.ViewHolder> {
//
//    private final List<CategoryModel> list;
//
//    public GroceryAdapter(List<CategoryModel> list) {
//        this.list = list;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_grocery_homecategory, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        CategoryModel model = list.get(position);
//
//        // Category name from API
//        holder.tvName.setText(model.getCatName());
//
//        // Temporary static icon (API has no image yet)
//        holder.imgCategory.setImageResource(R.drawable.nutbolt);
//        // 🔥 CLICK → OPEN SUBCATEGORY SCREEN
//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(
//                    v.getContext(),
//                    SubCategoryActivity.class
//            );
//
//            // ✅ Convert String → int
//            intent.putExtra(
//                    "category_id",
//                    Integer.parseInt(model.getCatId())
//            );
//
//            intent.putExtra("category_name", model.getCatName());
//
//            v.getContext().startActivity(intent);
//        });
//
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return list != null ? list.size() : 0;
//    }
//
//    // 🔹 ViewHolder
//    static class ViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView imgCategory;
//        TextView tvName;
//
//        ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imgCategory = itemView.findViewById(R.id.imgCategory);
//            tvName = itemView.findViewById(R.id.tvCategoryName);
//        }
//    }
//}

package com.example.shoppingapp.HomeScreen;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.network.response.MainCategoryResponse;
import com.example.shoppingapp.subcategory.SubCategoryActivity;

import java.util.List;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.ViewHolder> {

    private static final String TAG = "GroceryAdapter";

    private final List<MainCategoryResponse> list;

    public GroceryAdapter(List<MainCategoryResponse> list) {
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

        // 🟡 Null & bounds safety
        if (list == null || position >= list.size()) {
            Log.w(TAG, "Invalid adapter position: " + position);
            return;
        }

        MainCategoryResponse model = list.get(position);

        if (model == null) {
            Log.w(TAG, "Category model is NULL at position: " + position);
            return;
        }

        String categoryId = model.getMcateId();
        String categoryName = model.getCategoryName();

        // 🟢 Set category name
        holder.tvName.setText(categoryName != null ? categoryName : "N/A");

        // 🟢 SET IMAGE BASED ON CATEGORY ID
        if ("1".equals(categoryId)) {
            // Two Wheeler
            holder.imgCategory.setImageResource(R.drawable.twowheeler_img);

        } else if ("2".equals(categoryId)) {
            // Four Wheeler
            holder.imgCategory.setImageResource(R.drawable.fourwheeler_img);

        } else if ("3".equals(categoryId)) {
            // Three Wheeler
            holder.imgCategory.setImageResource(R.drawable.threewheeler_img);

        } else {
            // Fallback image
            holder.imgCategory.setImageResource(R.drawable.nutbolt);
        }

        // 🔥 CLICK → OPEN SUBCATEGORY
        holder.itemView.setOnClickListener(v -> {

            if (categoryId == null) {
                Log.e(TAG, "Category ID is NULL, cannot open subcategory");
                return;
            }

            Log.d(TAG, "Clicked Category → ID: " + categoryId +
                    ", Name: " + categoryName);

            Intent intent = new Intent(
                    v.getContext(),
                    SubCategoryActivity.class
            );

            // 🟢 Safe String → Int conversion
            try {
                intent.putExtra("category_id", Integer.parseInt(categoryId));
            } catch (NumberFormatException e) {
                Log.e(TAG, "Invalid category ID: " + categoryId, e);
                return;
            }

            intent.putExtra(
                    "category_name",
                    categoryName != null ? categoryName : "N/A"
            );

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    // ================= VIEW HOLDER =================

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
