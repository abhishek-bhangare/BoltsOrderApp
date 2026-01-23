
package com.example.shoppingapp.CategoriesScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;

import java.util.List;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.ViewHolder> {

    private List<CategoryItemModel> list;
    private Context context;
    private OnCategoryItemClick listener;
    private String mainCategory;   // ⭐ NEW: to know parent category

    // Constructor with mainCategory
    public CategoryItemAdapter(List<CategoryItemModel> list, Context context, String mainCategory, OnCategoryItemClick listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
        this.mainCategory = mainCategory;
    }

    // Interface for click callback
    public interface OnCategoryItemClick {
        void onItemClick(CategoryItemModel model, String mainCategory);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CategoryItemModel model = list.get(position);

        holder.imgItem.setImageResource(model.getImage());
        holder.txtItemName.setText(model.getName());

        // CLICK EVENT
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(model, mainCategory);   // ⭐ Send category also
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgItem;
        TextView txtItemName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItem = itemView.findViewById(R.id.imgItem);
            txtItemName = itemView.findViewById(R.id.txtItemName);
        }
    }
}
