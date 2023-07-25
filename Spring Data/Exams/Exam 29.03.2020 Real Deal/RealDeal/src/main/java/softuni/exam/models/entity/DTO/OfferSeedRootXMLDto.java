package softuni.exam.models.entity.DTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedRootXMLDto {

    @XmlElement(name = "offer")
    private List<OfferSeedXMLDto> offers;

    public List<OfferSeedXMLDto> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferSeedXMLDto> offers) {
        this.offers = offers;
    }
}
