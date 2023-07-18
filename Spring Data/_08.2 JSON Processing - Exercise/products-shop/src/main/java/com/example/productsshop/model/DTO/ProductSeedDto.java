package com.example.productsshop.model.DTO;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ProductSeedDto {

    @Expose
    @Size(min = 3)
    private String name;

    @Expose
    @Positive
    private BigDecimal price;

    public ProductSeedDto() {
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
}
