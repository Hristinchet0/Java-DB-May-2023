package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.DTO.ExportPictureDto;
import softuni.exam.instagraphlite.models.DTO.PictureSeedDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PictureServiceImpl implements PictureService {

    private static String PICTURE_FILE_PATH = "src/main/resources/files/pictures.json";

    public final PictureRepository pictureRepository;

    private final ModelMapper modelMapper;

    private final Gson gson;

    private final ValidationUtil validationUtil;

    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(PICTURE_FILE_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson
                .fromJson(readFromFileContent(), PictureSeedDto[].class))
                .filter(pictureSeedDto -> {
                    boolean isValid = validationUtil.isValid(pictureSeedDto);

                    Optional<Picture> picture = pictureRepository.findByPath(pictureSeedDto.getPath());

                    if(picture.isPresent()) {
                        isValid = false;
                    }

                    sb.append(isValid ? String.format("Successfully imported Picture, with size %s",
                            pictureSeedDto.getSize()) : "Invalid Picture")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(pictureSeedDto -> modelMapper.map(pictureSeedDto, Picture.class))
                .forEach(pictureRepository::save);

        return sb.toString().trim();
    }

    @Override
    public String exportPictures() {

        StringBuilder sb = new StringBuilder();

        List<ExportPictureDto> pictures = pictureRepository
                .findAllBySizeGreaterThanOrderBySizeAsc(30000.0);

        pictures.forEach(picture -> {
            sb.append(String.format("%.2f – %s",
                    picture.getSize(), picture.getPath()))
                    .append(System.lineSeparator());
        });

        return sb.toString().trim();
    }
}
