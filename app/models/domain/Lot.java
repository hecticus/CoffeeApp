package models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long idLot;

    @Constraints.Required
    @Column(nullable = false)
    private String NameLot;

    @Constraints.Required
    @Column(nullable = false)
    private String areaLot;


    @Constraints.Required
    @Column(nullable = false)
    private String farmLot;

    @Constraints.Required
    @Column(nullable = false)
    private Double heighLot;


    @OneToMany(mappedBy = "lot", cascade= CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails = new ArrayList<>();

    public String getName() {
        return NameLot;
    }

    public void setName(String name) {
        NameLot = name;
    }



    public Double getHeigh() {
        return heighLot;
    }

    public void setHeigh(Double heigh) {
        this.heighLot = heigh;
    }

    public String getFarm() {
        return farmLot;
    }

    public void setFarm(String farm) {
        this.farmLot = farm;
    }

    public String getArea() {
        return areaLot;
    }

    public void setArea(String area) {
        this.areaLot = area;
    }

    @JsonIgnore
    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public Long getId() {
        return idLot;
    }

    public void setId(Long idLot) {
        this.idLot = idLot;
    }
}
