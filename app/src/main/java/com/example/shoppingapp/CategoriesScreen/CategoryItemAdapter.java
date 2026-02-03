package com.example.shoppingapp.CategoriesScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.network.response.SubCategoryResponse;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class CategoryItemAdapter
        extends RecyclerView.Adapter<CategoryItemAdapter.ViewHolder> {

    private final List<SubCategoryResponse> list;
    private final OnCategoryItemClick listener;

    // ✅ Click callback (updated)
    public interface OnCategoryItemClick {
        void onItemClick(SubCategoryResponse model);
    }

    // ✅ Constructor updated (name unchanged)
    public CategoryItemAdapter(
            List<SubCategoryResponse> list,
            OnCategoryItemClick listener
    ) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {
        SubCategoryResponse model = list.get(position);

        holder.tvName.setText(
                model.getSubCatename() != null
                        ? model.getSubCatename()
                        : "N/A"
        );

        holder.cardCategory.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(model);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    // ================= VIEW HOLDER =================
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        MaterialCardView cardCategory;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            cardCategory = itemView.findViewById(R.id.cardCategory);
        }
    }

}
