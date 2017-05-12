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
@Table(name="invoices_details")
public class InvoiceDetail  extends AbstractEntity
{


    @Id
    private Long idInvoiceDetail;

    @ManyToOne
    @JoinColumn(name = "id_invoice")
    @Column(nullable = false)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "id_itemType")
    @Column(nullable = false)
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
    @Column(nullable = false, columnDefinition = "Decimal(10,2)")
    private Float costItemType;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @Column(columnDefinition = "datetime", nullable = false)
    private DateTime startDateInvoiceDetail;

    @Constraints.Required
    private Integer amountInvoiceDetail;

    private boolean freightInvoiceDetail=false;

    private String noteInvoiceDetail;


    @Constraints.Required
    @Column(nullable = false)
    private String nameReceivedInvoiceDetail;


    @Constraints.Required
    @Column(nullable = false)
    private String nameDeliveredInvoiceDetail;




    @JsonIgnore
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }



    public String getNameDelivered() {
        return nameDeliveredInvoiceDetail;
    }

    public void setNameDelivered(String nameDelivered) {
        this.nameDeliveredInvoiceDetail = nameDelivered;
    }

    public String getNameReceived() {
        return nameReceivedInvoiceDetail;
    }

    public void setNameReceived(String nameReceived) {
        this.nameReceivedInvoiceDetail = nameReceived;
    }

    public String getNote() {
        return noteInvoiceDetail;
    }

    public void setNote(String note) {
        this.noteInvoiceDetail = note;
    }

    public boolean isFreight() {
        return freightInvoiceDetail;
    }

    public void setFreight(boolean freight) {
        this.freightInvoiceDetail = freight;
    }

    public Integer getAmount() {
        return amountInvoiceDetail;
    }

    public void setAmount(Integer amount) {
        this.amountInvoiceDetail = amount;
    }

    public DateTime getStartDate() {
        return startDateInvoiceDetail;
    }

    public void setStartDate(DateTime startDate) {
        this.startDateInvoiceDetail = startDate;
    }

    public Float getCost() {
        return costItemType;
    }

    public void setCost(Float cost) {
        this.costItemType = cost;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    @JsonIgnore
    public List<InvoiceDetailPurity> getInvoiceDetailPurity() {
        return invoiceDetailPurity;
    }

    public void setInvoiceDetailPurity(List<InvoiceDetailPurity> invoiceDetailPurity) {
        this.invoiceDetailPurity = invoiceDetailPurity;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Long getId() {
        return idInvoiceDetail;
    }

    public void setId(Long idInvoiceDetail) {
        this.idInvoiceDetail = idInvoiceDetail;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
