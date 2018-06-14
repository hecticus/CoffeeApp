package models;


import com.avaje.ebean.validation.Range;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import controllers.utils.ListPagerCollection;
import io.ebean.*;
import io.ebean.annotation.CreatedTimestamp;
import io.ebean.annotation.Formula;
import io.ebean.annotation.UpdatedTimestamp;
import io.ebean.text.PathProperties;
import controllers.requestUtils.Request;
import controllers.responseUtils.CustomDateTimeSerializer;
import controllers.responseUtils.Response;
import org.joda.time.DateTime;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sm21 on 10/05/18.
 */
@Entity
@Table(name="invoices")
public class Invoice extends AbstractEntity{

    @Id
    @Column(name = "id_invoice")
    private Long idInvoice;

    @ManyToOne
    @JoinColumn(name = "id_provider", nullable = false)
    @JsonBackReference
    @Constraints.Required
    private Provider provider;

    @Column( name = "status_invoice")
    @Range(min = 0, max = 3)
    private Integer statusInvoice;

    @OneToMany(mappedBy = "invoice")
    private List<InvoiceDetail> invoiceDetails;

    @Formula(select = "(SELECT SUM( t.amount_invoiceDetail*t.price_ItemTypeByLot + t.amount_invoiceDetail*cost_ItemType) FROM  invoice_details t WHERE t.status_delete = 0 AND t.id_invoice = ${ta}.id_invoice)")
    private BigDecimal totalInvoice;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedTimestamp
    @Column(name = "dueDate_invoice", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private ZonedDateTime startDateInvoice;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdatedTimestamp
    @Column(name = "closedDate_invoice", insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private ZonedDateTime closedDateInvoice;

    // GETTER AND SETTER
    private static Finder<Long, Invoice> finder = new Finder<>(Invoice.class);

    public Invoice() {
        statusInvoice = 1;
        invoiceDetails = new ArrayList<>();
    }

    public Long getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(Long idInvoice) {
        this.idInvoice = idInvoice;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Integer getStatusInvoice() {
        return statusInvoice;
    }

    public void setStatusInvoice(Integer statusInvoice) {
        this.statusInvoice = statusInvoice;
    }

    public ZonedDateTime getStartDateInvoice() {
        return startDateInvoice;
    }

    public void setStartDateInvoice(ZonedDateTime startDateInvoice) {
        this.startDateInvoice = startDateInvoice;
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
                                         String sort, Long id_provider, Long id_providertype, String startDate,
                                         String endDate, Integer status ,Boolean deleted){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(id_provider != 0L)
            expressionList.eq("provider.idProvider", id_provider);

        if(id_providertype != 0L)
            expressionList.eq("provider.providerType.idProviderType", id_providertype);

        if(startDate != null)
            expressionList.eq("startDateInvoice", startDate);

        if(endDate!= null)
            expressionList.eq("closedDateInvoice", endDate);

        if(sort != null) {
            if(sort.contains(" ")) {
                String []  aux = sort.split(" ", 2);
                expressionList.orderBy(sort( aux[0], aux[1]));
            }else {
                expressionList.orderBy(sort("idInvoice", sort));
            }
        }

        if(status != null)
            expressionList.eq("status_invoice", status);

        if( deleted )
            expressionList.setIncludeSoftDeletes();

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());

        return new ListPagerCollection(
                expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(),
                expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findCount(),
                pageIndex,
                pageSize);
    }


    public static List<Invoice> toInvoices(List<SqlRow>  sqlRows)
    {
        List<Invoice> invoices = new ArrayList<>();
        Provider provider;
        Invoice invoice;
        try {
            for (int i = 0; i < sqlRows.size(); ++i) {
                provider = new Provider();
                invoice = new Invoice();
                provider.setContactNameProvider(sqlRows.get(i).getString("contact_name"));
                provider.setFullNameProvider(sqlRows.get(i).getString("full_name"));
                provider.setPhoneNumberProvider(sqlRows.get(i).getString("phone_number"));
                provider.setIdentificationDocProvider(sqlRows.get(i).getString("identification_doc"));
                provider.setAddressProvider(sqlRows.get(i).getString("address"));
                provider.setIdProvider(sqlRows.get(i).getLong("prov_id"));
                provider.setPhotoProvider(sqlRows.get(i).getString("photo_provider"));

                invoice.setProvider(provider);
                invoice.setStatusInvoice(sqlRows.get(i).getInteger("status"));
                invoice.setIdInvoice(sqlRows.get(i).getLong("invo_id"));
                //invoice.setStartDateInvoice(Request.dateTimeFormatter.parseDateTime(sqlRows.get(i).getString("start_date")));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s");
                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = sdf.parse(sqlRows.get(i).getString("start_date"));
                String formattedTime = output.format(d);

//                invoice.setStartDateInvoice(Request.dateTimeFormatter.parseDateTime(formattedTime));


//                d = sdf.parse(sqlRows.get(i).getString("closed_date"));
//                formattedTime = output.format(d);

//                invoice.setClosedDateInvoice(Request.dateTimeFormatter.parseDateTime(formattedTime));
                invoice.setTotalInvoice(sqlRows.get(i).getBigDecimal("total"));

                invoices.add(invoice);
            }
        }   catch(Exception e){
            Throwable eRoot = Response.getCause(e);
            eRoot.printStackTrace();
            return null;
        }

        return invoices;
    }



    public static  List<Invoice> getOpenByProviderId(Long providerId){
        return finder.query().where().eq("id_provider",providerId)
                .eq("status_invoice",1)
                .orderBy("dueDate_invoice desc")
                .findList();
    }

    public static  BigDecimal calcularTotalInvoice(Long idInvioce) {
        List<InvoiceDetail> invoiceDetails;// = InvoiceDetail.finderAllByIdInvoice(idInvioce);
        Lot lot;
        Invoice invoice = Invoice.findById(idInvioce);//this.findById(idInvioce);
        BigDecimal total = BigDecimal.ZERO;
        boolean harvest = true;
        if(invoice.getProvider().getProviderType().getNameProviderType().toUpperCase().equals("VENDEDOR")) harvest=false;

//        for(InvoiceDetail invoiceDetail: invoiceDetails){
//            if (!harvest) total = total.add(invoiceDetail.getCostItemType().multiply(invoiceDetail.getAmountInvoiceDetail()));
//            else{
//                lot = Lot.findById(invoiceDetail.getLot().getIdLot());
//                total=total.add(lot.getPrice_lot().multiply(invoiceDetail.getAmountInvoiceDetail()));
//            }
//        }

        return total;
    }

}
