package softuni.exam.models.DTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tickets")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSeedRootXMLDto {

    @XmlElement(name = "ticket")
    private List<TicketSeedXMLDto> ticket;

    public List<TicketSeedXMLDto> getTicket() {
        return ticket;
    }

    public void setTicket(List<TicketSeedXMLDto> ticket) {
        this.ticket = ticket;
    }
}
