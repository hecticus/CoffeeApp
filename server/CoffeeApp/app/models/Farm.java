package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import controllers.parsers.queryStringBindable.Pager;
import controllers.utils.ListPagerCollection;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.text.PathProperties;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sm21 on 23/05/18.
 */
@Entity
@Table(name="farms")
public class Farm extends AbstractEntity{


    @Id
    @Constraints.Required
    @Constraints.MaxLength(100)
    @Column(name = "id_farm", length = 100, nullable = false)
    private Long idFarm;

    @Constraints.Required
    @Column(nullable = false, name = "name_farm", length = 100)
    private String NameFarm;

    @Constraints.Required
    @Column(nullable = false, name = "status_farm", length = 100)
    private Integer statusFarm = 1;

    @OneToMany(mappedBy = "farm", cascade= CascadeType.ALL)
    @JsonIgnore
    private List<Lot> lots = new ArrayList<>();

    private static Finder<Long, Farm> finder = new Finder<>(Farm.class);

    // GETTER AND SETTER
    public Long getIdFarm() {
        return idFarm;
    }

    public void setIdFarm(Long idFarm) {
        this.idFarm = idFarm;
    }

    @JsonIgnore
    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    public Integer getStatusFarm() {
        return statusFarm;
    }

    public void setStatusFarm(Integer statusFarm) {
        this.statusFarm = statusFarm;
    }

    public String getNameFarm() {
        return NameFarm;
    }

    public void setNameFarm(String nameFarm) {
        NameFarm = nameFarm;
    }


    //METODOS DEFINIDOS
    public static Farm findById(Long id){
        return finder.byId(id);
    }

    public static boolean existId(Long id) {
        if(InvoiceDetail.findById(id) != null ) return true;
        return false;
    }

    public static boolean existName(String name_itemtype){
        if(finder.query().where().eq("name_itemtype",name_itemtype ).findUnique() != null ) return true;
        return false;
    }


    public static ListPagerCollection findAll(String name, Integer index, Integer size, String sort,PathProperties pathProperties,  Integer status){
        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(status != null)
            expressionList.eq("status_farm",status);

        if(name != null)
            expressionList.icontains("name_farm", name);

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
