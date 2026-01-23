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

public class RightCategoryAdapter extends RecyclerView.Adapter<RightCategoryAdapter.ViewHolder> {

    private List<RightCategoryModel> list;
    private Context context;
    private int selectedPosition = 0;

    private OnRightCategoryClick listener;

    public interface OnRightCategoryClick {
        void onCategoryClick(String categoryName);
    }

    public RightCategoryAdapter(List<RightCategoryModel> list, Context context,
                                OnRightCategoryClick listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.right_category_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RightCategoryModel model = list.get(position);

        holder.imgCat.setImageResource(model.getImage());
        holder.txtCat.setText(model.getName());

        // Highlight selected item
        if (position == selectedPosition) {
            holder.txtCat.setTextColor(Color.parseColor("#0077CC"));
        } else {
            holder.txtCat.setTextColor(Color.BLACK);
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

        ImageView imgCat;
        TextView txtCat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCat = itemView.findViewById(R.id.imgRightCat);
            txtCat = itemView.findViewById(R.id.txtRightCat);
        }
    }
}
