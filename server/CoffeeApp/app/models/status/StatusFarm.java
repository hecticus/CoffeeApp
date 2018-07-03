package models.status;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.PagedList;
import io.ebean.annotation.JsonIgnore;
import io.ebean.text.PathProperties;
import models.Farm;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("farm")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = StatusFarm.class)
public class StatusFarm extends Status {

    @OneToMany(mappedBy = "statusFarm")
    @JsonIgnore
    @JsonManagedReference
    private List<Farm> farms = new ArrayList<>();

    public List<Farm> getFarms() {
        return farms;
    }

    public void setFarms(List<Farm> farms) {
        this.farms = farms;
    }

    private static Finder<String, StatusFarm> finder = new Finder<>(StatusFarm.class);

    public static StatusFarm findById(String id){
        return finder.byId(id);
    }

    public static PagedList findAll(Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties){
        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(sort != null)
            expressionList.orderBy(sort(sort));

        return expressionList.findPagedList();

    }
}