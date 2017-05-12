package models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 21/04/17.
 */
@Entity
@Table(name="providers")
public class Provider extends AbstractEntity
{

    @Id
    private Long idProvider;

    @Constraints.Required
    @Column(nullable = false)
    private String identificationDocProvider;

    @Constraints.Required
    @Column(nullable = false)
    private String fullNameProvider;

    @Constraints.Required
    @Column(nullable = false)
    private String addressProvider;

    @Constraints.Required
    @Column(nullable = false)
    private String phoneNumberProvider;

    private String emailProvider;
    private String photoProvider;



    @ManyToOne
    @JoinColumn(name = "id_providerType")
    @Column(nullable = false)
    private ProviderType providerType;

    @Constraints.Required
    @Column(nullable = false)
    private String contactNameProvider;


    @OneToMany(mappedBy = "provider", cascade= CascadeType.ALL)
    private List<Invoice> invoices = new ArrayList<>();

    public String getIdentificationDoc() {
        return identificationDocProvider;
    }

    public void setIdentificationDoc(String identificationDoc) {
        this.identificationDocProvider = identificationDoc;
    }

    public String getContactName() {
        return contactNameProvider;
    }

    public void setContactName(String contactName) {
        this.contactNameProvider = contactName;
    }

    public ProviderType getProviderType() {
        return providerType;
    }

    public void setProviderType(ProviderType providerType) {
        this.providerType = providerType;
    }

    public String getPhoto() {
        return photoProvider;
    }

    public void setPhoto(String photo) {
        this.photoProvider = photo;
    }

    public String getEmail() {
        return emailProvider;
    }

    public void setEmail(String email) {
        this.emailProvider = email;
    }

    public String getPhoneNumber() {
        return phoneNumberProvider;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumberProvider = phoneNumber;
    }

    public String getAddress() {
        return addressProvider;
    }

    public void setAddress(String address) {
        this.addressProvider = address;
    }

    public String getFullName() {
        return fullNameProvider;
    }

    public void setFullName(String fullName) {
        this.fullNameProvider = fullName;
    }

    @JsonIgnore
    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Long getId() {
        return idProvider;
    }

    public void setId(Long idProvider) {
        this.idProvider = idProvider;
    }


}
