package models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long idItemType;

    @Constraints.Required
    @Column(nullable = false)
    private String nameItemType;

    @Constraints.Required
    @Column(nullable = false, columnDefinition = "Decimal(10,2)")
    private Float costItemType;

    @Constraints.Required
    @Column(nullable = false)
    private Integer statusItemType;

    @ManyToOne
    @JoinColumn(name = "id_providerType")
    @Column(nullable = false)
    private ProviderType providerType;

    @ManyToOne
    @JoinColumn(name = "id_unit")
    @Column(nullable = false)
    private Unit unit;



    @OneToMany(mappedBy = "itemType", cascade= CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails = new ArrayList<>();

    public String getName() {
        return nameItemType;
    }

    public void setName(String name) {
        this.nameItemType = name;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public ProviderType getProviderType() {
        return providerType;
    }

    public void setProviderType(ProviderType providerType) {
        this.providerType = providerType;
    }

    public Integer getStatus() {
        return statusItemType;
    }

    public void setStatus(Integer status) {
        this.statusItemType = status;
    }

    public Float getCost() {
        return costItemType;
    }

    public void setCost(Float cost) {
        this.costItemType = cost;
    }

    @JsonIgnore
    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public Long getId() {
        return idItemType;
    }

    public void setId(Long idItemType) {
        this.idItemType = idItemType;
    }
}
