package models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import controllers.utils.ListPagerCollection;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import com.avaje.ebean.text.PathProperties;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 31/05/17.
 */
@Entity
@Table(name="farms")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id", scope = Farm.class)
//@JsonIgnoreProperties({"proposals", "machines", "partRequests"}) //va os metodos que quiero que me ignore el metodo
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
    private Integer statusFarm=1;

    @OneToMany(mappedBy = "farm", cascade= CascadeType.ALL)
    @JsonIgnore
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

    public static ListPagerCollection findAll(Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties){
        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(sort != null)
            expressionList.orderBy(AbstractDaoImpl.Sort(sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.eq("status_delete",0).findList());
        return new ListPagerCollection(
                expressionList.eq("status_delete",0).setFirstRow(pageIndex).setMaxRows(pageSize).findList(),
                expressionList.eq("status_delete",0).setFirstRow(pageIndex).setMaxRows(pageSize).findCount(),
                pageIndex,
                pageSize);
    }

    public static ListPagerCollection findAll(Integer pageIndex, Integer pageSize, String sort){
        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(finder.query().where().eq("status_delete",0).orderBy(AbstractEntity.sort(sort)).findList());
        return new ListPagerCollection(
                finder.query().where().eq("status_delete",0).orderBy(AbstractEntity.sort(sort)).setFirstRow(pageIndex).setMaxRows(pageSize).findList(),
                finder.query().findCount(),
                pageIndex,
                pageSize);
    }


}
