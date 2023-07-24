package com.example.productsshop.model.DTO.usersDtos;

import com.example.productsshop.model.DTO.productsDtos.ProductSeedDto;

import javax.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductsCountDto {
    @XmlAttribute()
    private Integer count;

    @XmlElement(name = "product" )
    private List<ProductSeedDto> soldProducts;

    public SoldProductsCountDto() {

    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProductSeedDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductSeedDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}