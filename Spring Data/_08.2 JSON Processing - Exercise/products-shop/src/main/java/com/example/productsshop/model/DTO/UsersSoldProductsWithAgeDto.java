package com.example.productsshop.model.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersSoldProductsWithAgeDto {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private int age;

    @Expose
    @SerializedName("sold-products")
    private SoldProductsCountDto products;

    public UsersSoldProductsWithAgeDto() {
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
