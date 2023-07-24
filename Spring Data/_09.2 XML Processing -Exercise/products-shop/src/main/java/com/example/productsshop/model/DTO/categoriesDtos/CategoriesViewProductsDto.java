package com.example.productsshop.model.DTO.categoriesDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriesViewProductsDto {

    @XmlElement(name = "category")
    private List<CategoryProductsDto> categories;

    public CategoriesViewProductsDto() {
    }

    public List<CategoryProductsDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryProductsDto> categories) {
        this.categories = categories;
    }
}
