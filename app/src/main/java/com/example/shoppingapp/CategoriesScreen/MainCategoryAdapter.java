package com.example.shoppingapp.CategoriesScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.network.response.MainCategoryResponse;

import java.util.List;

public class MainCategoryAdapter
        extends RecyclerView.Adapter<MainCategoryAdapter.ViewHolder> {

    // 🔹 Callback to Fragment
    public interface OnCategoryClickListener {
        void onCategoryClick(MainCategoryResponse model);
    }

    private final List<MainCategoryResponse> list;
    private final OnCategoryClickListener listener;

    // 🔥 Default selected = first item (Two Wheeler)
    private int selectedPosition = 0;

    public MainCategoryAdapter(
            List<MainCategoryResponse> list,
            OnCategoryClickListener listener) {

        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_maincategories, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        if (list == null || position >= list.size()) return;

        MainCategoryResponse model = list.get(position);
        if (model == null) return;

        String mcateId = model.getMcateId();
        String name = model.getCategoryName();

        holder.tvName.setText(name != null ? name : "N/A");

        // 🔹 Image mapping (same as Home)
        if ("1".equals(mcateId)) {
            holder.imgCategory.setImageResource(R.drawable.twowheeler_img);
        } else if ("2".equals(mcateId)) {
            holder.imgCategory.setImageResource(R.drawable.fourwheeler_img);
        } else if ("3".equals(mcateId)) {
            holder.imgCategory.setImageResource(R.drawable.threewheeler_img);
        } else {
            holder.imgCategory.setImageResource(R.drawable.nutbolt);
        }

        // 🔥 Selection UI
        holder.itemView.setAlpha(
                position == selectedPosition ? 1f : 0.5f
        );

        holder.itemView.setOnClickListener(v -> {

            if (selectedPosition == position) return;

            selectedPosition = position;
            notifyDataSetChanged();

            // 🔥 Notify fragment
            listener.onCategoryClick(model);
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
