package com.example.objectmapping;

import com.example.objectmapping.model.DTO.EmployeeDto;
import com.example.objectmapping.model.DTO.ManagerDto;
import com.example.objectmapping.serivce.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Main implements CommandLineRunner {

    private final EmployeeService employeeService;

    public Main(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
        EmployeeDto dto = this.employeeService.findOne(1L);

        System.out.println(dto.getFirstName() + " " + dto.getLastName() + " " + dto.getSalary());

        ManagerDto managerDto = this.employeeService.findFirst(1L);
        System.out.println(dto.getFirstName() + " " + dto.getLastName());
        managerDto.getSubordinates()
                .forEach(employee -> {
                    System.out.println("\t" + employee.getFirstName() + " " + employee.getLastName() + " " + employee.getSalary());
                });

       List<ManagerDto> managers = this.employeeService.findAll();
        managers.forEach(manager -> {
            if (manager.getSubordinates().isEmpty()) {
                return;
            }
            System.out.println(manager.getFirstName() + " " + manager.getLastName() + "(" + manager.getSubordinates().size() + ")");
            manager.getSubordinates()
                    .forEach(employee -> {
                        System.out.println("\t" + employee.getFirstName() + " " + employee.getLastName() + " " + employee.getSalary());
                    });
        });


    }
}
