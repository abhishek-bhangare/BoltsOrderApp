package com.example.shoppingapp.HomeScreen;

public class HomeCategoryModel {

    private String categoryName;
    private int categoryImage;

    public HomeCategoryModel(String categoryName, int categoryImage) {
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryImage() {
        return categoryImage;
    }
}
