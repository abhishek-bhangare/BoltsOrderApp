package com.example.shoppingapp.OrderScreen;

public class OrderCardModel {

    private String cardName;
    private int imageResId;
    private boolean favorite;

    public OrderCardModel(String cardName, int imageResId) {
        this.cardName = cardName;
        this.imageResId = imageResId;
        this.favorite = false; // default
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
