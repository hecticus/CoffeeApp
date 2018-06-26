package models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.utils.ListPagerCollection;
import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.SqlRow;
import io.ebean.text.PathProperties;
import controllers.multimediaUtils.Multimedia;
import models.status.StatusProvider;
import org.apache.tools.ant.types.resources.Sort;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 21/04/17.
 * modify sm21 06/2018
 */
@Entity
@Table(name="providers")
public class Provider extends AbstractEntity{

    @ManyToOne(optional = false)
    @JsonBackReference
    @Constraints.Required
    @JoinColumn(nullable = false)
    private ProviderType providerType;

    @Constraints.Required
    @Constraints.MaxLength(255)
    @Column(unique=true, nullable = false)
    private String identificationDocProvider;

    @Constraints.Required
    @Constraints.MaxLength(60)
    @Column(nullable = false, length = 60)
    private String fullNameProvider;

    @Constraints.Required
    @Constraints.MaxLength(60)
    @Column(nullable = false,length = 60)
    private String addressProvider;

    @Constraints.Required
    @Constraints.MaxLength(20)
    @Column(nullable = false, length = 20)
    private String phoneNumberProvider;

    @Constraints.Email
    @Constraints.Required
    @Column( nullable = false)
    private String emailProvider;

    @Constraints.MaxLength(255)
    private String photoProvider;

    @Constraints.Required
    @Constraints.MaxLength(50)
    @Column(nullable = false, length = 50, unique = true)
    private String contactNameProvider;

    @ManyToOne
    private StatusProvider statusProvider;

    @OneToMany(mappedBy = "provider")
    @JsonManagedReference
    private List<Invoice> invoices;

    public static Finder<Long, Provider> finder = new Finder<>(Provider.class);

    public Provider() {
        invoices = new ArrayList<>();
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

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }


    public static Provider findById(Long id) {
        return finder.byId(id);
    }


    public static ListPagerCollection findAll( Integer index, Integer size, PathProperties pathProperties,
                                               String sort, String name,  Long idProviderType,
                                               String identificationDocProvider, String addressProvider,
                                               String phoneNumberProvider, String emailProvider,
                                               String contactNameProvider, Long status, boolean deleted){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(idProviderType != 0L)
            expressionList.eq("providerType.id", idProviderType);

        if(identificationDocProvider != null)
           expressionList.startsWith("identificationDocProvider", identificationDocProvider);

        if(name != null)
           expressionList.startsWith("fullNameProvider", name);

        if(addressProvider != null)
           expressionList.startsWith("addressProvider", addressProvider);

        if(phoneNumberProvider != null)
           expressionList.startsWith("phoneNumberProvider", phoneNumberProvider);

        if(emailProvider != null)
           expressionList.startsWith("emailProvider", emailProvider);

        if(contactNameProvider != null)
           expressionList.startsWith("contactNameProvider", contactNameProvider);

        if(sort != null)
            expressionList.orderBy(sort(sort));

        if(status != null)
            expressionList.eq("statusProvider", status);

        if( deleted )
            expressionList.setIncludeSoftDeletes();

        if(index == null || size == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(
                expressionList.setFirstRow(index).setMaxRows(size).findList(),
                expressionList.setFirstRow(index).setMaxRows(size).findCount(),
                index,
                size);
    }


/*    public static String uploadPhoto(String base64Photo, String ext) {

        ObjectMapper mapper = new ObjectMapper();
        //    ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode request = mapper.createObjectNode();

        request.put("fileExtension", ext);
        request.put("photo", base64Photo);
        Multimedia multimedia = new Multimedia();


        JsonNode result = multimedia.uploadPhoto(request);

        return result.get("url").asText();

    }*/
}
