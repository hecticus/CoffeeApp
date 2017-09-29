package models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import models.manager.responseUtils.CustomDateTimeSerializer;
import org.joda.time.DateTime;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 26/04/17.
 */
@Entity
@Table(name="invoice_details")
public class InvoiceDetail  extends AbstractEntity
{


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


    @Column(columnDefinition = "date", nullable = false, name = "dueDate_invoiceDetail")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private DateTime startDateInvoiceDetail;

    @Constraints.Required
    @Column(name = "amount_invoiceDetail")
    private Integer amountInvoiceDetail;

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

    @JsonIgnore
    public DateTime getStartDateInvoiceDetail() {
        return startDateInvoiceDetail;
    }

    public void setStartDateInvoiceDetail(DateTime startDateInvoiceDetail) {
        this.startDateInvoiceDetail = startDateInvoiceDetail;
    }

    public Integer getAmountInvoiceDetail() {
        return amountInvoiceDetail;
    }

    public void setAmountInvoiceDetail(Integer amountInvoiceDetail) {
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


    public Double getCostItemType() {
        return costItemType;
    }

    public void setCostItemType(Double costItemType) {
        this.costItemType = costItemType;
    }
}
