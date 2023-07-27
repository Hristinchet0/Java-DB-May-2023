package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.MechanicSeedDto;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.service.MechanicsService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class MechanicsServiceImpl implements MechanicsService {

    private static String MECHANICS_FILE_PATH = "src/main/resources/files/json/mechanics.json";

    private final MechanicsRepository mechanicsRepository;

    private final Gson gson;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    public MechanicsServiceImpl(MechanicsRepository mechanicsRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.mechanicsRepository = mechanicsRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return mechanicsRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(Path.of(MECHANICS_FILE_PATH));
    }

    @Override
    public String importMechanics() throws IOException {

        StringBuilder sb = new StringBuilder();

        Arrays.stream(
                        gson.fromJson(readMechanicsFromFile(), MechanicSeedDto[].class))
                .filter(mechanicSeedDto -> {
                    boolean isValid = validationUtil.isValid(mechanicSeedDto);

                    Optional<Mechanic> mechanicByEmail = this.mechanicsRepository
                            .findByEmail(mechanicSeedDto.getEmail());

                    if (mechanicByEmail.isPresent()) {
                        isValid = false;
                    }

                    sb.append(isValid ? String.format("Successfully imported mechanic %s %s",
                                    mechanicSeedDto.getFirstName(), mechanicSeedDto.getLastName()) : "Invalid mechanic")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(mechanicSeedDto -> modelMapper.map(mechanicSeedDto, Mechanic.class))
                .forEach(mechanicsRepository::save);

        return sb.toString().trim();
    }

    @Override
    public Optional<Mechanic> findByFirstName(String firstName) {
        return mechanicsRepository.findByFirstName(firstName);
    }

    @Override
    public Mechanic findMechanicByFirstName(String firstName) {
        return mechanicsRepository.findByFirstName(firstName).orElse(null);
    }

}
