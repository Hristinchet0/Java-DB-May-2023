package com.example.objectmapping.model.DTO;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class EmployeeDto extends BasicEmployeeDto{

    @Expose
//    @SerializedName("income")
    private BigDecimal salary;


    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
