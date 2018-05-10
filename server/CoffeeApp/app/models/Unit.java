package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.Finder;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sm21 on 10/05/18.
 */
@Entity
@Table(name="units")
public class Unit extends AbstractEntity{

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

    private static Finder<Long, Unit> finder = new Finder<>(Unit.class);


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

    public static Unit findById(Long id){
        return finder.byId(id);
    }

    public int getExist(String name_unit){
        if(finder.query().where().eq("name_unit",name_unit).eq("status_delete",0).findUnique()!=null) return 0;
        else{
            if(finder.query().where().eq("name_unit",name_unit).eq("status_delete",1).findUnique()!=null)  return 1;
            else return 2;
        }
    }




}
