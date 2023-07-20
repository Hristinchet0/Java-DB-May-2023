package com.example.objectmapping.serivce;

import com.example.objectmapping.model.DTO.EmployeeCreateRequestDto;
import com.example.objectmapping.model.DTO.EmployeeCreateResponseDto;
import com.example.objectmapping.model.DTO.EmployeeDto;
import com.example.objectmapping.model.DTO.ManagerDto;

import java.util.List;

public interface EmployeeService {

    EmployeeDto findOne(Long id);

    ManagerDto findFirst(Long id);

    List<ManagerDto> findAll();

    EmployeeCreateResponseDto save(EmployeeCreateRequestDto createRequest);

}
