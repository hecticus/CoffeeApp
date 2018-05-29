package models;

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

    public static int getExist(String name_unit){
        if(finder.query().where().eq("name_unit",name_unit).eq("status_delete",0).findUnique()!=null) return 0;
        else{
            if(finder.query().where().eq("name_unit",name_unit).eq("status_delete",1).findUnique()!=null)  return 1;
            else return 2;
        }
    }

    public static boolean existName(String name_unit){
        if(finder.query().where().eq("name_unit",name_unit).findUnique()!= null) return true;
        return false;
    }

    public static boolean existId(Long id) {
        if(InvoiceDetail.findById(id) != null ) return true;
        return false;
    }

    public static ListPagerCollection findAll(String name, Integer index, Integer size, String sort, PathProperties pathProperties, Integer status){
        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(status != null)
            expressionList.eq("status_unit",status);

        if(name != null)
            expressionList.icontains("name_unit", name);

        if(sort != null)
            expressionList.orderBy(sort(sort));

        if(index == null || size == null)
            return new ListPagerCollection(expressionList.eq("status_delete",0).findList());
        return new ListPagerCollection(
                expressionList.eq("status_delete",0).setFirstRow(index).setMaxRows(size).findList(),
                expressionList.eq("status_delete",0).setFirstRow(index).setMaxRows(size).findCount(),
                index,
                size);
    }

}
