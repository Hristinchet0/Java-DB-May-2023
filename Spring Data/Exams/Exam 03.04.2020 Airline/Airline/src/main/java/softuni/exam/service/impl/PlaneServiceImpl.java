package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.DTO.PlaneSeedRootXMLDto;
import softuni.exam.models.DTO.PlaneSeedXMLDto;
import softuni.exam.models.entities.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class PlaneServiceImpl implements PlaneService {

    private static String PLANES_PATH_FILE = "src/main/resources/files/xml/planes.xml";

    private final PlaneRepository planeRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final XmlParser xmlParser;

    public PlaneServiceImpl(PlaneRepository planeRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.planeRepository = planeRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return Files.readString(Path.of(PLANES_PATH_FILE));
    }

    @Override
    public String importPlanes() throws JAXBException, IOException {
        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(PLANES_PATH_FILE, PlaneSeedRootXMLDto.class)
                .getPlane()
                .stream()
                .filter(planeSeedXMLDto -> {
                    boolean isValid = validationUtil.isValid(planeSeedXMLDto);

                    Optional<Plane> planeByRegisterNumber = planeRepository.findByRegisterNumber(planeSeedXMLDto.getRegisterNumber());

                    if (planeByRegisterNumber.isPresent()) {
                        isValid = false;
                    }

                    sb.append(isValid ? String.format("Successfully imported Plane %s",
                                    planeSeedXMLDto.getRegisterNumber()) : "Invalid Plane")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(planeSeedXMLDto -> modelMapper.map(planeSeedXMLDto, Plane.class))
                .forEach(planeRepository::save);

        return sb.toString().trim();
    }
}
