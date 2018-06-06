package models;

import com.avaje.ebean.validation.Range;
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
    @Column(name = "id_farm", nullable = false)
    private Long idFarm;

    @Constraints.Required
    @Constraints.MaxLength(50)
    @Column(nullable = false, name = "name_farm", length = 50, unique = true)
    private String NameFarm;

    @Range(min = 0, max = 1)
    @Column( name = "status_farm", columnDefinition = "integer default 1")
    private Integer statusFarm;

    @JsonIgnore
    @OneToMany(mappedBy = "farm", cascade= CascadeType.ALL)
    private List<Lot> lots;

    private static Finder<Long, Farm> finder = new Finder<>(Farm.class);

    public Farm() {
        statusFarm = 1;
        lots = new ArrayList<>();
    }

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


    public static ListPagerCollection findAll(String name, Integer index, Integer size, String
                                                sort,PathProperties pathProperties,  Integer status){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(status != null)
            expressionList.eq("statusFarm",status);

        if(name != null)
            expressionList.icontains("NameFarm", name);

        if(sort != null)
            expressionList.orderBy(sort(sort));

//        if( status != null ){
//            expressionList.eq("statusDelete", status);
//        }

        if(index == null || size == null)
            return new ListPagerCollection(expressionList.findList());


        return new ListPagerCollection(
                expressionList.setFirstRow(index).setMaxRows(size).findList(),
                expressionList.setFirstRow(index).setMaxRows(size).findCount(),
                index,
                size);
    }

}
