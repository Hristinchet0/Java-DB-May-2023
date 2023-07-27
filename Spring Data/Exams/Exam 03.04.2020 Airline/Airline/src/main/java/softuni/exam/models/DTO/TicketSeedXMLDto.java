package softuni.exam.models.DTO;

import softuni.exam.models.entities.Town;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSeedXMLDto {

    @XmlElement(name = "serial-number")
    @Size(min = 2)
    private String serialNumber;

    @XmlElement(name = "price")
    @Positive
    private BigDecimal price;

    @XmlElement(name = "take-off")
    private String takeOff;

    @XmlElement(name = "from-town")
    private Town fromTown;

    @XmlElement(name = "to-town")
    private Town toTown;

    @XmlElement(name = "passenger")
    private PassengerEmailXMLDto passenger;

    @XmlElement(name = "plane")
    private PlaneRegisterNumberXMLDto plane;

    public TicketSeedXMLDto() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTakeOff() {
        return takeOff;
    }

    public void setTakeOff(String takeOff) {
        this.takeOff = takeOff;
    }

    public Town getFromTown() {
        return fromTown;
    }

    public void setFromTown(Town fromTown) {
        this.fromTown = fromTown;
    }

    public Town getToTown() {
        return toTown;
    }

    public void setToTown(Town toTown) {
        this.toTown = toTown;
    }

    public PassengerEmailXMLDto getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerEmailXMLDto passenger) {
        this.passenger = passenger;
    }

    public PlaneRegisterNumberXMLDto getPlane() {
        return plane;
    }

    public void setPlane(PlaneRegisterNumberXMLDto plane) {
        this.plane = plane;
    }
}
