package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import controllers.utils.ListPagerCollection;
import io.ebean.*;
import io.ebean.text.PathProperties;
import models.status.StatusInvoiceDetail;
import play.data.validation.Constraints;


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

    @ManyToOne
    @Constraints.Required
    @JoinColumn(nullable = false)
    private Invoice invoice;

    @ManyToOne
    @Constraints.Required
    @JoinColumn(nullable = false)
    private ItemType itemType;

    @ManyToOne
    @Constraints.Required
    @JoinColumn(nullable = false)
    private Lot lot;

    @ManyToOne
    @Constraints.Required
    @JoinColumn(nullable = false)
    private Store store;

    @Constraints.Min(0)
    @Constraints.Required
    @Column(nullable = false)
    private BigDecimal priceItemTypeByLot; //esta en lote desconozco la relacion Toma de Cosecha

    @Constraints.Required
    @Constraints.Min(0)
    @Column(nullable = false)
    private BigDecimal costItemType;//esta en Item Type desconozco la relacion Toma del app para vender

    @Constraints.Required
    @Constraints.Min(0)
    @Column(nullable = false)
    private BigDecimal amountInvoiceDetail; // cantidad por la que multiplica   era amount

    @Constraints.Required
    @Constraints.MaxLength(100)
    @Column(nullable = false, length = 100)
    private String nameReceived;

    @Constraints.Required
    @Constraints.MaxLength(100)
    @Column(nullable = false, length = 100)
    private String nameDelivered;

    @Column(columnDefinition = "text")
    private String note;

    @ManyToOne
    @JsonBackReference
    private StatusInvoiceDetail statusInvoiceDetail;

    @OneToMany(mappedBy = "invoiceDetail", cascade= CascadeType.ALL)
    private List<InvoiceDetailPurity> invoiceDetailPurity;

    private static Finder<Long, InvoiceDetail> finder = new Finder<>(InvoiceDetail.class);

    public InvoiceDetail() {
        costItemType = BigDecimal.ZERO;
        priceItemTypeByLot = BigDecimal.ZERO;
        invoiceDetailPurity = new ArrayList<>();
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

    public BigDecimal getAmountInvoiceDetail() {
        return amountInvoiceDetail;
    }

    public void setAmountInvoiceDetail(BigDecimal amountInvoiceDetail) {
        this.amountInvoiceDetail = amountInvoiceDetail;
    }

    public String getNoteInvoiceDetail() {
        return note;
    }

    public void setNoteInvoiceDetail(String noteInvoiceDetail) {
        this.note = noteInvoiceDetail;
    }

    public String getNameReceived() {
        return nameReceived;
    }

    public void setNameReceived(String nameReceived) {
        this.nameReceived = nameReceived;
    }

    public String getNameDelivered() {
        return nameDelivered;
    }

    public void setNameDelivered(String nameDelivered) {
        this.nameDelivered = nameDelivered;
    }

    public StatusInvoiceDetail getStatusInvoiceDetail() {
        return statusInvoiceDetail;
    }

    public void setStatusInvoiceDetail(StatusInvoiceDetail statusInvoiceDetail) {
        this.statusInvoiceDetail = statusInvoiceDetail;
    }

    public BigDecimal getPriceItemTypeByLot() {
        return priceItemTypeByLot;
    }

    public void setPriceItemTypeByLot(BigDecimal priceItemTypeByLot) {
        this.priceItemTypeByLot = priceItemTypeByLot;
    }

    public StatusInvoiceDetail getInvoiceDetail() {
        return statusInvoiceDetail;
    }

    public void setInvoiceDetail(StatusInvoiceDetail invoiceDetail) {
        this.statusInvoiceDetail = invoiceDetail;
    }

    //Metodos Creados
    public static InvoiceDetail findById(Long id){
        return finder.byId(id);
    }

    public static ListPagerCollection findAll(Integer index, Integer size, PathProperties pathProperties, String sort,
                                              Long invoice, Long itemType, Long lot, Long store, String nameReceived,
                                              String nameDelivered, String startDate, Long status, boolean delete){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(invoice != 0L)
            expressionList.eq("invoice.id", invoice );

        if(itemType != 0L)
            expressionList.eq("itemType.id", itemType );

        if(lot != 0L)
            expressionList.eq("lot.id", lot );

        if(store != 0L)
            expressionList.eq("store.id", store );

        if(nameReceived != null)
            expressionList.startsWith("nameReceived", nameReceived );

        if(nameDelivered != null)
            expressionList.startsWith("nameDelivered", nameDelivered);

        if(startDate != null)
            expressionList.startsWith("createdAt", startDate );

        if(delete)
            expressionList.setIncludeSoftDeletes();

        if(sort != null)
            expressionList.orderBy(sort(sort));

        if(status != 0L)
            expressionList.eq("statusInvoiceDetail.id", status );

        if(index == null || size == null)
            return new ListPagerCollection(expressionList.findList());

        return new ListPagerCollection(
                expressionList.setFirstRow(index).setMaxRows(size).findList(),
                expressionList.setFirstRow(index).setMaxRows(size).findCount(),
                index,
                size);
    }


}
