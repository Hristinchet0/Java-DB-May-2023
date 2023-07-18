package com.example.productsshop.model.DTO;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class CategoriesByProductsSummaryDto {

    @Expose
    private String category;

    @Expose
    private Long productsCount;

    @Expose
    private Double averagePrice;

    @Expose
    private BigDecimal totalRevenue;

    public CategoriesByProductsSummaryDto() {
    }

    public CategoriesByProductsSummaryDto(String category, Long productsCount, Double averagePrice, BigDecimal totalRevenue) {
        this.category = category;
        this.productsCount = productsCount;
        this.averagePrice = averagePrice;
        this.totalRevenue = totalRevenue;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(Long productsCount) {
        this.productsCount = productsCount;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
