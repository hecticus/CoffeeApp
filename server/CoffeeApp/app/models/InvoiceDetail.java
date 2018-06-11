package models;

import com.avaje.ebean.validation.Range;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.utils.ListPagerCollection;
import io.ebean.*;
import io.ebean.annotation.CreatedTimestamp;
import io.ebean.text.PathProperties;
import org.joda.time.DateTime;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.libs.Json;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sm21 on 10/05/18.
 */
@Entity
@Table(name="invoice_details")
public class InvoiceDetail  extends AbstractEntity{

    @Id
    @Column(name = "id_invoiceDetail")
    private Long idInvoiceDetail;

    @ManyToOne
    @Constraints.Required
    @JoinColumn(name = "id_invoice", nullable = false)
    private Invoice invoice;

    @ManyToOne
    @Constraints.Required
    @JoinColumn(name = "id_itemType", nullable = false)
    private ItemType itemType;

    @ManyToOne
    @Constraints.Required
    @JoinColumn(name = "id_lot")
    private Lot lot;

    @ManyToOne
    @Constraints.Required
    @JoinColumn(name = "id_store")
    private Store store;

    @Constraints.Min(0)
    @Constraints.Required
    @Column(nullable = false, name = "price_ItemTypeByLot")
    private BigDecimal priceItemTypeByLot; //esta en lote desconozco la relacion

    @Constraints.Required
    @Constraints.Min(0)
    @Column(nullable = false, name = "cost_ItemType")
    private BigDecimal costItemType;//esta en Item Type desconozco la relacion

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedTimestamp
    @Column(nullable = false, name = "dueDate_invoiceDetail", columnDefinition =
            "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", updatable = false)  //, insertable = false)
    private DateTime startDateInvoiceDetail;

    @Constraints.Required
    @Constraints.Min(0)
    @Column(nullable = false, name = "amount_invoiceDetail")
    private BigDecimal amountInvoiceDetail;

    @Column(name = "isFreight_invoiceDetail")
    private boolean freightInvoiceDetail;

    @Column(name = "note_invoiceDetail", columnDefinition = "text")
    private String noteInvoiceDetail;

    @Constraints.Required
    @Constraints.MaxLength(100)
    @Column(nullable = false, name = "nameReceived_invoiceDetail", length = 100)
    private String nameReceivedInvoiceDetail;

    @Constraints.Required
    @Constraints.MaxLength(100)
    @Column(nullable = false, name = "nameDelivered_invoiceDetail", length = 100)
    private String nameDeliveredInvoiceDetail;

    @Range(min = 0, max = 1)
    @Column(nullable = false, name = "status_invoiceDetail")
    private Integer statusInvoiceDetail;

    @OneToMany(mappedBy = "invoiceDetail", cascade= CascadeType.ALL)
    private List<InvoiceDetailPurity> invoiceDetailPurity;

    private static Finder<Long, InvoiceDetail> finder = new Finder<>(InvoiceDetail.class);

    public InvoiceDetail() {
        statusInvoiceDetail = 1;
        freightInvoiceDetail = false;
//        costItemType = BigDecimal.ZERO;
        invoiceDetailPurity = new ArrayList<>();
    }

    public Long getIdInvoiceDetail() {
        return idInvoiceDetail;
    }

    public void setIdInvoiceDetail(Long idInvoiceDetail) {
        this.idInvoiceDetail = idInvoiceDetail;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public List<InvoiceDetailPurity> getInvoiceDetailPurity() {
        return invoiceDetailPurity;
    }

    public void setInvoiceDetailPurity(List<InvoiceDetailPurity> invoiceDetailPurity) {
        this.invoiceDetailPurity = invoiceDetailPurity;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }


    public BigDecimal getCostItemType() {
        return costItemType;
    }

    public void setCostItemType(BigDecimal costItemType) {
        this.costItemType = costItemType;
    }

    public DateTime getStartDateInvoiceDetail() {
        return startDateInvoiceDetail;
    }

    public void setStartDateInvoiceDetail(DateTime startDateInvoiceDetail) {
        this.startDateInvoiceDetail = startDateInvoiceDetail;
    }

    public BigDecimal getAmountInvoiceDetail() {
        return amountInvoiceDetail;
    }

    public void setAmountInvoiceDetail(BigDecimal amountInvoiceDetail) {
        this.amountInvoiceDetail = amountInvoiceDetail;
    }

    public boolean isFreightInvoiceDetail() {
        return freightInvoiceDetail;
    }

    public void setFreightInvoiceDetail(boolean freightInvoiceDetail) {
        this.freightInvoiceDetail = freightInvoiceDetail;
    }

    public String getNoteInvoiceDetail() {
        return noteInvoiceDetail;
    }

    public void setNoteInvoiceDetail(String noteInvoiceDetail) {
        this.noteInvoiceDetail = noteInvoiceDetail;
    }

    public String getNameReceivedInvoiceDetail() {
        return nameReceivedInvoiceDetail;
    }

    public void setNameReceivedInvoiceDetail(String nameReceivedInvoiceDetail) {
        this.nameReceivedInvoiceDetail = nameReceivedInvoiceDetail;
    }

    public String getNameDeliveredInvoiceDetail() {
        return nameDeliveredInvoiceDetail;
    }

    public void setNameDeliveredInvoiceDetail(String nameDeliveredInvoiceDetail) {
        this.nameDeliveredInvoiceDetail = nameDeliveredInvoiceDetail;
    }

    public Integer getStatusInvoiceDetail() {
        return statusInvoiceDetail;
    }

    public void setStatusInvoiceDetail(Integer statusInvoiceDetail) {
        this.statusInvoiceDetail = statusInvoiceDetail;
    }

    //Metodos Creados
    public static InvoiceDetail findById(Long id){
        return finder.byId(id);
    }


    public static JsonNode finderAllByIdInvoiceSummary(Long idInvoice) {
        ObjectNode aux;
        List<ObjectNode> result = new ArrayList<>();
        String sql="SELECT duedate_invoicedetail as c0, SUM(amount_invoicedetail) as c1 " +
                "FROM invoice_details " +
                "where id_invoice=:idInvoice " +
                "and status_delete=0 "+
                "group by duedate_invoicedetail " +
                "order by duedate_invoicedetail";

        SqlQuery query = Ebean.createSqlQuery(sql).setParameter("idInvoice", idInvoice);

        List<SqlRow> results = query.findList();

        for(int i=0; i < results.size(); ++i) {
            aux= Json.newObject();

            aux.put("startDateInvoiceDetail",results.get(i).getTimestamp("c0").toString());
            aux.put("amountTotal",results.get(i).getString("c1"));

            result.add(aux);
        }
        return Json.toJson(result);
    }


    public static int deleteAllByIdInvoiceAndDate(Long idInvoice, String date){

        date = "'"+date+"'";

        String sql="update invoice_details " +
                "set status_delete=1 " +
                "where duedate_invoicedetail= "+date+"  and id_invoice= "+idInvoice+";";

        SqlUpdate query = Ebean.createSqlUpdate(sql);

        return query.execute();
    }

    public static List<InvoiceDetail> findAllList(Long invoice, Long itemType, Long lot, Long store, String nameReceivedInvoiceDetail,
                                              String startDateInvoiceDetail, Integer status, boolean deleted){
        ExpressionList expressionList = finder.query().where();

        if(invoice != null)
            expressionList.eq("invoice.idInvoice", invoice );

        if(itemType != null)
            expressionList.eq("itemType.idItemType", itemType );

        if(lot != null)
            expressionList.eq("lot.idLot", lot );

        if(store != null)
            expressionList.eq("store.idStore", store );

        if(nameReceivedInvoiceDetail != null)
            expressionList.eq("nameReceivedInvoiceDetail", nameReceivedInvoiceDetail );

        if(startDateInvoiceDetail != null)
            expressionList.eq("startDateInvoiceDetail", startDateInvoiceDetail );

        if(deleted)
            expressionList.setIncludeSoftDeletes();

        if(status != null)
            expressionList.eq("statusInvoiceDetail", status );

        return (List<InvoiceDetail>) expressionList.findList();
    }


    public static ListPagerCollection findAll(Integer index, Integer size, PathProperties pathProperties, String sort,
                                              Long invoice, Long itemType, Long lot, Long store, String nameReceivedInvoiceDetail,
                                              String startDateInvoiceDetail, Integer status, boolean deleted){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(invoice != 0L)
            expressionList.eq("invoice.idInvoice", invoice );

        if(itemType != 0L)
            expressionList.eq("itemType.idItemType", itemType );

        if(lot != 0L)
            expressionList.eq("lot.idLot", lot );

        if(store != 0L)
            expressionList.eq("store.idStore", store );

        if(nameReceivedInvoiceDetail != null)
            expressionList.eq("nameReceivedInvoiceDetail", nameReceivedInvoiceDetail );

        if(startDateInvoiceDetail != null)
            expressionList.eq("startDateInvoiceDetail", startDateInvoiceDetail );

        if(deleted)
            expressionList.setIncludeSoftDeletes();

        if(sort != null) {
            if(sort.contains(" ")) {
                String []  aux = sort.split(" ", 2);
                expressionList.orderBy(sort( aux[0], aux[1]));
            }else {
                expressionList.orderBy(sort("idInvoiceDetail", sort));
            }
        }

        if(status != null)
            expressionList.eq("statusInvoiceDetail", status );

        if(index == null || size == null)
            return new ListPagerCollection(expressionList.findList());


        return new ListPagerCollection(
                expressionList.setFirstRow(index).setMaxRows(size).findList(),
                expressionList.setFirstRow(index).setMaxRows(size).findCount(),
                index,
                size);
    }


}
