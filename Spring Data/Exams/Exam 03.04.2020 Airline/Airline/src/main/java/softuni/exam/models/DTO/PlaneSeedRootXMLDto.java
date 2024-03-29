package softuni.exam.models.DTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "planes")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneSeedRootXMLDto {

    @XmlElement(name = "plane")
    private List<PlaneSeedXMLDto> plane;

    public List<PlaneSeedXMLDto> getPlane() {
        return plane;
    }

    public void setPlane(List<PlaneSeedXMLDto> plane) {
        this.plane = plane;
    }
}
