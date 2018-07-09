package models.status;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.PagedList;
import io.ebean.text.PathProperties;
import models.Lot;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("lot")
public class StatusLot extends Status {

    @OneToMany(mappedBy = "statusLot")
    @JsonIgnore
//    @JsonManagedReference
    private List<Lot> lots = new ArrayList<>();

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    private static Finder<String, StatusLot> finder = new Finder<>(StatusLot.class);

    public static StatusLot findById(String id){
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
