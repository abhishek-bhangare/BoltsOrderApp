
package com.example.shoppingapp.CartScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.utils.CartStorage;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<CartModel> cartList;
    private CartUpdateListener listener;

    // Callback to update totals in CartFragment
    public interface CartUpdateListener {
        void onCartUpdated();
    }

    public CartAdapter(Context context, List<CartModel> cartList, CartUpdateListener listener) {
        this.context = context;
        this.cartList = cartList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartModel model = cartList.get(position);

        holder.txtTitle.setText(model.getName());
        holder.txtWeight.setText(model.getWeight());
        holder.txtPrice.setText("₹" + model.getPrice());
        holder.txtQty.setText(String.valueOf(model.getQty()));
        holder.imgProduct.setImageResource(R.drawable.nutbolt);

        // ------------------------- PLUS BUTTON -------------------------
        holder.btnPlus.setOnClickListener(v -> {
            model.setQty(model.getQty() + 1);
            holder.txtQty.setText(String.valueOf(model.getQty()));

            // Save changes to cart
            CartStorage.saveCart(context, cartList);
            listener.onCartUpdated();
        });

        // ------------------------- MINUS BUTTON -------------------------
        holder.btnMinus.setOnClickListener(v -> {
            int q = model.getQty();

            if (q > 1) {
                model.setQty(q - 1);
                holder.txtQty.setText(String.valueOf(model.getQty()));
            } else {
                // If qty reaches 0 → remove the item entirely
                cartList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartList.size());
            }

            // Save changes to cart
            CartStorage.saveCart(context, cartList);
            listener.onCartUpdated();
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct;
        TextView txtTitle, txtWeight, txtPrice, txtQty,btnPlus,btnMinus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtWeight = itemView.findViewById(R.id.txtWeight);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtQty = itemView.findViewById(R.id.txtQty);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
        }
    }
}
