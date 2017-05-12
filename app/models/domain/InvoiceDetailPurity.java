package models.domain;

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
    private Long idInvoiceDetailPurity;

    @ManyToOne
    @JoinColumn(name = "id_purity")
    @Column(nullable = false)
    private Purity purity;

    @Constraints.Required
    @Column(nullable = false)
    private Integer valueRateInvoiceDetailPurity=0;

    @Constraints.Required
    @Column(nullable = false)
    private Integer totalDiscountPurity=0;


    @ManyToOne
    @JoinColumn(name = "id_invoiceDetail")
    private InvoiceDetail invoiceDetail;


    public Purity getPurity() {
        return purity;
    }

    public void setPurity(Purity purity) {
        this.purity = purity;
    }

    public Integer getTotalDiscountPurity() {
        return totalDiscountPurity;
    }

    public void setTotalDiscountPurity(Integer totalDiscountPurity) {
        this.totalDiscountPurity = totalDiscountPurity;
    }

    public Integer getValueRateInvoiceDetailPurity() {
        return valueRateInvoiceDetailPurity;
    }

    public void setValueRateInvoiceDetailPurity(Integer valueRateInvoiceDetailPurity) {
        this.valueRateInvoiceDetailPurity = valueRateInvoiceDetailPurity;
    }

    public InvoiceDetail getInvoiceDetail() {
        return invoiceDetail;
    }

    public void setInvoiceDetails(InvoiceDetail invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

    public Long getId() {
        return idInvoiceDetailPurity;
    }

    public void setId(Long idInvoiceDetailPurity) {
        this.idInvoiceDetailPurity = idInvoiceDetailPurity;
    }
}
