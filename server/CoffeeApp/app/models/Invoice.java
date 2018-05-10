package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import models.domain.InvoiceDetail;
import models.domain.Provider;
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
public class Invoice extends AbstractEntity{

    @Id
    @Column(name = "id_invoice")
    private Long idInvoice;

    @ManyToOne
    @JoinColumn(name = "id_provider", nullable = false)
    private Provider provider;

    @Constraints.Required
    @Column(nullable = false, name = "status_invoice")
    private Integer statusInvoice=1;

    @Column( nullable = false, name = "dueDate_invoice")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private DateTime startDateInvoice;



    @Column( nullable = false,name = "closedDate_invoice")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private DateTime closedDateInvoice;


    @OneToMany(mappedBy = "invoice", cascade= CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails = new ArrayList<>();

    @Column(name = "total_invoice")
    private Double totalInvoice=0.00;

    public Long getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(Long idInvoice) {
        this.idInvoice = idInvoice;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Integer getStatusInvoice() {
        return statusInvoice;
    }

    public void setStatusInvoice(Integer statusInvoice) {
        this.statusInvoice = statusInvoice;
    }

    @JsonIgnore
    public DateTime getStartDateInvoice() {
        return startDateInvoice;
    }

    public void setStartDateInvoice(DateTime startDateInvoice) {
        this.startDateInvoice = startDateInvoice;
    }

    @JsonIgnore
    public DateTime getClosedDateInvoice() {
        return closedDateInvoice;
    }

    public void setClosedDateInvoice(DateTime closedDateInvoice) {
        this.closedDateInvoice = closedDateInvoice;
    }

    @JsonIgnore
    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public Double getTotalInvoice() {
        return totalInvoice;
    }

    public void setTotalInvoice(Double totalInvoice) {
        this.totalInvoice = totalInvoice;
    }
}
