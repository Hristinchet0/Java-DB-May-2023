package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ExportHighestPricedTaskDto;
import softuni.exam.models.dto.TaskSeedRootXMLDto;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;
import softuni.exam.models.entity.Task;
import softuni.exam.repository.CarsRepository;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.repository.PartsRepository;
import softuni.exam.repository.TasksRepository;
import softuni.exam.service.TasksService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class TasksServiceImpl implements TasksService {

    private static String TASKS_FILE_PATH = "src/main/resources/files/xml/tasks.xml";

    private final TasksRepository taskRepository;

    private final CarsRepository carRepository;

    private final MechanicsRepository mechanicRepository;

    private final PartsRepository partRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final XmlParser xmlParser;

    @Autowired
    public TasksServiceImpl(TasksRepository tasksRepository, CarsRepository carsRepository, MechanicsRepository mechanicsRepository, PartsRepository partsRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.taskRepository = tasksRepository;
        this.carRepository = carsRepository;
        this.mechanicRepository = mechanicsRepository;
        this.partRepository = partsRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return taskRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(Path.of(TASKS_FILE_PATH));
    }

    @Override
    public String importTasks() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(TASKS_FILE_PATH, TaskSeedRootXMLDto.class)
                .getTask()
                .stream()
                .filter(taskSeedXMLDto -> {
                    boolean isValid = validationUtil.isValid(taskSeedXMLDto);

                    Optional<Mechanic> mechanicByFirstName = this.mechanicRepository
                            .findByFirstName(taskSeedXMLDto.getMechanicFirstName().getFirstName());

//                    Optional<Car> car = this.carRepository
//                            .findById(taskSeedXMLDto.getCar().getId());
//
//                    Optional<Part> part = this.partRepository
//                            .findById(taskSeedXMLDto.getPart().getId());
//
//
//                    if (mechanicByFirstName.isEmpty() || car.isEmpty() || part.isEmpty()) {
//                        isValid = false;
//                    }


                    if (mechanicByFirstName.isEmpty()) {
                        isValid = false;
                    }


                    sb.append(isValid ? String.format("Successfully imported task %.2f",
                                    taskSeedXMLDto.getPrice()) : "Invalid task")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(taskSeedXMLDto -> {
                    Task task = modelMapper.map(taskSeedXMLDto, Task.class);

                    Optional<Mechanic> mechanic = this.mechanicRepository
                            .findByFirstName(taskSeedXMLDto.getMechanicFirstName().getFirstName());

                    Optional<Car> car = this.carRepository
                            .findById(taskSeedXMLDto.getCar().getId());

                    Optional<Part> part = this.partRepository
                            .findById(taskSeedXMLDto.getPart().getId());

                    task.setMechanic(mechanic.get());
                    task.setCar(car.get());
                    task.setPart(part.get());

                    return task;
                })
                .forEach(taskRepository::save);

        return sb.toString().trim();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {

        StringBuilder sb = new StringBuilder();
        List<ExportHighestPricedTaskDto> tasks = this.taskRepository
                .findHighestTaskPrice();

        tasks.forEach(task -> {
            sb.append(String.format("Car %s %s with %dkm%n" +
                            "-Mechanic: %s - task №%d:%n" +
                            " --Engine: %.1f%n" +
                            "---Price: %.2f$%n",
                    task.getCarMake(), task.getCarModel(), task.getKilometers(),
                    task.getFullName(), task.getId(),
                    task.getEngine(),
                    task.getPrice()
            ));
        });

        return sb.toString();
    }
}

