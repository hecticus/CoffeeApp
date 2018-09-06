package com.hecticus.eleta.model_new;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.request.invoice.ItemPost;
import com.hecticus.eleta.model.request.invoice.PurityPost;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.util.Util;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityReference(alwaysAsId = true)
public class InvoiceDetail {

    private Long id;
    private Boolean deleted;
    private String start;
    private String updatedAt;
    private String noteInvoiceDetail;
    private Float price;
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



    public InvoiceDetail(InvoiceDetails invoiceDetails) {
        this.id = (long) invoiceDetails.getId();
        this.noteInvoiceDetail = invoiceDetails.getObservation();
        this.price = invoiceDetails.getPriceByLot();
        this.costItemType = invoiceDetails.getPriceItem();
        this.nameReceived = invoiceDetails.getReceiverName();
        this.nameDelivered = invoiceDetails.getDispatcherName();
        this.start = invoiceDetails.getStartDate();
        try {
            this.itemType = new ItemType((long) invoiceDetails.getItemType().getId());
        }catch (Exception e){
            this.itemType = new ItemType((long) invoiceDetails.getItemTypeId());
        }
        this.amountInvoiceDetail = invoiceDetails.getAmount();
        if(invoiceDetails.getLotId()!=-1) {
            try {
                this.lot = new Lot((long) invoiceDetails.getLot().getId());
            }catch (Exception e){
                this.lot = new Lot((long) invoiceDetails.getLotId());
            }
            //this.lot = new Lot((long) invoiceDetails.getLot().getId());
        }else {
            try {
                this.store = new Store((long) invoiceDetails.getStore().getId());
            }catch (Exception e){
                this.store = new Store((long) invoiceDetails.getStoreId());
            }
            //this.store = new Store((long) invoiceDetails.getStore().getId());
        }
        this.invoice = new Invoice((long) invoiceDetails.getInvoiceId());
        this.purities = new ArrayList<>();
        try {
            for (int i = 0; i < invoiceDetails.getDetailPurities().size(); i++) {
                purities.add(new PurityPost(invoiceDetails.getDetailPurities().get(i).getId(),
                        invoiceDetails.getDetailPurities().get(i).getRateValue()));
            }
        }catch (Exception e){
        }

    }

    public InvoiceDetail(InvoiceDetails invoiceDetails, InvoicePost invoicePost) {
        this.id = (long) invoiceDetails.getId();
        this.noteInvoiceDetail = invoiceDetails.getObservation();
        this.price = invoiceDetails.getPriceByLot();
        this.costItemType = invoiceDetails.getPriceItem();
        this.nameReceived = invoiceDetails.getReceiverName();
        this.nameDelivered = invoiceDetails.getDispatcherName();
        this.itemType = new ItemType((long)invoiceDetails.getItemTypeId());
        for(int i=0; i<invoicePost.getItems().size(); i++) {
            if(invoicePost.getItems().get(i).getItemTypeId()==invoiceDetails.getItemTypeId()){
                this.amountInvoiceDetail = invoicePost.getItems().get(i).getAmount();
            }
        }
        if(invoiceDetails.getLotId()!=-1) {
            this.lot = new Lot((long) invoiceDetails.getLotId());
        }else {
            this.store = new Store((long) invoiceDetails.getStoreId());
        }
        this.invoice = new Invoice((long) invoiceDetails.getInvoiceId());
        //this.purities = invoiceDetails.getDetailPurities();

    }

    public InvoiceDetail(ItemPost itemPost, InvoicePost invoicePost) {
        //id = Long.valueOf(itemPost.getInvoiceDetailId());
        noteInvoiceDetail = invoicePost.getObservations();
        price = itemPost.getPrice();
        costItemType = itemPost.getAmount();
        amountInvoiceDetail= itemPost.getAmount();
        nameReceived = invoicePost.getReceiverName();
        nameDelivered = invoicePost.getDispatcherName();
        this.start = invoicePost.getStartDate();
        Log.d("DEBUG", "currentDate invoiceDeatails" + start);
        if(!invoicePost.getBuyOption()){
            itemType = new ItemType((long) itemPost.getItemTypeId());
            lot = null;//new Lot((long) invoicePost.getLot());
            store = new Store((long) itemPost.getStoreId());
        }else{ //cosecha
            itemType = new ItemType((long) itemPost.getItemTypeId());
            store = null;//new Store((long) itemPost.getStoreId());
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
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
        return price;
    }

    public void setPriceItemTypeByLot(Float priceItemTypeByLot) {
        this.price = priceItemTypeByLot;
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
