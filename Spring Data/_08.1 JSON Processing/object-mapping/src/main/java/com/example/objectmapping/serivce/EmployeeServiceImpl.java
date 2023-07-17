package com.example.objectmapping.serivce;

import com.example.objectmapping.model.DTO.EmployeeCreateRequestDto;
import com.example.objectmapping.model.DTO.EmployeeCreateResponseDto;
import com.example.objectmapping.model.DTO.EmployeeDto;
import com.example.objectmapping.model.DTO.ManagerDto;
import com.example.objectmapping.model.entities.Employee;
import com.example.objectmapping.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeDto findOne(Long id) {

        ModelMapper modelMapper = new ModelMapper();

        Optional<Employee> employee = this.employeeRepository.findById(id);

        return modelMapper.map(employee, EmployeeDto.class);

    }

    @Override
    public ManagerDto findFirst(Long id) {
        Optional<Employee> manager = this.employeeRepository.findById(id);

        return modelMapper.map(manager, ManagerDto.class);
    }

    @Override
    public List<ManagerDto> findAll() {
        return modelMapper.map(this.employeeRepository.findAll(),
                new TypeToken<List<ManagerDto>>() {
                }.getType());

    }

    @Override
    public EmployeeCreateResponseDto save(EmployeeCreateRequestDto createRequest) {
        Employee entity = this.modelMapper.map(
                createRequest,
                Employee.class
        );

        entity = this.employeeRepository.save(entity);

        EmployeeCreateResponseDto response = this.modelMapper.map(
                entity,
                EmployeeCreateResponseDto.class
        );

        return response;
    }

}
