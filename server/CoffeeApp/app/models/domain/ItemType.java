package models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.AbstractEntity;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 25/04/17.
 */
@Entity
@Table(name="item_types")
public class ItemType extends AbstractEntity
{

    @Id
    @Column(name = "id_itemType")
    private Long idItemType;

    @Constraints.Required
    @Column(nullable = false, name = "name_itemType")
    private String nameItemType;

    @Constraints.Required
    @Column(nullable = false, columnDefinition = "Decimal(10,2)",name = "cost_itemType")
    private Float costItemType;

    @Constraints.Required
    @Column(nullable = false, name = "status_itemType")
    private Integer statusItemType=1;

    @ManyToOne
    @JoinColumn(name = "id_providerType", nullable = false)
    private ProviderType providerType;

    @ManyToOne
    @JoinColumn(name = "id_unit", nullable = false)
    private Unit unit;

    @OneToMany(mappedBy = "itemType", cascade= CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails = new ArrayList<>();

    public Long getIdItemType() {
        return idItemType;
    }

    public void setIdItemType(Long idItemType) {
        this.idItemType = idItemType;
    }

    public String getNameItemType() {
        return nameItemType;
    }

    public void setNameItemType(String nameItemType) {
        this.nameItemType = nameItemType;
    }

    public Float getCostItemType() {
        return costItemType;
    }

    public void setCostItemType(Float costItemType) {
        this.costItemType = costItemType;
    }

    public Integer getStatusItemType() {
        return statusItemType;
    }

    public void setStatusItemType(Integer statusItemType) {
        this.statusItemType = statusItemType;
    }

    public ProviderType getProviderType() {
        return providerType;
    }

    @JsonIgnore
    public void setProviderType(ProviderType providerType) {
        this.providerType = providerType;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @JsonIgnore
    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }
}
