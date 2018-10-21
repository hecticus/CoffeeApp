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
    @Constraints.Required
    private Provider provider;

    @ManyToOne
    private StatusInvoice statusInvoice;

    @Formula(select = "(SELECT SUM( i.amount_invoice_detail * i.price_item_type_by_lot + i.amount_invoice_detail * i.cost_item_type) " +
            "FROM  invoice_details i WHERE i.deleted = 0 AND i.invoice_id = ${ta}.id)")
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal totalInvoice;

    @OneToMany(mappedBy = "invoice")
    private List<InvoiceDetail> invoiceDetails;

    @Formats.DateTime(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssX")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = TimeDeserializer.class)
    @Column(columnDefinition = "datetime")
    private ZonedDateTime startDate;

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

    public static ListPagerCollection createTotalReport(){

        String sql = " SELECT \n" +
                "    c.created_at as 'Fecha de Apertura',\n" +
                "    c.closed_date as 'Fecha de Cierre',\n" +
                "    s.name as 'Status de Factura',\n" +
                "    t.name_provider_type as 'Tipo de Proveedor',\n" +
                "    p.nit_provider as 'Identificación del Proveedor',\n" +
                "    p.name_provider as 'Nombre del Proveedor',\n" +
                "\tSUM( i.amount_invoice_detail) as 'Peso Total',\n" +
                "   \tSUM( i.amount_invoice_detail * i.price_item_type_by_lot + i.amount_invoice_detail * i.cost_item_type) as 'Total de la Factura'\n" +
                "FROM\n" +
                "    CoffeeApp.invoices AS c,\n" +
                "    CoffeeApp.providers AS p,\n" +
                "    CoffeeApp.status AS s,\n" +
                "    CoffeeApp.provider_type as t,\n" +
                "    CoffeeApp.invoice_details as i\n" +
                "WHERE\n" +
                "    c.provider_id = p.id\n" +
                "        AND c.status_invoice_id = s.id\n" +
                "        AND p.provider_type_id = t.id\n" +
                "        AND i.deleted = 0 \n" +
                "        AND i.invoice_id = c.id\n" +
                "GROUP BY  c.created_at,\n" +
                "    c.closed_date,\n" +
                "    t.name_provider_type,\n" +
                "    p.name_provider,\n" +
                "    p.nit_provider,\n" +
                "    s.name;";

        List<SqlRow>  sqlRows = Ebean.createSqlQuery(sql).findList();

        return new ListPagerCollection(sqlRows);
    }

    public static ListPagerCollection createDetailReport(){

        String sql = " SELECT \n" +
                "    c.created_at AS 'Fecha de Apertura',\n" +
                "    c.closed_date AS 'Fecha de Cierre',\n" +
                "    p.nit_provider as 'Identificación del Proveedor',\n" +
                "    p.name_provider as 'Nombre del Proveedor',\n" +
                "    l.name_lot as 'Nombre del Lote',\n" +
                "    i.amount_invoice_detail as Peso,\n" +
                "    it.name_item_type as 'Clase de Cafe',\n" +
                "    i.cost_item_type as 'Precio',\n" +
                "    i.price_item_type_by_lot as 'Costo',\n" +
                "    (i.amount_invoice_detail * i.price_item_type_by_lot + i.amount_invoice_detail * i.cost_item_type) AS Total\n" +
                "FROM\n" +
                "    CoffeeApp.invoices AS c,\n" +
                "    CoffeeApp.providers AS p,\n" +
                "    CoffeeApp.lots AS l,\n" +
                "    CoffeeApp.invoice_details AS i,\n" +
                "    CoffeeApp.item_types AS it\n" +
                "WHERE\n" +
                "    c.provider_id = p.id\n" +
                "        AND i.deleted = 0\n" +
                "        AND i.invoice_id = c.id\n" +
                "        AND i.lot_id = l.id\n" +
                "        AND i.item_type_id = it.id\n" +
                "GROUP BY c.created_at , c.closed_date , p.name_provider , \n" +
                "p.nit_provider , i.amount_invoice_detail , i.price_item_type_by_lot , i.cost_item_type , \n" +
                "l.name_lot , i.price_item_type_by_lot , it.name_item_type;";

        List<SqlRow>  sqlRows = Ebean.createSqlQuery(sql).findList();

        return new ListPagerCollection(sqlRows);
    }



/*    String sql = "SELECT invo.id_invoice AS invo_id, invo.status_invoice as status, invo.duedate_invoice as start_date, " +
            " invo.closeddate_invoice as closed_date, invo.total_invoice as total," +
            " prov.id_provider AS prov_id, prov.fullname_provider as full_name," +
            " prov.address_provider as address," +
            " prov.contactname_provider as contact_name, prov.identificationdoc_provider as identification_doc," +
            " prov.phonenumber_provider as phone_number, prov.photo_provider as photo_provider" +
            " FROM invoices invo " +
            " INNER JOIN  providers prov ON prov.id_provider = invo.id_provider " +
            " where invo.duedate_invoice like '"+date+"%'";

        if(typeProvider!=-1)  sql+=" and prov.id_providertype= :typeProvider ";

    sql+=" and invo.status_delete=0 and prov.status_delete=0 order by prov.fullname_provider ASC ";

    //    sql += " limit :pageIndex, :pagesize";


    List<SqlRow>  sqlRows = Ebean.createSqlQuery(sql)
            .setParameter("typeProvider", typeProvider)
            .setParameter("date", date)
            .setParameter("pageIndex",pageIndex)
            .setParameter("pagesize",pagesize)
            .findList();

    List<Invoice> invoiceList =toInvoices(sqlRows);*/
}
