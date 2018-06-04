package models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import controllers.utils.ListPagerCollection;
import io.ebean.*;
import io.ebean.annotation.CreatedTimestamp;
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

    @Column(nullable = false, name = "status_invoice")
    private Integer statusInvoice = 1;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedTimestamp
    @Column(name = "dueDate_invoice", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",  updatable = false)//, insertable = false)
    private DateTime startDateInvoice;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdatedTimestamp
    @Column(name = "closedDate_invoice", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")//, insertable = false)
    private DateTime closedDateInvoice;

    @OneToMany(mappedBy = "invoice")
    private List<InvoiceDetail> invoiceDetails = new ArrayList<>();

    @Constraints.Min(0)
    @Column(name = "total_invoice")
    private BigDecimal totalInvoice;

    // GETTER AND SETTER
    private static Finder<Long, Invoice> finder = new Finder<>(Invoice.class);

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

    @JsonIgnore
    public DateTime getStartDateInvoice() {
        return startDateInvoice;
    }

    public void setStartDateInvoice(DateTime startDateInvoice) {
        this.startDateInvoice = startDateInvoice;
    }

    @JsonIgnore
    public DateTime getClosedDateInvoice() {
        return closedDateInvoice;
    }

    public void setClosedDateInvoice(DateTime closedDateInvoice) {
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

    public static boolean existName(String name_itemtype){
        if(finder.query().where().eq("name_itemtype",name_itemtype ).findUnique() != null ) return true;
        return false;
    }

    public static boolean existId(Long id) {
        if(InvoiceDetail.findById(id) != null ) return true;
        return false;
    }

    public static  List<Invoice> getOpenByProviderId(Long providerId){
        return finder.query().where().eq("id_provider",providerId)
                .eq("status_delete",0)
                .eq("status_invoice",1)
                .orderBy("dueDate_invoice desc")
                .findList();
    }

    public static ListPagerCollection findAll( Integer pageIndex, Integer pageSize, String sort, PathProperties
                                pathProperties, Long id_provider, Long id_providertype, String startDate, String
                                endDate, Integer status ,Integer all){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
//            if(pathProperties.hasPath("provider"))
//                finder.query().fetch("provider");
//            if(pathProperties.hasPath("provider.providerType"))
//                finder.query().fetch("provider.providerType");
            expressionList.apply(pathProperties);


        if(id_provider != 0L)
            expressionList.eq("id_Provider", id_provider);

        if(startDate != null)
            expressionList.eq("dueDate_invoice", startDate);

        if(endDate!= null)
            expressionList.eq("closedDate_invoice", endDate);

        if(sort != null)
            expressionList.orderBy(sort(sort));

        if(status != null)
            expressionList.eq("status_invoice", status);

        expressionList.eq("status_delete", all);

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());

        return new ListPagerCollection(
                expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(),
                expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findCount(),
                pageIndex,
                pageSize);
    }


    public static  ListPagerCollection getByDateByTypeProvider(String date, Long typeProvider, Integer pageIndex, Integer pagesize){

        String sql = "SELECT invo.id_invoice AS invo_id, invo.status_invoice as status, invo.duedate_invoice as start_date, " +
                " invo.closeddate_invoice as closed_date, invo.total_invoice as total," +
                " prov.id_provider AS prov_id, prov.fullname_provider as full_name," +
                " prov.address_provider as address," +
                " prov.contactname_provider as contact_name, prov.identificationdoc_provider as identification_doc," +
                " prov.phonenumber_provider as phone_number, prov.photo_provider as photo_provider" +
                " FROM invoices invo " +
                " INNER JOIN  providers prov ON prov.id_provider = invo.id_provider " +
                " where invo.duedate_invoice like '"+date+"%'";

        if(typeProvider!= 0L)  sql+=" and prov.id_providertype= :typeProvider ";

        sql+=" and invo.status_delete=0 and prov.status_delete=0 order by prov.fullname_provider ASC ";

        //    sql += " limit :pageIndex, :pagesize";


        List<SqlRow>  sqlRows = Ebean.createSqlQuery(sql)
                .setParameter("typeProvider", typeProvider)
                .setParameter("date", date)
                .setParameter("pageIndex",pageIndex)
                .setParameter("pagesize",pagesize)
                .findList();

        List<Invoice> invoiceList = toInvoices(sqlRows);

        return new ListPagerCollection(invoiceList.subList(pageIndex,Math.min(pageIndex+pagesize, invoiceList.size())), invoiceList.size(), pageIndex, pagesize);
        //return toInvoices(sqlRows)
    }


    public static Boolean deletedInvoice( Long invoiceId){
        try {
            String sql = " CALL `deletedInvoicesAndInvoicesDetails`(:invoiceId) ";

            SqlQuery query = Ebean.createSqlQuery(sql)
                    .setParameter("invoiceId", invoiceId);

            SqlRow result = query.findUnique();
            return false;
        }catch (Exception e){
            return true;
        }
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

                invoice.setStartDateInvoice(Request.dateTimeFormatter.parseDateTime(formattedTime));


                d = sdf.parse(sqlRows.get(i).getString("closed_date"));
                formattedTime = output.format(d);

                invoice.setClosedDateInvoice(Request.dateTimeFormatter.parseDateTime(formattedTime));
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

    public static ListPagerCollection findAllSearch(Integer pageIndex, Integer pageSize, String sort, PathProperties
            pathProperties, Integer id_provider, Integer id_providertype, String startDate, String endDate){
        ExpressionList expressionList;
        try{

            SimpleDateFormat Datetemp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(startDate.equals("")){
                startDate = "1970-01-01";
            }

            if(endDate.equals("")){
                endDate="5000-01-01";
            }
            String startDateTemp = startDate.concat(" 00:00:00");
            String endDateTemp = endDate.concat(" 23:59:59");
            // Date startDateTemp = Datetemp.parse(startDate);
            // Date endDateTemp = Datetemp.parse(endDate);

            if(!id_provider.equals(-2)){
                //to invoices t1 provider
                if(!id_provider.equals(-1)) expressionList = finder.query().fetch("provider").where().eq("t0.status_delete", 0).eq("t0.id_provider",id_provider).between("t0.duedate_invoice",startDateTemp,endDateTemp);
                else
                { expressionList = finder.query()
                        .fetch("provider")
                        .where()
                        .eq("t1.id_providertype",id_providertype)
                        .eq("t0.status_delete",0)
                        .between("duedate_invoice",startDateTemp,endDateTemp);

                }

                if (pathProperties != null)
                    expressionList.apply(pathProperties);

                if (sort != null)
                    expressionList.orderBy(AbstractEntity.sort(sort));

                if (pageIndex == null || pageSize == null)
                    return new ListPagerCollection(expressionList.findList());

                //to invoices t1 provider
                int findRowCount = expressionList.eq("t1.id_providertype",id_providertype).eq("t0.status_delete",0).findList().size();

                return new ListPagerCollection(expressionList.eq("t1.id_providertype",id_providertype).eq("t0.status_delete",0).setFirstRow(pageIndex).setMaxRows(pageSize).findList(), findRowCount, pageIndex, pageSize);
            }
        }
        catch(Exception e){
            Throwable eRoot = Response.getCause(e);
            eRoot.printStackTrace();

        }

        expressionList = finder.query().where().eq("status_delete", -2);
        return new ListPagerCollection(expressionList.findList());

    }

    public static  BigDecimal calcularTotalInvoice(Long idInvioce) {
        List<InvoiceDetail> invoiceDetails = InvoiceDetail.finderAllByIdInvoice(idInvioce);
        Lot lot;
        Invoice invoice = Invoice.findById(idInvioce);//this.findById(idInvioce);
        BigDecimal total = BigDecimal.ZERO;
        boolean harvest = true;
        if(invoice.getProvider().getProviderType().getNameProviderType().toUpperCase().equals("VENDEDOR")) harvest=false;

        for(InvoiceDetail invoiceDetail: invoiceDetails){
            if (!harvest) total = total.add(invoiceDetail.getCostItemType().multiply(invoiceDetail.getAmountInvoiceDetail()));
            else{
                lot = Lot.findById(invoiceDetail.getLot().getIdLot());
                total=total.add(lot.getPrice_lot().multiply(invoiceDetail.getAmountInvoiceDetail()));
            }
        }

        return total;
    }

}
