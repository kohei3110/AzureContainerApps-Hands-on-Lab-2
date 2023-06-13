package com.microsoft.cloudworkshop.models;

import java.time.LocalDateTime;

public class Product {

    public int ProductId;

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String CategoryName;

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String ProductName;

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String Color;

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public double StandardCost;

    public double getStandardCost() {
        return StandardCost;
    }

    public void setStandardCost(double standardCost) {
        StandardCost = standardCost;
    }

    public LocalDateTime SellStartDate;

    public LocalDateTime getSellStartDate() {
        return SellStartDate;
    }

    public void setSellStartDate(LocalDateTime sellStartDate) {
        SellStartDate = sellStartDate;
    }
}
