package softuni.exam.models.entity.DTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sellers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerSeedRootXMLDto {

    @XmlElement(name = "seller")
    private List<SellerSeedXMLDto> sellers;

    public List<SellerSeedXMLDto> getSellers() {
        return sellers;
    }

    public void setSellers(List<SellerSeedXMLDto> sellers) {
        this.sellers = sellers;
    }
}
