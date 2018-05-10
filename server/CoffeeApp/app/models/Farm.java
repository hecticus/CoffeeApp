package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.text.PathProperties;
import controllers.utils.ListPagerCollection;
import models.domain.Lot;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 31/05/17.
 */
@Entity
@Table(name="farms")
public class Farm extends AbstractEntity
{
    @Id
    @Column(name = "id_farm")
    private Long idFarm;

    @Constraints.Required
    @Column(nullable = false, name = "name_farm")
    private String NameFarm;

    @Constraints.Required
    @Column(nullable = false, name = "status_farm")
    private Integer statusFarm=1;

    @OneToMany(mappedBy = "farm", cascade= CascadeType.ALL)
    private List<Lot> lots = new ArrayList<>();

    private static Finder<Long, Farm> finder = new Finder<>(Farm.class);

    public static Farm findById(Long id){
        return finder.byId(id);
    }



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


    public static ListPagerCollection findAllSearch(String name, Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties) {
        ExpressionList expressionList = finder.query().where().eq("status_delete",0);

        if(pathProperties != null)
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.icontains("name", name);

        if(sort != null)
            expressionList.orderBy(AbstractEntity.sort(sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(), expressionList.query().findCount(), pageIndex, pageSize);
    }



}
