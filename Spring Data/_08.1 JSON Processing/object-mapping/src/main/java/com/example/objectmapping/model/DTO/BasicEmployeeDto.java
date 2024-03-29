package com.example.objectmapping.model.DTO;

import com.google.gson.annotations.Expose;

public abstract class BasicEmployeeDto {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

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
}
