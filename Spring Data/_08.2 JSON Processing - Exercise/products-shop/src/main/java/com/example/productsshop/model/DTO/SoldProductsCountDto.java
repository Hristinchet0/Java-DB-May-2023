package com.example.productsshop.model.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SoldProductsCountDto {

    @Expose
    private Integer count;

    @Expose
    @SerializedName("products")
    private List<ProductNameAndPriceDto> soldProducts;

    public SoldProductsCountDto() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProductNameAndPriceDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductNameAndPriceDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
