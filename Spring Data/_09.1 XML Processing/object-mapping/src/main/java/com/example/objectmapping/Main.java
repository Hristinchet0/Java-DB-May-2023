package com.example.objectmapping;

import com.example.objectmapping.model.DTO.EmployeeCreateRequestDto;
import com.example.objectmapping.model.DTO.EmployeeCreateResponseDto;
import com.example.objectmapping.model.DTO.ManagerCollectionDto;
import com.example.objectmapping.model.DTO.ManagerDto;
import com.example.objectmapping.serivce.EmployeeService;
import com.example.objectmapping.serivce.util.FormatConverter;
import com.example.objectmapping.serivce.util.FormatConverterFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

@Component
public class Main implements CommandLineRunner {

    private final EmployeeService employeeService;

    private final FormatConverterFactory formatConverterFactory;

    public Main(EmployeeService employeeService, FormatConverterFactory formatConverterFactory) {
        this.employeeService = employeeService;

        this.formatConverterFactory = formatConverterFactory;
    }

    @Override
    public void run(String... args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter format type:");
        String formatType = scanner.nextLine();

        FormatConverter converter = this.formatConverterFactory.create(formatType);

        String line = scanner.nextLine();

        while (!line.equals("end")) {

            String[] tokens = line.split(" ", 2);
            String command = tokens[0];

            switch (command) {
                case "findById":
                    Long id = Long.parseLong(tokens[1]);
                    ManagerDto managerById = this.employeeService.findFirst(id);

                    System.out.println(converter.serialize(managerById));

                    break;
                case "findAll":
                    List<ManagerDto> allManagers = this.employeeService.findAll();

                    System.out.println(converter.serialize(new ManagerCollectionDto(allManagers)));

                    break;

                case "save":
                    String inputXml = tokens[1];

                    //save <?xml version="1.0" encoding="UTF-8" standalone="yes"?><employee first_name="John" last_name="Doe"><salary>4000</salary><address>Blagoevgrad</address></employee>
                    //save {"firstName": "New", "lastName": "Employee", "salary": 3000.50, "address": "Razlog"}

                    EmployeeCreateRequestDto requestXml = converter.deserialize(inputXml, EmployeeCreateRequestDto.class);

                    EmployeeCreateResponseDto responseXml = this.employeeService.save(requestXml);

                    System.out.println(converter.serialize(responseXml));

                    break;
                case "save-from-file":
                    //save-from-file ./src/main/resources/save-from-xml-file.xml
                    //save-from-file ./src/main/resources/first-employee.json

                    EmployeeCreateRequestDto fileRequestXml = converter.deserializeFromFile(
                            tokens[1],
                            EmployeeCreateRequestDto.class
                    );

                    EmployeeCreateResponseDto fileResponseXml = this.employeeService.save(fileRequestXml);

                    System.out.println(converter.serialize(fileResponseXml));

                    break;
                case "find-all-to":
                    //find-all-to ./src/main/resources/findAll

                    List<ManagerDto> managers = this.employeeService.findAll();

                    converter.serialize(
                            new ManagerCollectionDto(managers),
                            tokens[1] + "." + formatType
                    );

                    System.out.println("Written to file " + tokens[1]);


                    break;

            }

            line = scanner.nextLine();
        }
    }
}
