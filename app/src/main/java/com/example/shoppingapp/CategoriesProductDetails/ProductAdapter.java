

package com.example.shoppingapp.CategoriesProductDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.CartScreen.CartModel;
import com.example.shoppingapp.R;
import com.example.shoppingapp.utils.CartStorage;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<ProductModel> list;
    private Context context;

    // ⭐ Callback to notify ProductDetailsFragment when cart changes
    private OnCartChangeListener cartListener;

    public interface OnCartChangeListener {
        void onCartChanged();
    }

    public ProductAdapter(List<ProductModel> list, Context context, OnCartChangeListener listener) {
        this.list = list;
        this.context = context;
        this.cartListener = listener;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.product_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {

        ProductModel model = list.get(position);
        String productId = model.getProductId();   // ⭐ FIXED — UNIQUE ID

        holder.imgProduct.setImageResource(model.getImageRes());
        holder.txtProductName.setText(model.getName());
        holder.txtProductPrice.setText(model.getPrice());

        // ⭐ Check if product already in cart
        int cartQty = getCartQuantity(productId);

        if (cartQty > 0) {
            holder.btnAdd.setVisibility(View.GONE);
            holder.qtyLayout.setVisibility(View.VISIBLE);
            holder.txtQty.setText(String.valueOf(cartQty));
        } else {
            holder.qtyLayout.setVisibility(View.GONE);
            holder.btnAdd.setVisibility(View.VISIBLE);
        }


        // ================== ADD BUTTON ==================
        holder.btnAdd.setOnClickListener(v -> {

            holder.btnAdd.setVisibility(View.GONE);
            holder.qtyLayout.setVisibility(View.VISIBLE);
            holder.txtQty.setText("1");

            CartStorage.addToCart(context,
                    new CartModel(
                            productId,
                            model.getName(),
                            "1 unit",
                            Integer.parseInt(model.getPrice().replace("₹", "")),
                            1,
                            model.getImageRes()
                    )
            );

            cartListener.onCartChanged();
        });

        // ================== PLUS BUTTON ==================
        holder.btnPlus.setOnClickListener(v -> {

            int qty = Integer.parseInt(holder.txtQty.getText().toString());
            qty++;
            holder.txtQty.setText(String.valueOf(qty));

            CartStorage.addToCart(context,
                    new CartModel(
                            productId,
                            model.getName(),
                            "1 unit",
                            Integer.parseInt(model.getPrice().replace("₹", "")),
                            1,
                            model.getImageRes()
                    )
            );

            cartListener.onCartChanged();
        });

        // ================== MINUS BUTTON ==================
        holder.btnMinus.setOnClickListener(v -> {

            int qty = Integer.parseInt(holder.txtQty.getText().toString());
            qty--;

            if (qty <= 0) {
                holder.qtyLayout.setVisibility(View.GONE);
                holder.btnAdd.setVisibility(View.VISIBLE);

                CartStorage.removeItem(context, productId);

            } else {
                holder.txtQty.setText(String.valueOf(qty));
                CartStorage.decreaseQty(context, productId);
            }

            cartListener.onCartChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // ⭐ Check existing quantity in cart
    private int getCartQuantity(String id) {
        for (CartModel item : CartStorage.getCart(context)) {
            if (item.getId().equals(id)) {
                return item.getQty();
            }
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct;
        TextView txtProductName, txtProductPrice;
        TextView btnAdd, btnPlus, btnMinus, txtQty;
        View qtyLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);

            btnAdd = itemView.findViewById(R.id.btnAdd);
            qtyLayout = itemView.findViewById(R.id.qtyLayout);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            txtQty = itemView.findViewById(R.id.txtQty);
        }
    }
}
