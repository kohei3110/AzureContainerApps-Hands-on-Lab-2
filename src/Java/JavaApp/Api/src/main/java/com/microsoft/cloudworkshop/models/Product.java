package com.microsoft.cloudworkshop.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public class Product {

    @Id
    @Column("ProductId")
    public int ProductId;

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    @Column("CategoryName")
    public String CategoryName;

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    @Column("ProductName")
    public String ProductName;

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    @Column("Color")
    public String Color;

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    @Column("StandardCost")
    public double StandardCost;

    public double getStandardCost() {
        return StandardCost;
    }

    public void setStandardCost(double standardCost) {
        StandardCost = standardCost;
    }

    @Column("SellStartDate")
    public LocalDateTime SellStartDate;

    public LocalDateTime getSellStartDate() {
        return SellStartDate;
    }

    public void setSellStartDate(LocalDateTime sellStartDate) {
        SellStartDate = sellStartDate;
    }
}
