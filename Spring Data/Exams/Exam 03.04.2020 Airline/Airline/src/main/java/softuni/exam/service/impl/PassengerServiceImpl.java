package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.DTO.ExportPassengerDto;
import softuni.exam.models.DTO.PassengerSeedDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PassengerServiceImpl implements PassengerService {

    private static String PASSENGERS_PATH_FILE = "src/main/resources/files/json/passengers.json";

    private final PassengerRepository passengerRepository;

    private final TownRepository townRepository;

    private final Gson gson;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    public PassengerServiceImpl(PassengerRepository passengerRepository, TownRepository townRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.passengerRepository = passengerRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of(PASSENGERS_PATH_FILE));
    }

    @Override
    public String importPassengers() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readPassengersFileContent(), PassengerSeedDto[].class))
                .filter(passengerSeedDto -> {
                    boolean isValid = validationUtil.isValid(passengerSeedDto);

                    Optional<Passenger> byEmail =
                            this.passengerRepository.findByEmail(passengerSeedDto.getEmail());
                    if (byEmail.isPresent()) {
                        isValid = false;
                    }

                    sb.append(isValid ? String.format("Successfully imported Passenger %s - %s",
                            passengerSeedDto.getLastName(), passengerSeedDto.getEmail()) : "Invalid Passenger")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(passengerSeedDto -> {
                    Passenger passenger = modelMapper.map(passengerSeedDto, Passenger.class);

                    Optional<Town> findByName = townRepository.findByName(passengerSeedDto.getTown());

                    passenger.setTown(findByName.get());

                    return passenger;
                })
                .forEach(passengerRepository::save);

        return sb.toString().trim();
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        StringBuilder sb = new StringBuilder();

        List<ExportPassengerDto> exportPassengerDtos = passengerRepository.passengerInfo();

        exportPassengerDtos
                .forEach(passenger -> sb.append(
                        String.format("Passenger %s  %s\n" +
                        "\tEmail - %s\n" +
                        "\tPhone - %s\n" +
                        "\tNumber of tickets - %d\n",
                                passenger.getFirstName(), passenger.getLastName(),
                                passenger.getEmail(), passenger.getPhoneNumber(),
                                passenger.getNumberOfTickets()))
                        .append(System.lineSeparator()));

        return sb.toString().trim();
    }
}
