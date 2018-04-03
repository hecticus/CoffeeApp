package models.domain;


import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Car extends Model{

    @Id
    private Integer id;
    private String model;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
