package models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 12/05/17.
 */
@Entity
@Table(name="stores")
public class Store extends AbstractEntity
{


    @Id
    private Long idStore;
    @Constraints.Required
    @Column(nullable = false)
    private String NameStore;

    @Constraints.Required
    @Column(nullable = false)
    private Integer statusStore=1;

    @OneToMany(mappedBy = "store", cascade= CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails = new ArrayList<>();

    public Long getId() {
        return idStore;
    }

    public void setId(Long idStore) {
        this.idStore = idStore;
    }

    public String getName() {
        return NameStore;
    }

    public void setName(String nameStore) {
        NameStore = nameStore;
    }

    public Integer getStatus() {
        return statusStore;
    }

    public void setStatus(Integer statusStore) {
        this.statusStore = statusStore;
    }

    @JsonIgnore
    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }
}
