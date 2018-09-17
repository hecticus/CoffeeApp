package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import controllers.parsers.jsonParser.CustomDeserializer.CustomDateTimeDeserializer;
import controllers.parsers.jsonParser.CustomDeserializer.TimeDeserializer;
import controllers.parsers.jsonParser.customSerializer.CustomDateTimeSerializer;
import controllers.utils.ListPagerCollection;
import io.ebean.*;
import io.ebean.annotation.Formula;
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
//    @JsonBackReference
    @Constraints.Required
    private Provider provider;

    @ManyToOne
//    @JsonBackReference
    private StatusInvoice statusInvoice;

//    @Formula(select = "(SELECT SUM( i.amount_invoice_detail * i.price_item_type_by_lot) " +
//            "FROM  invoice_details i WHERE i.deleted = 0 AND i.invoice_id = ${ta}.id)")
//    private BigDecimal totalInvoice;
    @Formula(select = "(SELECT SUM( i.amount_invoice_detail * i.price_item_type_by_lot + i.amount_invoice_detail * i.cost_item_type) " +
            "FROM  invoice_details i WHERE i.deleted = 0 AND i.invoice_id = ${ta}.id)")
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal totalInvoice;

    @OneToMany(mappedBy = "invoice")
    private List<InvoiceDetail> invoiceDetails;

    //    @Constraints.Required
    @Formats.DateTime(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssX")
    // @JsonSerialize(using = TimeDeserializer.class)
    @JsonDeserialize(using = TimeDeserializer.class)
    @Column(columnDefinition = "datetime")
    private ZonedDateTime startDate;

    // @Formats.DateTime(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssX")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(columnDefinition = "datetime")
    private ZonedDateTime closedDate;

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

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(ZonedDateTime closedDate) {
        this.closedDate = closedDate;
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

    public static List<Invoice> getOpenseByProviderId(Long id_provider, String dateStart){
       return finder.query().where()
               .eq("provider.id", id_provider)
               .startsWith("startDate", dateStart)
               .findList();
    }

    public static List<Invoice> invoicesByProviderId(Long id_provider){
        return finder.query().where()
                .eq("provider.id", id_provider)
                .eq("deleted",false )
                .findList();
    }

    public static Invoice invoicesByProvider(Provider provider, ZonedDateTime dateStart){
        return finder.query().where()
                .eq("provider.id", provider.getId())
                .le("startDate", dateStart)
                .eq("statusInvoice.id", 11 )
                .findUnique();
    }

    public static List<Invoice> invoicesListByProvider(Long provider, ZonedDateTime dateStart){
        return finder.query().where()
                .eq("provider.id", provider)
                .le("startDate", dateStart)
                .eq("statusInvoice.id", 11 )
                .findList();
    }

    public static Invoice findById(Long id){
        return finder.byId(id);
    }

    public static List<Invoice> findAllInvoiceActive() {
        return finder.query().where()
                .eq("statusInvoice.id", 11)
                .findList();
    }


    public static ListPagerCollection findAll( Integer pageIndex, Integer pageSize,  PathProperties pathProperties,
                                         String sort, Long id_provider, Long providerType,  ZonedDateTime startDate,
                                         ZonedDateTime closeDate, Long status ,boolean delete, String nitName){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(id_provider != 0L)
            expressionList.eq("provider.id", id_provider);

        if(providerType != 0L)
            expressionList.eq("provider.providerType.id", providerType);

        if(startDate != null && closeDate != null) {
            expressionList.between("startDate", startDate, closeDate);
        } else if(startDate != null) {
            expressionList.ge("startDate", startDate);
        } else if(closeDate != null) {
            expressionList.le("closeDate", closeDate);
        }

        if(nitName != null){
            expressionList
                    .or()
                        .startsWith("provider.nameProvider", nitName)
                        .startsWith("provider.nitProvider", nitName);
        }

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
