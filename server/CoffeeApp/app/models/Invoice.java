package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import controllers.utils.ListPagerCollection;
import io.ebean.*;
import io.ebean.annotation.Formula;
import io.ebean.annotation.UpdatedTimestamp;
import io.ebean.text.PathProperties;
import models.status.StatusInvoice;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sm21 on 10/05/18.
 */
@Entity
@Table(name="invoices")
public class Invoice extends AbstractEntity{

    @ManyToOne
    @JoinColumn( nullable = false)
    @JsonBackReference
    @Constraints.Required
    private Provider provider;

    @ManyToOne
    private StatusInvoice statusInvoice;

    @Formula(select = "(SELECT SUM( i.amount_invoice_detail * i.price_item_type_by_lot + i.amount_invoice_detail * i.cost_item_type) " +
            "FROM  invoice_details i WHERE i.deleted = 0 AND i.invoice_id = ${ta}.id)")
    private BigDecimal totalInvoice;

    @OneToMany(mappedBy = "invoice")
    private List<InvoiceDetail> invoiceDetails;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdatedTimestamp
    @Column(name = "closedDate_invoice", insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private ZonedDateTime closedDateInvoice;

    // GETTER AND SETTER
    private static Finder<Long, Invoice> finder = new Finder<>(Invoice.class);

    public Invoice() {
        invoiceDetails = new ArrayList<>();
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public StatusInvoice getStatusInvoice() {
        return statusInvoice;
    }

    public void setStatusInvoice(StatusInvoice statusInvoice) {
        this.statusInvoice = statusInvoice;
    }

    public ZonedDateTime getClosedDateInvoice() {
        return closedDateInvoice;
    }

    public void setClosedDateInvoice(ZonedDateTime closedDateInvoice) {
        this.closedDateInvoice = closedDateInvoice;
    }

    @JsonIgnore
    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public BigDecimal getTotalInvoice() {
        return totalInvoice;
    }

    public void setTotalInvoice(BigDecimal totalInvoice) {
        this.totalInvoice = totalInvoice;
    }

    //METODOS DEFINIDOS
    public static Invoice findById(Long id){
        return finder.byId(id);
    }


    public static ListPagerCollection findAll( Integer pageIndex, Integer pageSize,  PathProperties pathProperties,
                                         String sort, Long id_provider, Long providerType, String startDate,
                                         String endDate, Long status ,boolean delete){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(id_provider != 0L)
            expressionList.eq("provider.id", id_provider);

        if(providerType != 0L)
            expressionList.eq("provider.providerType.id", providerType);

        if(startDate != null)
            expressionList.startsWith("createdAt", startDate);

        if(endDate!= null)
            expressionList.startsWith("closedDateInvoice", endDate);

        if(sort != null)
            expressionList.orderBy(sort( sort));

        if(status != 0L)
            expressionList.eq("statusInvoice.id", status);

        if( delete )
            expressionList.setIncludeSoftDeletes();

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());

        return new ListPagerCollection(
                expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(),
                expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findCount(),
                pageIndex,
                pageSize);
    }

}
