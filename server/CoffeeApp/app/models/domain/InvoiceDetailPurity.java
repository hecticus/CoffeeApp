package models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.AbstractEntity;
import play.data.validation.Constraints;

import javax.persistence.*;


/**
 * Created by drocha on 26/04/17.
 */
@Entity
@Table(name="invoicesdetails_purities")
public class InvoiceDetailPurity  extends AbstractEntity
{
    @Id
    @Column(name = "id_invoiceDetail_purity")
    private Long idInvoiceDetailPurity;

    @ManyToOne
    @JoinColumn(name = "id_purity", nullable = false)
    private Purity purity;

    @Constraints.Required
    @Column(nullable = false, name = "valueRate_invoiceDetail_purity")
    private Integer valueRateInvoiceDetailPurity=0;

    @Constraints.Required
    @Column(nullable = false, name = "totalDiscount_purity")
    private Integer totalDiscountPurity=0;

    @Constraints.Required
    @Column(nullable = false, name = "discountRate_purity")
    private Integer discountRatePurity=0;

    @ManyToOne
    @JoinColumn(name = "id_invoiceDetail")
    private InvoiceDetail invoiceDetail;


    @Constraints.Required
    @Column(nullable = false, name = "status__invoiceDetail_purity")
    private Integer statusInvoiceDetailPurity=1;

    public Long getIdInvoiceDetailPurity() {
        return idInvoiceDetailPurity;
    }

    public void setIdInvoiceDetailPurity(Long idInvoiceDetailPurity) {
        this.idInvoiceDetailPurity = idInvoiceDetailPurity;
    }

    public Purity getPurity() {
        return purity;
    }

    public void setPurity(Purity purity) {
        this.purity = purity;
    }

    public Integer getValueRateInvoiceDetailPurity() {
        return valueRateInvoiceDetailPurity;
    }

    public void setValueRateInvoiceDetailPurity(Integer valueRateInvoiceDetailPurity) {
        this.valueRateInvoiceDetailPurity = valueRateInvoiceDetailPurity;
    }

    public Integer getTotalDiscountPurity() {
        return totalDiscountPurity;
    }

    public void setTotalDiscountPurity(Integer totalDiscountPurity) {
        this.totalDiscountPurity = totalDiscountPurity;
    }

    public Integer getDiscountRatePurity() {
        return discountRatePurity;
    }

    public void setDiscountRatePurity(Integer discountRatePurity) {
        this.discountRatePurity = discountRatePurity;
    }

    @JsonIgnore
    public InvoiceDetail getInvoiceDetail() {
        return invoiceDetail;
    }

    public void setInvoiceDetail(InvoiceDetail invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

    public Integer getStatusInvoiceDetailPurity() {
        return statusInvoiceDetailPurity;
    }

    public void setStatusInvoiceDetailPurity(Integer statusInvoiceDetailPurity) {
        this.statusInvoiceDetailPurity = statusInvoiceDetailPurity;
    }
}
