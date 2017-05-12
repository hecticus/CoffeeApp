package models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 25/04/17.
 */
@Entity
@Table(name="units")
public class Unit extends AbstractEntity
{

    @Id
    private Long idUnit;

    @Constraints.Required
    @Column(nullable = false)
    private String NameUnit;

    @Constraints.Required
    @Column(nullable = false)
    private Integer statusUnit=1;

    @OneToMany(mappedBy = "unit", cascade= CascadeType.ALL)
    private List<ItemType> itemTypes = new ArrayList<>();




    public String getName() {
        return NameUnit;
    }

    public void setFullName(String fullName) {
        this.NameUnit = fullName;
    }

    public void setName(String name) {
        NameUnit = name;
    }

    public Integer getStatus() {
        return statusUnit;
    }

    public void setStatus(Integer status) {
        this.statusUnit = status;
    }

    @JsonIgnore
    public List<ItemType> getItemTypes() {
        return itemTypes;
    }

    public void setItemTypes(List<ItemType> itemTypes) {
        this.itemTypes = itemTypes;
    }

    public Long getId() {
        return idUnit;
    }

    public void setId(Long idUnit) {
        this.idUnit = idUnit;
    }

}
