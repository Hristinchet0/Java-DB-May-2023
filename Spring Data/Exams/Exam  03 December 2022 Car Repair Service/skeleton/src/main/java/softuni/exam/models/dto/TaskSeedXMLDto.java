package softuni.exam.models.dto;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskSeedXMLDto {

    @XmlElement(name = "date")
    private String date;

    @XmlElement(name = "price")
    @Positive
    private BigDecimal price;

    @XmlElement(name = "car")
    private CarIdXMLDto car;

    @XmlElement(name = "mechanic")
    private MechanicFirstNameXMLDto mechanicFirstName;

    @XmlElement(name = "part")
    private PartIdXmlDto part;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CarIdXMLDto getCar() {
        return car;
    }

    public void setCar(CarIdXMLDto car) {
        this.car = car;
    }

    public MechanicFirstNameXMLDto getMechanicFirstName() {
        return mechanicFirstName;
    }

    public void setMechanicFirstName(MechanicFirstNameXMLDto mechanicFirstName) {
        this.mechanicFirstName = mechanicFirstName;
    }

    public PartIdXmlDto getPart() {
        return part;
    }

    public void setPart(PartIdXmlDto part) {
        this.part = part;
    }
}
