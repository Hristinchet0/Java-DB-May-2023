package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AstronomerSeedRootXMLDto;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class AstronomerServiceImpl implements AstronomerService {

    private static String ASTRONOMER_PATH_FILE = "src/main/resources/files/xml/astronomers.xml";

    private final AstronomerRepository astronomerRepository;

    private final StarRepository starRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final XmlParser xmlParser;

    public AstronomerServiceImpl(AstronomerRepository astronomerRepository, StarRepository starRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.astronomerRepository = astronomerRepository;
        this.starRepository = starRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException{
        return Files.readString(Path.of(ASTRONOMER_PATH_FILE));

    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(ASTRONOMER_PATH_FILE, AstronomerSeedRootXMLDto.class)
                .getAstronomer()
                .stream()
                .filter(astronomerSeedXMLDto -> {
                    boolean isValid = validationUtil.isValid(astronomerSeedXMLDto);

                    Optional<Astronomer> astronomerByFullName = astronomerRepository.findByFirstNameAndLastName(
                            astronomerSeedXMLDto.getFirstName(), astronomerSeedXMLDto.getLastName());

                    if (astronomerByFullName.isPresent()) {
                        isValid = false;
                    }

                    Optional<Star> starById = starRepository.findById(astronomerSeedXMLDto.getObservingStarId());

                    if (starById.isEmpty()) {
                        isValid = false;
                    }

                    sb.append(isValid ? String.format("Successfully imported astronomer %s %s - %.2f",
                                    astronomerSeedXMLDto.getFirstName(), astronomerSeedXMLDto.getLastName(),
                                    astronomerSeedXMLDto.getAverageObservationHours()) : "Invalid astronomer")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(astronomerSeedXMLDto -> {
                    Astronomer astronomer = modelMapper.map(astronomerSeedXMLDto, Astronomer.class);

                    Optional<Star> starById = starRepository.findById(astronomerSeedXMLDto.getObservingStarId());

                    astronomer.setObservingStar(starById.get());

                    return astronomer;
                })
                .forEach(astronomerRepository::save);


        return sb.toString().trim();
    }
}
