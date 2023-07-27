package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskSeedRootXMLDto {

    @XmlElement(name = "task")
    private List<TaskSeedXMLDto> task;

    public List<TaskSeedXMLDto> getTask() {
        return task;
    }

    public void setTask(List<TaskSeedXMLDto> task) {
        this.task = task;
    }
}
