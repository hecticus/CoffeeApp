package com.hecticus.eleta.model_new;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.request.invoice.ItemPost;
import com.hecticus.eleta.model.request.invoice.PurityPost;

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
    private Float priceItemTypeByLot;
    private Float costItemType;
    private Float amountInvoiceDetail;
    private String nameReceived;
    private String nameDelivered;
    private Invoice invoice;
    private ItemType itemType;
    private Lot lot;
    private Store store;
    private List<PurityPost> purities;

    public InvoiceDetail() {
    }

    public InvoiceDetail(ItemPost itemPost, InvoicePost invoicePost) {
        //id = (long) itemPost.getInvoiceDetailId();
        noteInvoiceDetail = invoicePost.getObservations();
        priceItemTypeByLot = itemPost.getPrice();
        costItemType = itemPost.getAmount();
        amountInvoiceDetail= itemPost.getAmount();
        nameReceived = invoicePost.getReceiverName();
        nameDelivered = invoicePost.getDispatcherName();
        if(!invoicePost.getBuyOption()){
            itemType = new ItemType((long) itemPost.getItemTypeId());
            lot = null;//new Lot((long) invoicePost.getLot());
            store = null;//new Store((long) itemPost.getStoreId());
        }else{
            itemType = null;//new ItemType((long) itemPost.getItemTypeId());
            store = new Store((long) itemPost.getStoreId());
            lot = new Lot((long) invoicePost.getLot());
        }
        purities = itemPost.getPurities();
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

    public Float getPriceItemTypeByLot() {
        return priceItemTypeByLot;
    }

    public void setPriceItemTypeByLot(Float priceItemTypeByLot) {
        this.priceItemTypeByLot = priceItemTypeByLot;
    }

    public Float getCostItemType() {
        return costItemType;
    }

    public void setCostItemType(Float costItemType) {
        this.costItemType = costItemType;
    }

    public Float getAmountInvoiceDetail() {
        return amountInvoiceDetail;
    }

    public void setAmountInvoiceDetail(Float amountInvoiceDetail) {
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

    public List<PurityPost> getPurities() {
        return purities;
    }

    public void setPurities(List<PurityPost> purities) {
        this.purities = purities;
    }
}
