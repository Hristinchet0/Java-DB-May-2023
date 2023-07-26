package softuni.exam.service;


import softuni.exam.models.entity.Mechanic;

import java.io.IOException;
import java.util.Optional;

public interface MechanicsService {

    boolean areImported();

    String readMechanicsFromFile() throws IOException;

    String importMechanics() throws IOException;

    Optional<Mechanic> findByFirstName(String firstName);

    Mechanic findMechanicByFirstName(String firstName);
}
