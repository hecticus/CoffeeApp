package models;

import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.PagedList;
import io.ebean.annotation.JsonIgnore;
import io.ebean.text.PathProperties;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("incoiceDetail")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = StatusInvoicesDetail.class)
public class StatusInvoiceDetail extends Status {

    @OneToMany(mappedBy = "statusInvoiceDetail")
    @JsonIgnore
    private List<InvoiceDetail> invoiceDetails = new ArrayList<>();

    private static Finder<String, StatusInvoiceDetail> finder = new Finder<>(StatusInvoiceDetail.class);

    public static StatusInvoiceDetail findById(String id){
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