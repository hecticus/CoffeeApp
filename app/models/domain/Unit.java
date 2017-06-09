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
    @Column(name = "id_unit")
    private Long idUnit;

    @Constraints.Required
    @Column(nullable = false, name = "name_unit")
    private String NameUnit;

    @Constraints.Required
    @Column(nullable = false, name = "status_unit")
    private Integer statusUnit=1;

    @OneToMany(mappedBy = "unit", cascade= CascadeType.ALL)
    private List<ItemType> itemTypes = new ArrayList<>();

    public Long getIdUnit() {
        return idUnit;
    }

    public void setIdUnit(Long idUnit) {
        this.idUnit = idUnit;
    }

    public String getNameUnit() {
        return NameUnit;
    }

    public void setNameUnit(String nameUnit) {
        NameUnit = nameUnit;
    }

    public Integer getStatusUnit() {
        return statusUnit;
    }

    public void setStatusUnit(Integer statusUnit) {
        this.statusUnit = statusUnit;
    }

    @JsonIgnore
    public List<ItemType> getItemTypes() {
        return itemTypes;
    }

    public void setItemTypes(List<ItemType> itemTypes) {
        this.itemTypes = itemTypes;
    }
}
