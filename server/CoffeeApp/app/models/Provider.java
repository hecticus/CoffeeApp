package models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.multimediaUtils.Multimedia;
import controllers.utils.ListPagerCollection;
import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.SqlRow;
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

//    @ManyToOne(optional = false)
//    @JsonBackReference
//    @JoinColumn(nullable = false)
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
    @Column(nullable = false, length = 60, unique = true)
    private String nameProvider;

    @Constraints.Required
    @Constraints.MaxLength(60)
    @Column(nullable = false,length = 60)
    private String addressProvider;

//    @Constraints.Required
    @Constraints.MaxLength(20)
    @Column( length = 20)
    private String numberProvider;

    @Constraints.Email
//    @Constraints.Required
    private String emailProvider;

    @Constraints.MaxLength(255)
    private String photoProvider;

//    @Constraints.Required
    @Constraints.MaxLength(50)
    @Column(nullable = false, length = 50)
    private String contactNameProvider;

    @ManyToOne
//    @JsonBackReference
//    @Constraints.Required
    private StatusProvider statusProvider;

    @OneToMany(mappedBy = "provider")
//    @JsonManagedReference
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

    public String getPhotoProvider() {
        return photoProvider;
    }

    public void setPhotoProvider(String photoProvider) {
        this.photoProvider = photoProvider;
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

    public static Provider findById(Long id) {
        return finder.byId(id);
    }

    public static Provider findByNit(String nitProvider) {
        return finder.query().where().eq("nitProvider", nitProvider).setIncludeSoftDeletes().findUnique();
    }

    public static Provider findByProvider(Provider provider) {
        return finder.query().where()
                .or()
                    .startsWith("nitProvider", provider.getNitProvider())
                    .startsWith("nameProvider", provider.getNameProvider())
                .setIncludeSoftDeletes().findUnique();
    }

    public static ListPagerCollection findAll( Integer index, Integer size, PathProperties pathProperties,
                                               String sort, String name,  Long idProviderType,
                                               String identificationDocProvider, String addressProvider,
                                               String phoneNumberProvider, String emailProvider,
                                               String contactNameProvider, Long status, boolean delete){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
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


//    public static String uploadPhoto(String base64Photo, String ext) {
//
//        ObjectMapper mapper = new ObjectMapper();
//        //    ArrayNode arrayNode = mapper.createArrayNode();
//        ObjectNode request = mapper.createObjectNode();
//
//        request.put("fileExtension", ext);
//        request.put("photo", base64Photo);
//        Multimedia multimedia = new Multimedia();
//
//
//        JsonNode result = multimedia.uploadPhoto(request);
//
//        return result.get("url").asText();
//
//    }

    public static String uploadPhoto(String base64Photo, String ext) {

        ObjectMapper mapper = new ObjectMapper();
        //    ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode request = mapper.createObjectNode();

        request.put("fileExtension", ext);
        request.put("photo", base64Photo);
        Multimedia multimedia = new Multimedia();


        JsonNode result = multimedia.uploadPhoto(request);

        return result.get("url").asText();
    }

}
