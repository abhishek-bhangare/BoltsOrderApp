package com.example.shoppingapp.CategoriesProductDetails;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;

import java.util.List;

public class LeftCategoryAdapter extends RecyclerView.Adapter<LeftCategoryAdapter.ViewHolder> {

    private List<LeftCategoryModel> list;
    private Context context;
    private int selectedPosition = 0;
    private OnLeftCategoryClick listener;

    public interface OnLeftCategoryClick {
        void onCategoryClick(String categoryName);
    }

    public LeftCategoryAdapter(List<LeftCategoryModel> list, Context context, OnLeftCategoryClick listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.left_category_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        LeftCategoryModel model = list.get(position);

        holder.imgCategory.setImageResource(model.getImage());
        holder.txtCategory.setText(model.getName());

        // Highlight selected
        if (position == selectedPosition) {
            holder.imgCategory.setBackgroundResource(R.drawable.rounded_selected_bg);
            holder.txtCategory.setTextColor(Color.parseColor("#0077CC"));
        } else {
            holder.imgCategory.setBackgroundResource(R.drawable.rounded_white_bg);
            holder.txtCategory.setTextColor(Color.BLACK);
        }

        holder.itemView.setOnClickListener(view -> {
            selectedPosition = position;
            notifyDataSetChanged();
            listener.onCategoryClick(model.getName());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCategory;
        TextView txtCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCategory = itemView.findViewById(R.id.imgCategory);
            txtCategory = itemView.findViewById(R.id.txtCategory);
        }
    }
}
