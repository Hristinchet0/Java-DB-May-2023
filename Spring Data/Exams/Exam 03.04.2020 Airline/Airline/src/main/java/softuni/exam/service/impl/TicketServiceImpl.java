package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.DTO.TicketSeedRootXMLDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Plane;
import softuni.exam.models.entities.Ticket;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TicketService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private static String TICKETS_PATH_FILE = "src/main/resources/files/xml/tickets.xml";

    private final TicketRepository ticketRepository;

    private final TownRepository townRepository;

    private final PassengerRepository passengerRepository;

    private final PlaneRepository planeRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final XmlParser xmlParser;

    public TicketServiceImpl(TicketRepository ticketRepository, TownRepository townRepository, PassengerRepository passengerRepository, PlaneRepository planeRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.ticketRepository = ticketRepository;
        this.townRepository = townRepository;
        this.passengerRepository = passengerRepository;
        this.planeRepository = planeRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of(TICKETS_PATH_FILE));
    }

    @Override
    public String importTickets() throws JAXBException {

        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(TICKETS_PATH_FILE, TicketSeedRootXMLDto.class)
                .getTicket()
                .stream()
                .filter(ticketSeedXMLDto -> {
                    boolean isValid = validationUtil.isValid(ticketSeedXMLDto);

                    Optional<Ticket> ticketBySerialNumber = ticketRepository.findBySerialNumber(ticketSeedXMLDto.getSerialNumber());

                    if(ticketBySerialNumber.isPresent()) {
                        isValid = false;
                    }

                    sb.append(isValid ? String.format("Successfully imported Ticket %s - %s",
                            ticketSeedXMLDto.getFromTown().getName(), ticketSeedXMLDto.getToTown().getName()) : "Invalid Ticket")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(ticketSeedXMLDto -> {
                    Ticket ticket = modelMapper.map(ticketSeedXMLDto, Ticket.class);

                    Optional<Town> fromTown = townRepository.findByName(ticketSeedXMLDto.getFromTown().getName());

                    Optional<Town> toTown = townRepository.findByName(ticketSeedXMLDto.getToTown().getName());

                    Optional<Passenger> passengerEmail = passengerRepository.findByEmail(ticketSeedXMLDto.getPassenger().getEmail());

                    Optional<Plane> planeRegisterNumber = planeRepository.findByRegisterNumber(ticketSeedXMLDto.getPlane().getRegisterNumber());

                    ticket.setFromTown(fromTown.get());
                    ticket.setToTown(toTown.get());
                    ticket.setPassenger(passengerEmail.get());
                    ticket.setPlane(planeRegisterNumber.get());

                    return ticket;
                })
                .forEach(ticketRepository::save);

        return sb.toString().trim();
    }
}
