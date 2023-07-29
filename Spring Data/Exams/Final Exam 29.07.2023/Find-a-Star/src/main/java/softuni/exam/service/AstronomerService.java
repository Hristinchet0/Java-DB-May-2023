package softuni.exam.service;


import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface AstronomerService {

    boolean areImported();

    String readAstronomersFromFile() throws IOException, JAXBException;

	String importAstronomers() throws IOException, JAXBException;

}
