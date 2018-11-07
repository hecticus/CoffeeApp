package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.PagedList;
import io.ebean.text.PathProperties;
import models.status.StatusFarm;
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

    @Constraints.Required
    @Constraints.MaxLength(20)
    @Column(nullable = false, length = 20, unique = true)
    private String nameFarm;

    @ManyToOne
//    @JsonBackReference
    @Constraints.Required
    private StatusFarm statusFarm;

    @JsonIgnore
    @OneToMany(mappedBy = "farm", cascade= CascadeType.ALL)
    private List<Lot> lots;

    public static Finder<Long, Farm> finder = new Finder<>(Farm.class);

    public Farm() {
        lots = new ArrayList<>();
    }

    public String getNameFarm() {
        return nameFarm;
    }

    public void setNameFarm(String nameFarm) {
        this.nameFarm = nameFarm;
    }

    public StatusFarm getStatusFarm() {
        return statusFarm;
    }

    public void setStatusFarm(StatusFarm statusFarm) {
        this.statusFarm = statusFarm;
    }

    @JsonIgnore
    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }


    //METODOS DEFINIDOS
    public static Farm findById(Long id){
        return finder.byId(id);
    }

    public static PagedList findAll(Integer index, Integer size, PathProperties pathProperties,
//    public static ListPagerCollection findAll(Integer index, Integer size, PathProperties pathProperties,
                                    String name, String sort, Long status, boolean delete){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.startsWith("nameFarm", name);

        if(sort != null)
            expressionList.orderBy(sort);

        if(status != 0L)
            expressionList.eq("statusFarm.id",status);

        if( delete )
            expressionList.setIncludeSoftDeletes();


        if(index == null || size == null)
            return expressionList.setFirstRow(0).setMaxRows(expressionList.findCount()).findPagedList();
        return expressionList.setFirstRow(index).setMaxRows(size).findPagedList();

//        if(index == null || size == null)
//            return new ListPagerCollection(expressionList.findList());
//
//        return new ListPagerCollection(
//                expressionList.setFirstRow(index).setMaxRows(size).findList(),
//                expressionList.setFirstRow(index).setMaxRows(size).findCount(),
//                index,
//                size);
    }

}
