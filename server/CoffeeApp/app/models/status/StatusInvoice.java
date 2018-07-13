package models.status;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import controllers.utils.ListPagerCollection;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.PagedList;
import io.ebean.text.PathProperties;
import models.Invoice;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("invoice")
public class StatusInvoice extends Status {

    public static final String OPEN = "Open";
    public static final String CLOSED = "Closed";
    public static final String CANCELED = "Canceled";

    @OneToMany(mappedBy = "statusInvoice")
    @JsonIgnore
//    @JsonManagedReference
    private List<Invoice> invoices = new ArrayList<>();

    public static String getOPEN() {
        return OPEN;
    }

    public static String getCLOSED() {
        return CLOSED;
    }

    public static String getCANCELED() {
        return CANCELED;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    private static Finder<Long, StatusInvoice> finder = new Finder<>(StatusInvoice.class);

    public static StatusInvoice findById(Long id){
        return finder.byId(id);
    }

    public static StatusInvoice findByName(String name){
        return finder.query().where().startsWith("name", name).findUnique();
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



