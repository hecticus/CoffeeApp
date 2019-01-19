package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Constraints.Required
    @Formats.DateTime(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
    @Column(columnDefinition = "datetime", nullable = false)
    private ZonedDateTime startDate;

    @Formats.DateTime(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
    @Column(columnDefinition = "datetime")
    private ZonedDateTime closedDate;


    // GETTER AND SETTER
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
    private static Finder<Long, Invoice> finder = new Finder<>(Invoice.class);

    public static Invoice findById(Long id){
        return finder.byId(id);
//        return finder.query().where().eq("id", id).findUnique();
    }

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

    public static List<Invoice> findAllInvoiceActive() {
        return finder.query().where()
                .eq("statusInvoice.id", 11)
                .findList();
    }


    public static ListPagerCollection findAll( Integer pageIndex, Integer pageSize,  PathProperties pathProperties,
                                         String sort, Long id_provider, Long providerType,  String startDate,
                                         String closeDate, Long status ,boolean delete, String nitName){

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
            expressionList.le("startDate", closeDate);
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
                "    DATE_FORMAT(c.created_at, '%d/%m/%Y'T'%H:%i:%sZ') as 'Fecha de Apertura',\n" +
                "    DATE_FORMAT(c.closed_date, '%d/%m/%Y %H:%i:%s') as 'Fecha de Cierre',\n" +
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

    public static ListPagerCollection createDetailReport(Integer pageIndex, Integer pageSize,  PathProperties pathProperties,
                                                         String sort, Long id_provider, Long providerType,  String startDate,
                                                         String closeDate, Long status ,boolean delete, String nitName){

        String sql = "SELECT \n" +
                "    p.nit_provider AS 'Identificación del Proveedor',\n" +
                "    p.name_provider AS 'Nombre del Proveedor',\n" +
                "    DATE_FORMAT( i.start_date, '%d/%m/%Y') AS 'Fecha de Apertura',\n" +
                "    i.id AS 'Numero de Recibo',\n" +
                "    id.price_item_type_by_lot AS 'Costo de Cafe',\n" +
                "    id.cost_item_type AS 'Precio del Cafe',\n" +
                "    id.amount_invoice_detail AS Peso,\n" +
                "    it.name_item_type AS 'Tipo de Cafe',\n" +
                "    (id.amount_invoice_detail * id.price_item_type_by_lot + id.amount_invoice_detail * id.cost_item_type) AS 'Total de la Factura'\n" +
                "FROM\n" +
                "    CoffeeApp.invoices AS i,\n" +
                "    CoffeeApp.invoice_details AS id,\n" +
                "    CoffeeApp.providers AS p,\n" +
                "    CoffeeApp.provider_type AS pt,\n" +
                "    CoffeeApp.status AS s,\n" +
                "    CoffeeApp.lots AS l,\n" +
                "    CoffeeApp.item_types AS it\n" +
                "WHERE\n" +
                "    i.provider_id = p.id\n" +
                "        AND p.provider_type_id = pt.id\n" +
                "        AND i.deleted = 0\n" +
                "        AND id.deleted = 0\n" +
                "        AND id.invoice_id = i.id\n" +
                "        AND s.id = i.status_invoice_id\n" +
                "        AND (id.lot_id = l.id OR id.lot_id IS NULL)\n" +
                "        AND id.item_type_id = it.id\n ";

        if(providerType != 0L)
            sql += "        AND pt.id = "+ providerType + "\n";

        if(nitName != null)
            sql += "        AND (p.nit_provider = " + nitName + " OR p.name_provider = "+ nitName +")\n";

        if(status != 0L)
            sql += "        AND i.status_invoice_id = " + status + " AND s.dtype = 'invoice'\n";
        if(startDate != null && closeDate != null) {
            sql += "        AND ( id.start_date between '" + startDate +"' AND '"+ closeDate +"')\n";
        } else if(startDate != null) {
            sql += "        AND id.start_date >=  '"+startDate +"'\n";
        } else if(closeDate != null) {
            sql += "        AND id.closed_date <= '"+ closeDate +"' OR id.closed_date is null) \n";
        }

        // sql +=  "GROUP BY id.start_date , i.closed_date , pt.name_provider_type , p.name_provider , p.nit_provider,\n" +
        //         "id.amount_invoice_detail, id.price_item_type_by_lot, id.amount_invoice_detail, id.cost_item_type,\n" +
        //         "it.name_item_type, i.id\n" +
            sql +=  "ORDER BY p.name_provider, i.id, id.start_date ASC;";

        List<SqlRow>  sqlRows = Ebean.createSqlQuery(sql).findList();

        return new ListPagerCollection(sqlRows);
    }

    public static ListPagerCollection createReport(Integer pageIndex, Integer pageSize,  PathProperties pathProperties,
                                                   String sort, Long id_provider, Long providerType,  String startDate,
                                                   String closeDate, Long status ,boolean delete, String nitName){

        String sql = "SELECT \n" +
                "    DATE_FORMAT( i.start_date, '%d/%m/%Y') AS 'Fecha de Apertura',\n" +
                "    p.nit_provider AS 'Identificación del Proveedor',\n" +
                "    p.name_provider AS 'Nombre del Proveedor',\n" +
                "    SUM(id.amount_invoice_detail) AS Peso,\n" +
                "    SUM(id.amount_invoice_detail * id.price_item_type_by_lot + id.amount_invoice_detail * id.cost_item_type) AS 'Total de la Factura'\n" +
                "FROM\n" +
                "    CoffeeApp.invoices AS i,\n" +
                "    CoffeeApp.invoice_details AS id,\n" +
                "    CoffeeApp.providers AS p,\n" +
                "    CoffeeApp.provider_type AS pt,\n" +
                "    CoffeeApp.status AS s\n" +
                "WHERE\n" +
                "    i.provider_id = p.id\n" +
                "        AND p.provider_type_id = pt.id\n" +
                "        AND i.deleted = 0\n" +
                "        AND id.deleted = 0\n" +
                "        AND id.invoice_id = i.id\n" +
                "        AND s.id = i.status_invoice_id\n";

        if(nitName != null)
            sql += "        AND (p.nit_provider = " + nitName + " OR p.name_provider = "+ nitName +")\n";

        if(status != 0L)
            sql += "        AND i.status_invoice_id = " + status + " AND s.dtype = 'invoice'\n";

        if(providerType != 0L)
            sql += "        AND pt.id = "+ providerType + "\n";

        if(startDate != null && closeDate != null) {
            sql += "        AND ( i.start_date between '" + startDate +"' AND '"+ closeDate +"')\n";
        } else if(startDate != null) {
            sql += "        AND i.start_date >=  '"+ startDate + "'\n";
        } else if(closeDate != null) {
            sql += "        AND ( i.closed_date <= '" + closeDate + "' OR i.closed_date is null) \n";
        }

        sql += " GROUP BY i.start_date , i.closed_date , pt.name_provider_type , p.name_provider , p.nit_provider\n" +
                "ORDER BY p.name_provider, i.start_date ASC";

        List<SqlRow>  sqlRows = Ebean.createSqlQuery(sql).findList();

        return new ListPagerCollection(sqlRows);
    }

    public static ListPagerCollection createPagos(Integer pageIndex, Integer pageSize,  PathProperties pathProperties,
                                                   String sort, Long id_provider, Long providerType,  String startDate,
                                                   String closeDate, Long status ,boolean delete, String nitName){

        String sql = "SELECT \n" +
                "    Min(DATE_FORMAT(c.start_date, '%d/%m/%Y')) as 'Fecha de Primera Cosecha',\n" +
                "    Max(DATE_FORMAT(c.start_date, '%d/%m/%Y')) as 'Fecha de Ultima Cosecha',\n" +
                "    p.nit_provider as 'Identificación del Proveedor',\n" +
                "    p.name_provider as 'Nombre del Proveedor',\n" +
                "    SUM( i.amount_invoice_detail) as 'Peso total Cosechado',\n" +
                "    SUM( i.amount_invoice_detail * i.price_item_type_by_lot + i.amount_invoice_detail * i.cost_item_type) as 'Monto Total a Pagar'\n" +
                "FROM\n" +
                "    CoffeeApp.invoices AS c,\n" +
                "    CoffeeApp.providers AS p,\n" +
                "    CoffeeApp.status AS s,\n" +
                "    CoffeeApp.provider_type as t,\n" +
                "    CoffeeApp.invoice_details as i\n" +
                "WHERE\n" +
                "    c.provider_id = p.id\n" +
                "    AND c.status_invoice_id = s.id\n" +
                "    AND p.provider_type_id = t.id\n" +
                "    AND i.deleted = 0 \n" +
                "    AND i.invoice_id = c.id\n";


        if(providerType != 0L)
            sql += "        AND p.provider_type_id = "+ providerType + "\n";

        if(nitName != null)
            sql += "        AND (p.nit_provider = " + nitName + " OR p.name_provider = "+ nitName +")\n";

        if(status != 0L)
            sql += "        AND i.status_invoice_id = " + status + " AND s.dtype = 'invoice'\n";

        if(startDate != null && closeDate != null) {
            sql += "        AND ( i.start_date between '" + startDate +"' AND '"+ closeDate +"')\n";
        } else if(startDate != null) {
            sql += "        AND i.start_date >=  '"+ startDate + "'\n";
        } else if(closeDate != null) {
            sql += "        AND ( i.closed_date <= '" + closeDate + "' OR i.closed_date is null) \n";
        }

        sql += "GROUP BY  +\n" +
                "      p.name_provider,\n" +
                "      p.nit_provider\n" +
                "ORDER BY" +
                "      p.nit_provider ASC,\n" +
                "      p.nit_provider ASC;";

        List<SqlRow>  sqlRows = Ebean.createSqlQuery(sql).findList();

        return new ListPagerCollection(sqlRows);
    }

}
