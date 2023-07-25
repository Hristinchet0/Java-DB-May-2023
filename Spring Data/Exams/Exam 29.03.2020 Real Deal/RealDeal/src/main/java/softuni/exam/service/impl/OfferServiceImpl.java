package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.entity.DTO.OfferSeedRootXMLDto;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.OfferService;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class OfferServiceImpl implements OfferService {

    private static final String OFFERS_FILE_PATH = "src/main/resources/files/xml/offers.xml";

    public final OfferRepository offerRepository;

    private final CarService carService;

    private final SellerService sellerService;

    public final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final XmlParser xmlParser;


    public OfferServiceImpl(OfferRepository offerRepository, CarService carService, SellerService sellerService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.offerRepository = offerRepository;
        this.carService = carService;
        this.sellerService = sellerService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFERS_FILE_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(OFFERS_FILE_PATH, OfferSeedRootXMLDto.class)
                .getOffers()
                .stream()
                .filter(offerSeedXMLDto -> {
                    boolean isValid = validationUtil.isValid(offerSeedXMLDto);

                    sb.append(isValid ? String.format("Successfully import offer %s - %s",
                            offerSeedXMLDto.getAddedOn(), offerSeedXMLDto.getHasGoldStatus()) : "Invalid offer")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(offerSeedXMLDto -> {
                    Offer offer = modelMapper.map(offerSeedXMLDto, Offer.class);

                    offer.setSeller(sellerService.findById(offerSeedXMLDto.getSeller().getId()));
                    offer.setCar(carService.findById(offerSeedXMLDto.getCar().getId()));

                    return offer;
                })
                .forEach(offerRepository::save);

        return sb.toString().trim();
    }
}
