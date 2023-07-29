package softuni.exam.instagraphlite.models.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PostSeedXMLDto {

    @XmlElement(name = "caption")
    @Size(min = 21)
    @NotNull
    private String caption;

    @XmlElement(name = "user")
    @NotNull
    private UserByUsernameDto user;

    @XmlElement(name = "picture")
    @NotNull
    private PictureByPathDto picture;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public UserByUsernameDto getUser() {
        return user;
    }

    public void setUser(UserByUsernameDto user) {
        this.user = user;
    }

    public PictureByPathDto getPicture() {
        return picture;
    }

    public void setPicture(PictureByPathDto picture) {
        this.picture = picture;
    }
}
