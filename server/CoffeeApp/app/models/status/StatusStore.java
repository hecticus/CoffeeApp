package models.status;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.PagedList;
import io.ebean.annotation.JsonIgnore;
import io.ebean.text.PathProperties;
import models.Store;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("store")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = StatusFarm.class)
public class StatusStore extends Status {

    @OneToMany(mappedBy = "statusStore")
    @JsonIgnore
    @JsonManagedReference
    private List<Store> stores = new ArrayList<>();

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    private static Finder<String, StatusStore> finder = new Finder<>(StatusStore.class);

    public static StatusStore findById(String id){
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
