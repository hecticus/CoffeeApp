package com.hecticus.eleta.model_new;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityReference(alwaysAsId = true)
public class InvoiceDetail {

    private Long id;
    private Boolean deleted;
    private String createdAt;
    private String updatedAt;
    private String noteInvoiceDetail;
    private Integer priceItemTypeByLot;
    private Integer costItemType;
    private Integer amountInvoiceDetail;
    private String nameReceived;
    private String nameDelivered;
    private Invoice invoice;
    private ItemType itemType;
    private Lot lot;
    private Store store;
    private InvoiceDetail invoiceDetail;
    private List<InvoiceDetailPurity> invoiceDetailPurity;

    public InvoiceDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getNoteInvoiceDetail() {
        return noteInvoiceDetail;
    }

    public void setNoteInvoiceDetail(String noteInvoiceDetail) {
        this.noteInvoiceDetail = noteInvoiceDetail;
    }

    public Integer getPriceItemTypeByLot() {
        return priceItemTypeByLot;
    }

    public void setPriceItemTypeByLot(Integer priceItemTypeByLot) {
        this.priceItemTypeByLot = priceItemTypeByLot;
    }

    public Integer getCostItemType() {
        return costItemType;
    }

    public void setCostItemType(Integer costItemType) {
        this.costItemType = costItemType;
    }

    public Integer getAmountInvoiceDetail() {
        return amountInvoiceDetail;
    }

    public void setAmountInvoiceDetail(Integer amountInvoiceDetail) {
        this.amountInvoiceDetail = amountInvoiceDetail;
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

    public InvoiceDetail getInvoiceDetail() {
        return invoiceDetail;
    }

    public void setInvoiceDetail(InvoiceDetail invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

    public List<InvoiceDetailPurity> getInvoiceDetailPurity() {
        return invoiceDetailPurity;
    }

    public void setInvoiceDetailPurity(List<InvoiceDetailPurity> invoiceDetailPurity) {
        this.invoiceDetailPurity = invoiceDetailPurity;
    }
}
