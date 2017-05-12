package models.domain;

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
    private Long idPurity;
    @Constraints.Required
    @Column(nullable = false)
    private String NamePurity;

    @Constraints.Required
    @Column(nullable = false)
    private Integer statusPurity=1;

    @Constraints.Required
    @Column(nullable = false)
    private Integer DiscountRatePurity=0;


    @OneToMany(mappedBy = "purity", cascade= CascadeType.ALL)
    private List<InvoiceDetailPurity> invoiceDetailPurities = new ArrayList<>();



    public String getName() {
        return NamePurity;
    }

    public void setName(String name) {
        NamePurity = name;
    }


    public Integer getDiscountRate() {
        return DiscountRatePurity;
    }

    public void setDiscountRate(Integer discountRate) {
        DiscountRatePurity = discountRate;
    }

    public Integer getStatus() {
        return statusPurity;
    }

    public void setStatus(Integer status) {
        this.statusPurity = status;
    }

    public List<InvoiceDetailPurity> getInvoiceDetailPurities() {
        return invoiceDetailPurities;
    }

    public void setInvoiceDetailPurities(List<InvoiceDetailPurity> invoiceDetailPurities) {
        this.invoiceDetailPurities = invoiceDetailPurities;
    }

    public Long getId() {
        return idPurity;
    }

    public void setId(Long idPurity) {
        this.idPurity = idPurity;
    }
}
