package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cars")
public class Car extends PassengerVehicle{

    private static final String CAR_TYPE = "CAR";

    public Car() {
    }

    public Car(String type, int seats) {
        super(type, seats);
    }

}
