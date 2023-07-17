package com.example.objectmapping.model.DTO;

import com.google.gson.annotations.Expose;

public class EmployeeCreateResponseDto extends EmployeeCreateRequestDto{

    @Expose
    private Long id;

    public EmployeeCreateResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
