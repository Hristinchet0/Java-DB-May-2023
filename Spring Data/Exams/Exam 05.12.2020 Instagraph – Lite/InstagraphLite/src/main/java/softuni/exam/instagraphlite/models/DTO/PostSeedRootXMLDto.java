package softuni.exam.instagraphlite.models.DTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "posts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PostSeedRootXMLDto {

    @XmlElement(name = "post")
    List<PostSeedXMLDto> post;

    public List<PostSeedXMLDto> getPost() {
        return post;
    }

    public void setPost(List<PostSeedXMLDto> post) {
        this.post = post;
    }
}
