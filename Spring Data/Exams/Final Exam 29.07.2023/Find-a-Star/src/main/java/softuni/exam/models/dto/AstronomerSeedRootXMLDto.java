package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "astronomers")
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerSeedRootXMLDto {

    @XmlElement(name = "astronomer")
    private List<AstronomerSeedXMLDto> astronomer;

    public List<AstronomerSeedXMLDto> getAstronomer() {
        return astronomer;
    }

    public void setAstronomer(List<AstronomerSeedXMLDto> astronomer) {
        this.astronomer = astronomer;
    }
}
