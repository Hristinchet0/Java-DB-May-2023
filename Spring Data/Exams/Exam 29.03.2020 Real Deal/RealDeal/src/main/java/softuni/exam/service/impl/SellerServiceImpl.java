package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.entity.DTO.SellerSeedRootXMLDto;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SellerServiceImpl implements SellerService {

    private static final String SELLERS_PATH_FILE = "src/main/resources/files/xml/sellers.xml";

    private final SellerRepository sellerRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final XmlParser xmlParser;

    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(Path.of(SELLERS_PATH_FILE));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();

        xmlParser
                .fromFile(SELLERS_PATH_FILE, SellerSeedRootXMLDto.class)
                .getSellers()
                .stream()
                .filter(sellerSeedXMLDto -> {
                    boolean isValid = validationUtil.isValid(sellerSeedXMLDto);

                    sb.append(isValid ? String.format("Successfully import seller %s - %s",
                                    sellerSeedXMLDto.getLastName(), sellerSeedXMLDto.getEmail()) : "Invalid seller")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(sellerSeedXMLDto -> modelMapper.map(sellerSeedXMLDto, Seller.class))
                .forEach(sellerRepository::save);

        return sb.toString().trim();
    }

    @Override
    public Seller findById(Long sellerId) {
        return sellerRepository.findById(sellerId).orElse(null);
    }
}
