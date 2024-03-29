package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import controllers.utils.ListPagerCollection;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.text.PathProperties;
import models.status.StatusProvider;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * modify sm21 06/2018
 */
@Entity
@Table(name="providers")
public class Provider extends AbstractEntity{

    @ManyToOne
    @Constraints.Required
    @JoinColumn(nullable = false)
    private ProviderType providerType;

    @Constraints.Required
    @Constraints.MaxLength(100)
    @Column(unique=true, nullable = false)
    private String nitProvider;

    @Constraints.Required
    @Constraints.MaxLength(60)
    @Column(nullable = false, length = 60)
    private String nameProvider;

    @Constraints.MaxLength(60)
    @Column(nullable = false,length = 60)
    private String addressProvider;

    @Constraints.MaxLength(20)
    @Column( length = 20)
    private String numberProvider;

    @Constraints.Email
    private String emailProvider;

    @Constraints.MaxLength(50)
    @Column( length = 50)
    private String contactNameProvider;

    @ManyToOne
    private StatusProvider statusProvider;

    @OneToOne(cascade = CascadeType.ALL)
    private Multimedia multimediaProfile;

    @OneToMany(mappedBy = "provider")
    @JsonIgnore
    private List<Invoice> invoices;

    public static Finder<Long, Provider> finder = new Finder<>(Provider.class);

    public Provider() {
        invoices = new ArrayList<>();
    }

    public ProviderType getProviderType() {
        return providerType;
    }

    public void setProviderType(ProviderType providerType) {
        this.providerType = providerType;
    }

    public String getNitProvider() {
        return nitProvider;
    }

    public void setNitProvider(String nitProvider) {
        this.nitProvider = nitProvider;
    }

    public String getNameProvider() {
        return nameProvider;
    }

    public void setNameProvider(String nameProvider) {
        this.nameProvider = nameProvider;
    }

    public Multimedia getMultimediaProfile() {
        return multimediaProfile;
    }

    public void setMultimediaProfile(Multimedia multimediaProfile) {
        this.multimediaProfile = multimediaProfile;
    }

    public String getAddressProvider() {
        return addressProvider;
    }

    public void setAddressProvider(String addressProvider) {
        this.addressProvider = addressProvider;
    }

    public String getNumberProvider() {
        return numberProvider;
    }

    public void setNumberProvider(String numberProvider) {
        this.numberProvider = numberProvider;
    }

    public String getEmailProvider() {
        return emailProvider;
    }

    public void setEmailProvider(String emailProvider) {
        this.emailProvider = emailProvider;
    }

    public String getContactNameProvider() {
        return contactNameProvider;
    }

    public void setContactNameProvider(String contactNameProvider) {
        this.contactNameProvider = contactNameProvider;
    }

    public StatusProvider getStatusProvider() {
        return statusProvider;
    }

    public void setStatusProvider(StatusProvider statusProvider) {
        this.statusProvider = statusProvider;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public static Provider findById(Long id){
        return finder.byId(id);
    }


    public static Provider findByIdAll(Long id){
        return finder.query().where().eq("id",id)
                .setIncludeSoftDeletes()
                .findUnique();
    }


    public static Provider findId(Long id, PathProperties pathProperties){

        if(pathProperties != null){
            return finder.query().apply(pathProperties).where()
                    .eq("id",id)
                    .setIncludeSoftDeletes()
                    .findUnique();
        }
//            return finder.query().apply(pathProperties).setId(id).findUnique();
        return finder.query().where().eq("id",id)
                .setIncludeSoftDeletes()
                .findUnique();
    }


    public static Provider findByNit(String nitProvider) {
        return finder.query().where()
                .eq("nitProvider", nitProvider)
                .setIncludeSoftDeletes().findUnique();
    }

    public static Provider findByMediaProfileId(Long mediaProfileId){
        return finder.query().where().eq("mediaProfile.id", mediaProfileId).findUnique();
    }

    public static Provider findByProvider(Provider provider) {
        Integer idProviderType = provider.getProviderType().getId().intValue();
        if( idProviderType == 1) {
            return finder.query().where()
                    .eq("providerType.id", 1)
                    .or()
                        .eq("nitProvider", provider.getNitProvider())
                        .eq("nameProvider", provider.getNameProvider())
                    .setIncludeSoftDeletes()
                    .findUnique();
        }
        
        return finder.query().where()
                .eq("providerType.id", idProviderType)
                .eq("nitProvider", provider.getNitProvider())
                .setIncludeSoftDeletes()
                .findUnique();

    }

    public static Provider findByName(String provider) {

        return finder.query().where()
                .eq("nameProvider", provider)
                .eq("providerType.id", 1)
                .findUnique();
    }


    public static ListPagerCollection findAll( Integer index, Integer size, PathProperties pathProperties,
                                               String sort, String name,  Long idProviderType,
                                               String identificationDocProvider, String addressProvider,
                                               String phoneNumberProvider, String emailProvider,
                                               String contactNameProvider, Long status, boolean delete){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null)
            expressionList.apply(pathProperties);

        if(idProviderType != 0L)
            expressionList.eq("providerType.id", idProviderType);

        if(identificationDocProvider != null)
           expressionList.startsWith("nitProvider", identificationDocProvider);

        if(name != null)
           expressionList.startsWith("nameProvider", name);

        if(addressProvider != null)
           expressionList.startsWith("addressProvider", addressProvider);

        if(phoneNumberProvider != null)
           expressionList.startsWith("numberProvider", phoneNumberProvider);

        if(emailProvider != null)
           expressionList.startsWith("emailProvider", emailProvider);

        if(contactNameProvider != null)
           expressionList.startsWith("contactNameProvider", contactNameProvider);

        if(sort != null)
            expressionList.orderBy(sort(sort));

        if(status != 0L)
            expressionList.eq("statusProvider.id", status);

        if( delete)
            expressionList.setIncludeSoftDeletes();

        if(index == null || size == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(
                expressionList.setFirstRow(index).setMaxRows(size).findList(),
                expressionList.setFirstRow(index).setMaxRows(size).findCount(),
                index,
                size);
    }

}
