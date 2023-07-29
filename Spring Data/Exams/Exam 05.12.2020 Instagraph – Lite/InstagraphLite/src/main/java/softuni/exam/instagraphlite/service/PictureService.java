package softuni.exam.instagraphlite.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface PictureService {
    boolean areImported();
    String readFromFileContent() throws IOException;
    String importPictures() throws IOException;
    String exportPictures();

}
