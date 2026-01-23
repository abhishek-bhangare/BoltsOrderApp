
package com.example.shoppingapp.CategoriesProductDetails;

public class ProductModel {

    private String productId;   // UNIQUE ID
    private int imageRes;
    private String name;
    private String price;

    private int quantity;        // Qty in UI
    private boolean isAdded;     // To toggle ADD / qty UI

    public ProductModel(String productId, int imageRes, String name, String price) {
        this.productId = productId;
        this.imageRes = imageRes;
        this.name = name;
        this.price = price;

        this.quantity = 0;
        this.isAdded = false;
    }

    public String getProductId() {
        return productId;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    // Quantity getter/setter
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }
}
