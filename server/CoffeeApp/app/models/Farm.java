package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import controllers.utils.ListPagerCollection;
import io.ebean.ExpressionList;
import io.ebean.Finder;
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

    public StatusFarm getStatusComun() {
        return statusFarm;
    }

    public void setStatusComun(StatusFarm statusComun) {
        this.statusFarm = statusComun;
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

    public static ListPagerCollection findAll(Integer index, Integer size, PathProperties pathProperties,
                                    String name, String sort, Long status, boolean deleted){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.startsWith("nameFarm", name);

        if(sort != null)
            expressionList.orderBy(sort);

        if(status != 0l)
            expressionList.eq("statusFarm.id",status);

        if( deleted )
            expressionList.setIncludeSoftDeletes();

        if(index == null || size == null)
            return new ListPagerCollection(expressionList.findList());

        return new ListPagerCollection(
                expressionList.setFirstRow(index).setMaxRows(size).findList(),
                expressionList.setFirstRow(index).setMaxRows(size).findCount(),
                index,
                size);
    }

}
