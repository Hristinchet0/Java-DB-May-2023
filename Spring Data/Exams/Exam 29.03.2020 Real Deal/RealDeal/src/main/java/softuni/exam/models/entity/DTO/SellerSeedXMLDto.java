package softuni.exam.models.entity.DTO;

import softuni.exam.models.entity.enums.Rating;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "seller")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerSeedXMLDto {

    @XmlElement(name = "first-name")
    @Size(min = 2, max = 19)
    private String firstName;

    @XmlElement(name = "last-name")
    @Size(min = 2, max = 19)
    private String lastName;

    @XmlElement(name = "email")
    @Email
    private String email;

    @XmlElement(name = "rating")
    @NotNull
    private Rating rating;

    @XmlElement(name = "town")
    @NotBlank
    private String town;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Rating getRating() {
        return rating;
    }

    public String getTown() {
        return town;
    }
}
