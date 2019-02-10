package models.status;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.PagedList;
import io.ebean.text.PathProperties;
import models.Provider;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("provider")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = StatusProvider.class)
public class StatusProvider extends Status {

    @OneToMany(mappedBy = "statusProvider")
    @JsonIgnore
//    @JsonManagedReference
    private List<Provider> providers = new ArrayList<>();

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    private static Finder<Long, StatusProvider> finder = new Finder<>(StatusProvider.class);

    public static StatusProvider findById(Long id){
        return finder.byId(id);
    }

    public static PagedList findAll(Integer index, Integer size, String sort, PathProperties pathProperties){
        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(sort != null)
            expressionList.orderBy(sort(sort));

        if(index == null || size == null)
            return new ListPagerCollection(expressionList.findList());

        return new ListPagerCollection(
                expressionList.setFirstRow(index).setMaxRows(size).findList(),
                expressionList.setFirstRow(index).setMaxRows(size).findCount(),
                index,
                size);

    }
}
