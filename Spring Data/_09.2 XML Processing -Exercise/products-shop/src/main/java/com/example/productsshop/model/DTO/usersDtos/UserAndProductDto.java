package com.example.productsshop.model.DTO.usersDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserAndProductDto {

    @XmlAttribute(name = "count")
    private Integer usersCount;

    @XmlElement(name = "user")
    private List<UsersSoldProductsWithAgeDto> users;

    public UserAndProductDto(List<UsersSoldProductsWithAgeDto> users) {
        this.usersCount = users.size();
        this.users = users;
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public List<UsersSoldProductsWithAgeDto> getUsers() {
        return users;
    }

    public void setUsers(List<UsersSoldProductsWithAgeDto> users) {
        this.users = users;
    }
}
