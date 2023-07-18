package com.example.productsshop.model.DTO;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class ProductNamePriceAndSellerDto {

    @Expose
    private String name;

    @Expose
    private BigDecimal price;

    @Expose
    private String seller;

    public ProductNamePriceAndSellerDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
