package models;

import com.avaje.ebean.validation.Range;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.PagedList;
import io.ebean.text.PathProperties;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sm21 on 10/05/18.
 */
@Entity
@Table(name="units")
public class Unit extends AbstractEntity {

    @Constraints.Required
    @Column(nullable = false, unique = true)
    private String nameUnit;

    @OneToMany(mappedBy = "unit", cascade= CascadeType.ALL)
    private List<ItemType> itemTypes;

    private static Finder<Long, Unit> finder = new Finder<>(Unit.class);

    public Unit() {
        itemTypes = new ArrayList<>();
    }

    //Setter and Getter
    public String getNameUnit() {
        return nameUnit;
    }

    public void setNameUnit(String nameUnit) {
        this.nameUnit = nameUnit;
    }

    @JsonIgnore
    public List<ItemType> getItemTypes() {
        return itemTypes;
    }

    public void setItemTypes(List<ItemType> itemTypes) {
        this.itemTypes = itemTypes;
    }

    //Metodos Definidos
    public static Unit findById(Long id){
        return finder.byId(id);
    }

    public static PagedList findAll(Integer index, Integer size, PathProperties pathProperties,
                                    String sort, String name, boolean delete){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.startsWith("nameUnit", name);

        if(delete)
            expressionList.setIncludeSoftDeletes();

        if(sort != null)
            expressionList.orderBy(sort( sort));

        if(index == null || size == null){
            return expressionList
                    .setFirstRow(0)
                    .setMaxRows(expressionList.findCount()).findPagedList();
        }

        return expressionList.setFirstRow(index).setMaxRows(size).findPagedList();
    }

}


