package models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.AbstractEntity;
import models.Farm;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 26/04/17.
 */
@Entity
@Table(name="lots")
public class Lot extends AbstractEntity
{
    @Id
    @Column(name = "id_lot")
    private Long idLot;

    @Constraints.Required
    @Column(nullable = false, name = "name_lot")
    private String nameLot;

    @Constraints.Required
    @Column(nullable = false, name = "area_lot")
    private String areaLot;

    @Constraints.Required
    @Column(nullable = false, name = "heigh_lot")
    private Double heighLot;

    @Constraints.Required
    @Column(nullable = false, name = "status_lot")
    private Integer statusLot=1;

    @ManyToOne
    @JoinColumn(name = "id_farm", nullable = false)
    private Farm farm;

    @Constraints.Required
    @Column(nullable = false, columnDefinition = "Decimal(10,2)",name = "price_lot")
    private Float priceLot;

    @OneToMany(mappedBy = "lot", cascade= CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails = new ArrayList<>();

    @JsonIgnore
    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public Long getIdLot() {
        return idLot;
    }

    public void setIdLot(Long idLot) {
        this.idLot = idLot;
    }

    public String getNameLot() {
        return nameLot;
    }

    public void setNameLot(String nameLot) {
        this.nameLot = nameLot;
    }

    public String getAreaLot() {
        return areaLot;
    }

    public void setAreaLot(String areaLot) {
        this.areaLot = areaLot;
    }

    public Double getHeighLot() {
        return heighLot;
    }

    public void setHeighLot(Double heighLot) {
        this.heighLot = heighLot;
    }

    public Integer getStatusLot() {
        return statusLot;
    }

    public void setStatusLot(Integer statusLot) {
        this.statusLot = statusLot;
    }

    public Float getPrice_lot() {
        return priceLot;
    }

    public void setPrice_lot(Float priceLot) {
        this.priceLot = priceLot;
    }
}
