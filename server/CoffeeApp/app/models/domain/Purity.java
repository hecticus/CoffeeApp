package models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.AbstractEntity;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 26/04/17.
 */
@Entity
@Table(name="purities")
public class Purity extends AbstractEntity
{


    @Id
    @Column(name = "id_purity")
    private Long idPurity;

    @Constraints.Required
    @Column(nullable = false, name = "name_purity")
    private String NamePurity;

    @Constraints.Required
    @Column(nullable = false, name = "status_purity")
    private Integer statusPurity=1;

    @Constraints.Required
    @Column(nullable = false, name = "discountRate_purity")
    private Integer DiscountRatePurity=0;


    @OneToMany(mappedBy = "purity", cascade= CascadeType.ALL)
    private List<InvoiceDetailPurity> invoiceDetailPurities = new ArrayList<>();

    public Long getIdPurity() {
        return idPurity;
    }

    public void setIdPurity(Long idPurity) {
        this.idPurity = idPurity;
    }

    public String getNamePurity() {
        return NamePurity;
    }

    public void setNamePurity(String namePurity) {
        NamePurity = namePurity;
    }

    public Integer getStatusPurity() {
        return statusPurity;
    }

    public void setStatusPurity(Integer statusPurity) {
        this.statusPurity = statusPurity;
    }

    public Integer getDiscountRatePurity() {
        return DiscountRatePurity;
    }

    public void setDiscountRatePurity(Integer discountRatePurity) {
        DiscountRatePurity = discountRatePurity;
    }

    @JsonIgnore
    public List<InvoiceDetailPurity> getInvoiceDetailPurities() {
        return invoiceDetailPurities;
    }

    public void setInvoiceDetailPurities(List<InvoiceDetailPurity> invoiceDetailPurities) {
        this.invoiceDetailPurities = invoiceDetailPurities;
    }
}
