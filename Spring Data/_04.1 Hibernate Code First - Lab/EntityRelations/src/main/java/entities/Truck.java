package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "trucks")
public class Truck extends TransportationVehicle{
    private static final String TRUCK_TYPE = "TRUCK";
    private int numOfContainers;


    public Truck() {
    }

    public Truck(String model, String fuelType, int numOfContainers, double loadCapacity) {
        super(TRUCK_TYPE, loadCapacity);

        this.model = model;
        this.fuelType = fuelType;
        this.numOfContainers = numOfContainers;
    }

}
