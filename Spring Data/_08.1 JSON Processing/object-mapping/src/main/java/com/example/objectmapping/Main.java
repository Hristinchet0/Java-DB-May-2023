package com.example.objectmapping;

import com.example.objectmapping.model.DTO.EmployeeCreateRequestDto;
import com.example.objectmapping.model.DTO.EmployeeCreateResponseDto;
import com.example.objectmapping.model.DTO.ManagerDto;
import com.example.objectmapping.serivce.EmployeeService;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

@Component
public class Main implements CommandLineRunner {

    private final EmployeeService employeeService;
    private final Gson gson;

    public Main(EmployeeService employeeService, Gson gson) {
        this.employeeService = employeeService;
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {
//        EmployeeDto dto = this.employeeService.findOne(1L);
//
//        System.out.println(dto.getFirstName() + " " + dto.getLastName() + " " + dto.getSalary());
//
//        ManagerDto managerDto = this.employeeService.findFirst(1L);
//        System.out.println(dto.getFirstName() + " " + dto.getLastName());
//        managerDto.getSubordinates()
//                .forEach(employee -> {
//                    System.out.println("\t" + employee.getFirstName() + " " + employee.getLastName() + " " + employee.getSalary());
//                });
//
//        List<ManagerDto> managers = this.employeeService.findAll();
//        managers.forEach(manager -> {
//            if (manager.getSubordinates().isEmpty()) {
//                return;
//            }
//            System.out.println(manager.getFirstName() + " " + manager.getLastName() + "(" + manager.getSubordinates().size() + ")");
//            manager.getSubordinates()
//                    .forEach(employee -> {
//                        System.out.println("\t" + employee.getFirstName() + " " + employee.getLastName() + " " + employee.getSalary());
//                    });
//        });


        //JSON processing

        Scanner scanner = new Scanner(System.in);

        String line = scanner.nextLine();

        while (!line.equals("end")) {

            String[] tokens = line.split(" ", 2);
            String command = tokens[0];

            switch (command) {
                case "findById":
                    Long id = Long.parseLong(tokens[1]);
                    ManagerDto managerById = this.employeeService.findFirst(id);

                    System.out.println(this.gson.toJson(managerById));

                    break;
                case "findAll":
                    List<ManagerDto> allManagers = this.employeeService.findAll();

                    System.out.println(this.gson.toJson(allManagers));

                    break;
                case "save-from-json-string":

                    //save-from-json-string {"firstName": "New", "lastName": "Employee", "salary": 3000.50, "address": "Razlog"}

                    String json = tokens[1];

                    EmployeeCreateRequestDto request = this.gson.fromJson(
                            json, EmployeeCreateRequestDto.class);

                    EmployeeCreateResponseDto response = this.employeeService.save(request);

                    System.out.println(this.gson.toJson(response));

                    break;
                case "save-from-file":
                    //save-from-file ./src/main/resources/first-employee.json
                    EmployeeCreateRequestDto fileRequest = this.gson.fromJson(
                            new FileReader(tokens[1]), EmployeeCreateRequestDto.class);

                    EmployeeCreateResponseDto fileResponse = this.employeeService.save(fileRequest);

                    System.out.println(this.gson.toJson(fileResponse));

                    break;
                case "find-all-to-file":
                    //find-all-to-file ./src/main/resources/findAll.json

                    try (FileWriter fileWriter = new FileWriter(tokens[1])) {
                        List<ManagerDto> managers =  this.employeeService.findAll();
                        this.gson.toJson(
                                managers,
                                fileWriter);

                        System.out.println("Written to file " + tokens[1]);
                    }


                    break;

            }


            line = scanner.nextLine();
        }


    }
}
