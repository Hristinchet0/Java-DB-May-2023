package com.example.productsshop.model.DTO.productsDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductViewRootDto {

    @XmlElement(name = "product")
    private List<ProductWithSellerDto> products;

    public List<ProductWithSellerDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductWithSellerDto> products) {
        this.products = products;
    }
}
