package models.status;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import controllers.utils.ListPagerCollection;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.PagedList;
import io.ebean.text.PathProperties;
import models.InvoiceDetail;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("invoiceDetail")
public class StatusInvoiceDetail extends Status {

    @OneToMany(mappedBy = "statusInvoiceDetail")
    @JsonIgnore
//    @JsonManagedReference
    private List<InvoiceDetail> invoiceDetails = new ArrayList<>();

    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }


    private static Finder<String, StatusInvoiceDetail> finder = new Finder<>(StatusInvoiceDetail.class);

    public static StatusInvoiceDetail findById(String id){
        return finder.byId(id);
    }

    public static ListPagerCollection findAll(Integer index, Integer size, String sort, PathProperties pathProperties){
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