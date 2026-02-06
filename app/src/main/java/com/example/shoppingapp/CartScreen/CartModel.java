//package com.example.shoppingapp.CartScreen;
//
//public class CartModel {
//
//    private String id;
//    private String name;
//    private String weight;
//    private int price;
//    private int qty;
//    private int image; // drawable resource ID
//
//    public CartModel(String id, String name, String weight, int price, int qty, int image) {
//        this.id = id;
//        this.name = name;
//        this.weight = weight;
//        this.price = price;
//        this.qty = qty;
//        this.image = image;
//    }
//
//    // --- Getters ---
//    public String getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getWeight() {
//        return weight;
//    }
//
//    public int getPrice() {
//        return price;
//    }
//
//    public int getQty() {
//        return qty;
//    }
//
//    public int getImage() {
//        return image;
//    }
//
//    // --- Setters ---
//    public void setQty(int qty) {
//        this.qty = qty;
//    }
//
//    public void setImage(int image) {
//        this.image = image;
//    }
//}
//
//package com.example.shoppingapp.CartScreen;
//
//public class CartModel {
//
//    private String itemId;
//    private String partName;
//    private String unitName;
//    private int saleRate;
//    private int qty;
//    private String imageUrl;
//
//    // ✅ CONSTRUCTOR (MATCHES ADD TO CART)
//    public CartModel(
//            String itemId,
//            String partName,
//            String unitName,
//            int saleRate,
//            int qty,
//            String imageUrl
//    ) {
//        this.itemId = itemId;
//        this.partName = partName;
//        this.unitName = unitName;
//        this.saleRate = saleRate;
//        this.qty = qty;
//        this.imageUrl = imageUrl;
//    }
//
//    // ===== GETTERS =====
//    public String getItemId() {
//        return itemId;
//    }
//
//    public String getPartName() {
//        return partName;
//    }
//
//    public String getUnitName() {
//        return unitName;
//    }
//
//    public int getSaleRate() {
//        return saleRate;
//    }
//
//    public int getQty() {
//        return qty;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    // ===== SETTERS =====
//    public void setQty(int qty) {
//        this.qty = qty;
//    }
//}

package com.example.shoppingapp.CartScreen;

public class CartModel {

    private String itemId;
    private String partName;
    private String unitName;
    private double mrp;   // ✅ GST INCLUDED MRP
    private int qty;
    private String imageUrl;

    public CartModel(
            String itemId,
            String partName,
            String unitName,
            double mrp,
            int qty,
            String imageUrl
    ) {
        this.itemId = itemId;
        this.partName = partName;
        this.unitName = unitName;
        this.mrp = mrp;
        this.qty = qty;
        this.imageUrl = imageUrl;
    }

    public String getItemId() {
        return itemId;
    }

    public String getPartName() {
        return partName;
    }

    public String getUnitName() {
        return unitName;
    }

    public double getMrp() {
        return mrp;
    }

    public int getQty() {
        return qty;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
