package com.example.shoppingapp.CartScreen;

public class CartModel {

    private String id;
    private String name;
    private String weight;
    private int price;
    private int qty;
    private int image; // drawable resource ID

    public CartModel(String id, String name, String weight, int price, int qty, int image) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.qty = qty;
        this.image = image;
    }

    // --- Getters ---
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWeight() {
        return weight;
    }

    public int getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    public int getImage() {
        return image;
    }

    // --- Setters ---
    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
