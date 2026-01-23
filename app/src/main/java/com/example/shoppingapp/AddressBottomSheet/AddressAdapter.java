package com.example.shoppingapp.AddressBottomSheet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppingapp.R;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private List<AddressModel> list;
    private OnAddressClick listener;

    public interface OnAddressClick {
        void onClick(AddressModel model);
    }

    public AddressAdapter(List<AddressModel> list, OnAddressClick listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_address, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddressModel model = list.get(position);

        holder.txtTitle.setText(model.getTitle());
        holder.txtFull.setText(model.getFullAddress());
        holder.iconAddress.setImageResource(R.drawable.ic_home);

        holder.itemView.setOnClickListener(v -> listener.onClick(model));
    }

    @Override
    public int getItemCount() { return list.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtFull;
        ImageView iconAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtAddressTitle);
            txtFull = itemView.findViewById(R.id.txtAddressFull);
            iconAddress = itemView.findViewById(R.id.iconAddress);
        }
    }
}
