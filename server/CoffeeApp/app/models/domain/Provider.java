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
    @Column(name = "id_Provider")
    private Long idProvider;

    @Constraints.Required
    @Column(unique=true,nullable = false, name = "identificationDoc_Provider")
    private String identificationDocProvider;

    @Constraints.Required
    @Column(nullable = false, name = "fullName_Provider")
    private String fullNameProvider;

    @Constraints.Required
    @Column(nullable = false, name = "address_Provider")
    private String addressProvider;

    @Constraints.Required
    @Column(nullable = false, name = "phoneNumber_Provider")
    private String phoneNumberProvider;

    @Column(name = "email_Provider")
    private String emailProvider;

    @Column(name = "photo_Provider")
    private String photoProvider;



    @ManyToOne
    @JoinColumn(name = "id_providerType", nullable = false)
    private ProviderType providerType;

    @Constraints.Required
    @Column(nullable = false, name = "contactName_Provider")
    private String contactNameProvider;

    @Constraints.Required
    @Column(nullable = false, name = "status_Provider")
    private Integer statusProvider = 1;


    @OneToMany(mappedBy = "provider", cascade= CascadeType.ALL)
    private List<Invoice> invoices = new ArrayList<>();

    public Long getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Long idProvider) {
        this.idProvider = idProvider;
    }

    public String getIdentificationDocProvider() {
        return identificationDocProvider;
    }

    public void setIdentificationDocProvider(String identificationDocProvider) {
        this.identificationDocProvider = identificationDocProvider;
    }

    public String getFullNameProvider() {
        return fullNameProvider;
    }

    public void setFullNameProvider(String fullNameProvider) {
        this.fullNameProvider = fullNameProvider;
    }

    public String getAddressProvider() {
        return addressProvider;
    }

    public void setAddressProvider(String addressProvider) {
        this.addressProvider = addressProvider;
    }

    public String getPhoneNumberProvider() {
        return phoneNumberProvider;
    }

    public void setPhoneNumberProvider(String phoneNumberProvider) {
        this.phoneNumberProvider = phoneNumberProvider;
    }

    public String getEmailProvider() {
        return emailProvider;
    }

    public void setEmailProvider(String emailProvider) {
        this.emailProvider = emailProvider;
    }

    public String getPhotoProvider() {
        return photoProvider;
    }

    public void setPhotoProvider(String photoProvider) {
        this.photoProvider = photoProvider;
    }

    public ProviderType getProviderType() {
        return providerType;
    }

    public void setProviderType(ProviderType providerType) {
        this.providerType = providerType;
    }

    public String getContactNameProvider() {
        return contactNameProvider;
    }

    public void setContactNameProvider(String contactNameProvider) {
        this.contactNameProvider = contactNameProvider;
    }

    public Integer getStatusProvider() {
        return statusProvider;
    }

    public void setStatusProvider(Integer statusProvider) {
        this.statusProvider = statusProvider;
    }

    @JsonIgnore
    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }
}
