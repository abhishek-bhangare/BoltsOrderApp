//package com.example.shoppingapp.subcategory;
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
//import com.example.shoppingapp.modelclass.SubCategoryModel;
//
//import java.util.List;
//
//public class SubCategoryAdapter
//        extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
//
//    private final List<SubCategoryModel> list;
//
//    public SubCategoryAdapter(List<SubCategoryModel> list) {
//        this.list = list;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_subcategory, parent, false);
//
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        SubCategoryModel model = list.get(position);
//
//        // 🔹 Set subcategory name
//        holder.tvSubCategoryName.setText(model.getSubcat_name());
//
//        // 🔹 Temporary static icon
//        holder.imgSubCategory.setImageResource(R.drawable.nutbolt);
//
//        // 🔥 CLICK → NEXT SCREEN (Items)
//        holder.itemView.setOnClickListener(v -> {
//
//            // 🔜 Next step (when you create ItemActivity)
//            /*
//            Intent intent = new Intent(
//                    v.getContext(),
//                    ItemActivity.class
//            );
//            intent.putExtra("subcat_id", model.getSubcatid());
//            intent.putExtra("subcat_name", model.getSubcat_name());
//            v.getContext().startActivity(intent);
//            */
//
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return list != null ? list.size() : 0;
//    }
//
//    // 🔹 ViewHolder
//    static class ViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView imgSubCategory;
//        TextView tvSubCategoryName;
//
//        ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imgSubCategory = itemView.findViewById(R.id.imgSubCategory);
//            tvSubCategoryName = itemView.findViewById(R.id.tvSubCategoryName);
//        }
//    }
//}

package com.example.shoppingapp.subcategory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.network.response.SubCategoryResponse;

import java.util.List;

public class SubCategoryAdapter
        extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {

    private final List<SubCategoryResponse> list;

    public SubCategoryAdapter(List<SubCategoryResponse> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subcategory, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (list == null || position >= list.size()) {
            return;
        }

        SubCategoryResponse model = list.get(position);

        if (model == null) {
            holder.tvSubCategoryName.setText("N/A");
            holder.imgSubCategory.setImageResource(R.drawable.nutbolt);
            return;
        }

        String name = model.getSubCatename();
        holder.tvSubCategoryName.setText(name != null ? name : "N/A");

        holder.imgSubCategory.setImageResource(R.drawable.nutbolt);
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgSubCategory;
        TextView tvSubCategoryName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSubCategory = itemView.findViewById(R.id.imgSubCategory);
            tvSubCategoryName = itemView.findViewById(R.id.tvSubCategoryName);
        }
    }
}
