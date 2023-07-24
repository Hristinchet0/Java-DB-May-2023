package com.example.productsshop.model.DTO.usersDtos;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersSoldProductsWithAgeDto {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlAttribute
    private int age;

    @XmlElement(name = "sold-products")
    SoldProductsCountDto products;

    public UsersSoldProductsWithAgeDto() {
        products = new SoldProductsCountDto();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public SoldProductsCountDto getProducts() {
        return products;
    }

    public void setProducts(SoldProductsCountDto products) {
        this.products = products;
    }
}