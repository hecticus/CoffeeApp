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
 * Created by drocha on 31/05/17.
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

    public static ListPagerCollection findAll(String name, Pager pager, String sort, PathProperties pathProperties){
        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(sort != null)
            expressionList.orderBy(AbstractDaoImpl.Sort(sort));

        if(name!=null)
            expressionList.contains( "name_farm", name);

        if(pager.index == null || pager.size == null)
            return new ListPagerCollection(expressionList.eq("status_delete",0).findList());
        return new ListPagerCollection(
                expressionList.eq("status_delete",0).setFirstRow(pager.index).setMaxRows(pager.size).findList(),
                expressionList.eq("status_delete",0).setFirstRow(pager.index).setMaxRows(pager.size).findCount(),
                pager.index,
                pager.size);
    }

}
