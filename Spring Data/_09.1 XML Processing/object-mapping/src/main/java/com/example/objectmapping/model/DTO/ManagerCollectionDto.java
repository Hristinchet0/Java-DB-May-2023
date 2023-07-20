package com.example.objectmapping.model.DTO;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "managers")
@XmlAccessorType(XmlAccessType.FIELD)
public class ManagerCollectionDto {

    @XmlElement(name = "manager")
    @Expose
    private List<ManagerDto> managers;

    public ManagerCollectionDto() {
    }

    public ManagerCollectionDto(List<ManagerDto> managers) {
        this.managers = managers;
    }

    public List<ManagerDto> getManagers() {
        return managers;
    }

    public void setManagers(List<ManagerDto> managers) {
        this.managers = managers;
    }
}
