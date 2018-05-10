package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.utils.ListPagerCollection;
import io.ebean.*;
import io.ebean.text.PathProperties;
import models.responseUtils.CustomDateTimeSerializer;
import org.joda.time.DateTime;
import play.data.validation.Constraints;
import play.libs.Json;

import javax.persistence.*;
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
    @JoinColumn(name = "id_invoice", nullable = false)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "id_itemType", nullable = false)
    private ItemType itemType;

    @OneToMany(mappedBy = "invoiceDetail", cascade= CascadeType.ALL)
    private List<InvoiceDetailPurity> invoiceDetailPurity = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_lot")
    private Lot lot;

    @ManyToOne
    @JoinColumn(name = "id_store")
    private Store store;

    @Constraints.Required
    @Column(nullable = false, columnDefinition = "Decimal(10,2)", name = "price_ItemTypeByLot")
    private Float priceItemTypeByLot;

    @Constraints.Required
    @Column(nullable = false, columnDefinition = "Decimal(10,2)", name = "cost_ItemType")
    private Double costItemType = 0.0;


    @Column( nullable = false, name = "dueDate_invoiceDetail")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private DateTime startDateInvoiceDetail;

    @Constraints.Required
    @Column(nullable = false, columnDefinition = "FLOAT(10,2)", name = "amount_invoiceDetail")
    //@Column(name = "amount_invoiceDetail")
    private Float amountInvoiceDetail;

    @Column(name = "isFreight_invoiceDetail")
    private boolean freightInvoiceDetail=false;

    @Column(name = "note_invoiceDetail")
    private String noteInvoiceDetail;


    @Constraints.Required
    @Column(nullable = false, name = "nameReceived_invoiceDetail")
    private String nameReceivedInvoiceDetail;


    @Constraints.Required
    @Column(nullable = false, name = "nameDelivered_invoiceDetail")
    private String nameDeliveredInvoiceDetail;


    @Constraints.Required
    @Column(nullable = false, name = "status_invoiceDetail")
    private Integer statusInvoiceDetail=1;

        private static Finder<Long, InvoiceDetail> finder = new Finder<>(InvoiceDetail.class);

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

    public Float getPriceItemTypeByLot() {
        return priceItemTypeByLot;
    }

    public void setPriceItemTypeByLot(Float priceItemTypeByLot) {
        this.priceItemTypeByLot = priceItemTypeByLot;
    }

    public Double getCostItemType() {
        return costItemType;
    }

    public void setCostItemType(Double costItemType) {
        this.costItemType = costItemType;
    }

    public DateTime getStartDateInvoiceDetail() {
        return startDateInvoiceDetail;
    }

    public void setStartDateInvoiceDetail(DateTime startDateInvoiceDetail) {
        this.startDateInvoiceDetail = startDateInvoiceDetail;
    }

    public Float getAmountInvoiceDetail() {
        return amountInvoiceDetail;
    }

    public void setAmountInvoiceDetail(Float amountInvoiceDetail) {
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



    public List<InvoiceDetail> finderAllByIdInvoice(Long IdInvoice)
    {
        return finder.query()
                .where()
                .eq("id_invoice", IdInvoice)
                .eq("status_delete",0)
                .orderBy("duedate_invoicedetail")
                .findList();

    }

    public    List<InvoiceDetail> getOpenByItemTypeId( Long idItemType)
    {
        return finder.query().where().eq("id_itemtype",idItemType).eq("status_delete",0).findList();
    }

    public    List<InvoiceDetail> getOpenByLotId( Long idLot)
    {
        return finder.query().where().eq("id_lot",idLot).eq("status_delete",0).findList();
    }

    public    List<InvoiceDetail> getOpenByStoreId( Long idStore)
    {
        return finder.query().where().eq("id_store",idStore).eq("status_delete",0).findList();
    }


    public ListPagerCollection finderAllSearch(Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties) {
        ExpressionList expressionList = finder.query().where().eq("status_delete",0);

        if(pathProperties != null)
            expressionList.apply(pathProperties);


        if(sort != null)
            expressionList.orderBy(AbstractEntity.sort(sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(), expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findCount(), pageIndex, pageSize);
    }


    public JsonNode finderAllByIdInvoiceSummary(Long idInvoice)
    {
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



        for(int i=0; i < results.size(); ++i)
        {
            aux= Json.newObject();

            aux.put("startDateInvoiceDetail",results.get(i).getTimestamp("c0").toString());
            aux.put("amountTotal",results.get(i).getString("c1"));

            result.add(aux);

        }



        return Json.toJson(result);
    }


    public int deleteAllByIdInvoiceAndDate(Long idInvoice, String date)
    {

        date = "'"+date+"'";

        String sql="update invoice_details " +
                "set status_delete=1 " +
                "where duedate_invoicedetail= "+date+"  and id_invoice= "+idInvoice+";";

        SqlUpdate query = Ebean.createSqlUpdate(sql);

        return query.execute();

    }



}
