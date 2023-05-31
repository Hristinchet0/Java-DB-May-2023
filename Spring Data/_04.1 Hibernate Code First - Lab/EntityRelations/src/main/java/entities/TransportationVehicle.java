package entities;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class TransportationVehicle  extends Vehicle {
    private double loadCapacity;

    public TransportationVehicle() {
    }

    public TransportationVehicle(String type, double loadCapacity) {
        super(type);
        this.loadCapacity = loadCapacity;
    }

    public double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(int loadCapacity) {
        this.loadCapacity = loadCapacity;
    }
}
