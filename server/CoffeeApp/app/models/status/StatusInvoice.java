package models.status;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.PagedList;
import io.ebean.annotation.JsonIgnore;
import io.ebean.text.PathProperties;
import models.Invoice;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("invoice")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = StatusInvoice.class)
public class StatusInvoice extends Status {

    public static final String APPROVED = "in_approved";
    public static final String REJECTED = "in_rejected";
    public static final String CLOSED = "in_closed";
    public static final String CANCELLED = "in_cancelled";

    @OneToMany(mappedBy = "statusInvoice")
    @JsonIgnore
    @JsonManagedReference
    private List<Invoice> invoices = new ArrayList<>();

    public static String getAPPROVED() {
        return APPROVED;
    }

    public static String getREJECTED() {
        return REJECTED;
    }

    public static String getCLOSED() {
        return CLOSED;
    }

    public static String getCANCELLED() {
        return CANCELLED;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    private static Finder<String, StatusInvoice> finder = new Finder<>(StatusInvoice.class);

    public static StatusInvoice findById(String id){
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



