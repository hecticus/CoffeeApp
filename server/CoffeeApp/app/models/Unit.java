package models;

import com.avaje.ebean.validation.Range;
import com.fasterxml.jackson.annotation.JsonIgnore;
import controllers.utils.ListPagerCollection;
import io.ebean.ExpressionList;
import io.ebean.Finder;
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

    public static ListPagerCollection findAll(Integer index, Integer size, PathProperties pathProperties,
                                              String sort, String name, Integer status, boolean deleted){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.startsWith("nameUnit", name);

        if(deleted)
            expressionList.setIncludeSoftDeletes();

        if(sort != null)
            expressionList.orderBy(sort( sort));

        if(status != 0L)
            expressionList.eq("statusUnit", status );

        if(index == null || size == null)
            return new ListPagerCollection(expressionList.findList());


        return new ListPagerCollection(
                expressionList.setFirstRow(index).setMaxRows(size).findList(),
                expressionList.setFirstRow(index).setMaxRows(size).findCount(),
                index,
                size);
    }

}
