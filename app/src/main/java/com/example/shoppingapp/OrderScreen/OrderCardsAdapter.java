
package com.example.shoppingapp.OrderScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.FavoriteManager;
import com.example.shoppingapp.R;
import com.example.shoppingapp.ProfileScreen.profilepages.WishlistActivity;

import java.util.List;

public class OrderCardsAdapter extends RecyclerView.Adapter<OrderCardsAdapter.CardViewHolder> {

    private Context context;
    private List<OrderCardModel> cardList;
    private OnCardClickListener listener;

    public OrderCardsAdapter(Context context, List<OrderCardModel> cardList, OnCardClickListener listener) {
        this.context = context;
        this.cardList = cardList;
        this.listener = listener;
    }

    public OrderCardsAdapter(Context context, List<OrderCardModel> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_card_order, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        OrderCardModel model = cardList.get(position);
        if (model == null) return;

        holder.cardTitle.setText(model.getCardName());
        holder.cardImage.setImageResource(model.getImageResId());

        updateFavIcon(holder.favIcon, model.isFavorite());

        // ⭐ FAVORITE LOGIC
        holder.favIcon.setOnClickListener(v -> {

            boolean newState = !model.isFavorite();
            model.setFavorite(newState);

            // ---------------------------
            // ADD TO FAVORITE
            // ---------------------------
            if (newState) {
                FavoriteManager.addToFavorite(model);

            } else {

                // ---------------------------
                // REMOVE FROM FAVORITES
                // ---------------------------
                FavoriteManager.removeFromFavorite(model);

                // If we are inside wishlist screen → remove card from list
                if (context instanceof WishlistActivity) {

                    int index = cardList.indexOf(model);

                    if (index >= 0) {
                        cardList.remove(index);
                        notifyItemRemoved(index);
                    } else {
                        notifyDataSetChanged();
                    }

                    // ⭐ Save updated favorites
                    FavoriteManager.save(context);

                    return; // prevent extra UI update for this case
                }
            }

            // ⭐ SAVE AFTER EVERY CHANGE
            FavoriteManager.save(context);

            // Update icon
            updateFavIcon(holder.favIcon, newState);

            // Small pop animation
            holder.favIcon.animate().scaleX(1.2f).scaleY(1.2f).setDuration(120)
                    .withEndAction(() ->
                            holder.favIcon.animate().scaleX(1f).scaleY(1f).setDuration(120)
                    ).start();
        });

        if (listener != null) {
            holder.itemView.setOnClickListener(v ->
                    listener.onCardClick(model, holder.getAdapterPosition())
            );
        }
    }

    @Override
    public int getItemCount() {
        return cardList != null ? cardList.size() : 0;
    }

    private void updateFavIcon(ImageView favIcon, boolean isFav) {
        if (isFav) {
            favIcon.setImageResource(R.drawable.ic_favorite_filled);
            favIcon.setColorFilter(ContextCompat.getColor(context, R.color.red));
        } else {
            favIcon.setImageResource(R.drawable.ic_favorite_outline);
            favIcon.setColorFilter(ContextCompat.getColor(context, R.color.black));
        }
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {

        ImageView cardImage, favIcon;
        TextView cardTitle;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            cardImage = itemView.findViewById(R.id.cardImage);
            favIcon = itemView.findViewById(R.id.favIcon);
            cardTitle = itemView.findViewById(R.id.cardTitle);
        }
    }

    public interface OnCardClickListener {
        void onCardClick(OrderCardModel model, int position);
    }
}

