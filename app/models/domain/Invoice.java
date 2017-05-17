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
 * Created by drocha on 25/04/17.
 */
@Entity
@Table(name="invoices")
public class Invoice extends AbstractEntity
{

    @Id
    private Long idInvoice;

    @ManyToOne
    @JoinColumn(name = "id_provider")
    @Column(nullable = false)
    private Provider provider;

    @Constraints.Required
    @Column(nullable = false)
    private Integer statusInvoice=1;

    @Column(columnDefinition = "date", nullable = false)
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private DateTime startDateInvoice;



    @Column(columnDefinition = "date", nullable = false)
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private DateTime closedDateInvoice;


    @OneToMany(mappedBy = "invoice", cascade= CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails = new ArrayList<>();

    private Double totalInvoice=0.00;




    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Double getTotal() {
        return totalInvoice;
    }

    public void setTotal(Double total) {
        this.totalInvoice = total;
    }

    @JsonIgnore
    public DateTime getClosedDate() {
        return closedDateInvoice;
    }

    public void setClosedDate(DateTime closedDate) {
        this.closedDateInvoice = closedDate;
    }

    @JsonIgnore
    public DateTime getStartDate() {
        return startDateInvoice;
    }

    public void setStartDate(DateTime startDate) {
        this.startDateInvoice = startDate;
    }

    public Integer getStatus() {
        return statusInvoice;
    }

    public void setStatus(Integer status) {
        this.statusInvoice = status;
    }

    @JsonIgnore
    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public Long getId() {
        return idInvoice;
    }

    public void setId(Long IdInvoice) {
        this.idInvoice = IdInvoice;
    }
}
