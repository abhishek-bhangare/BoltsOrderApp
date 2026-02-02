
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
    private final String comId;   // ✅ Vehicle type (2W / 3W / 4W)

    // ✅ UPDATED CONSTRUCTOR
    public GroceryAdapter(List<MainCategoryResponse> list, String comId) {
        this.list = list;
        this.comId = comId;
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

        // 🟡 Safety checks
        if (list == null || position >= list.size()) {
            Log.w(TAG, "Invalid adapter position: " + position);
            return;
        }

        MainCategoryResponse model = list.get(position);
        if (model == null) {
            Log.w(TAG, "Category model is NULL at position: " + position);
            return;
        }

        String mcateId = model.getMcateId();
        String categoryName = model.getCategoryName();

        // 🟢 Set category name
        holder.tvName.setText(categoryName != null ? categoryName : "N/A");

        // 🟢 SET IMAGE BASED ON MAIN CATEGORY ID (mcate_id)
        if ("1".equals(mcateId)) {
            // Two Wheeler
            holder.imgCategory.setImageResource(R.drawable.twowheeler_img);

        } else if ("2".equals(mcateId)) {
            // Four Wheeler
            holder.imgCategory.setImageResource(R.drawable.fourwheeler_img);

        } else if ("3".equals(mcateId)) {
            // Three Wheeler
            holder.imgCategory.setImageResource(R.drawable.threewheeler_img);

        } else {
            // Fallback
            holder.imgCategory.setImageResource(R.drawable.nutbolt);
        }


        // 🔥 CLICK → OPEN SUBCATEGORY
        holder.itemView.setOnClickListener(v -> {

            if (mcateId == null) {
                Log.e(TAG, "mcate_id is NULL, cannot open subcategory");
                return;
            }

            Log.d(TAG, "Clicked Main Category → mcate_id: " + mcateId +
                    ", Name: " + categoryName +
                    ", com_id: " + comId);

            Intent intent = new Intent(
                    v.getContext(),
                    SubCategoryActivity.class
            );

            // ✅ REQUIRED FOR SUBCATEGORY API
            intent.putExtra("mcate_id", mcateId);
            intent.putExtra("com_id", comId);
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
