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
import play.data.validation.Constraints;
import scala.reflect.internal.Trees;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 21/04/17.
 */
@Entity
@Table(name="providers")
public class Provider extends AbstractEntity{

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

    @Constraints.MaxLength(255)
    @Column(name = "photo_Provider")
    private String photoProvider;

    @ManyToOne(optional = false)
    //@JoinColumn(name = "id_providerType", nullable = false)
    @JsonBackReference
    private ProviderType providerType;

    @Constraints.Required
    @Column(nullable = false, name = "contactName_Provider")
    private String contactNameProvider;

    @Constraints.Required
    @Column(nullable = false, name = "status_Provider")
//    private boolean statusProvider;
    private Integer statusProvider = 1;


    @OneToMany(mappedBy = "provider", cascade= CascadeType.ALL)
    @JsonManagedReference
    private List<Invoice> invoices = new ArrayList<>();

    private static Finder<Long, Provider> finder = new Finder<>(Provider.class);


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

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }


    public static Provider findById(Long id) {
        return finder.byId(id);
    }

    public static boolean existName(String name_itemtype){
        if(finder.query().where().eq("name_itemtype",name_itemtype ).findUnique() != null ) return true;
        return false;
    }

    public static boolean existId(Long id) {
        if(InvoiceDetail.findById(id) != null ) return true;
        return false;
    }

    private static ProviderType providerTypeDao = new ProviderType();

    public static ListPagerCollection findAll(String name, Integer index, Integer size, String sort, PathProperties
                                                pathProperties, Integer all, Long idProviderType, Integer statusProvider){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(idProviderType != 0L)
            expressionList.eq("id_providerType", idProviderType);

        if(statusProvider != null)
            expressionList.eq("status_Provider", statusProvider);

//        if(sort != null)
//            expressionList.orderBy(sort(sort));


        expressionList.eq("status_delete",all);

        if(index == null || size == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(
                expressionList.setFirstRow(index).setMaxRows(size).findList(),
                expressionList.setFirstRow(index).setMaxRows(size).findCount(),
                index,
                size);
    }



    public ListPagerCollection findAllSearch(String name, Integer pageIndex, Integer pageSize, String sort, PathProperties
            pathProperties, boolean all, Integer listAll, boolean inside, Integer idProviderType) {

        ExpressionList expressionList;

        if(inside){
            if (all) expressionList = finder.query().where();
            else expressionList = finder.query().where().eq("status_delete", 0);
        }else{
            if(listAll.equals(1)){
                if(idProviderType.equals(-1)) expressionList = finder.query().where().eq("status_delete",0);
                else expressionList = finder.query().where().eq("status_delete",0).eq("id_providertype",idProviderType);
            }
            else  expressionList = finder.query().where().eq("status_delete",0).eq("status_provider",1);
        }

        if(pathProperties != null)
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.icontains("fullName_provider", name);

        if(sort != null)
            expressionList.orderBy(sort(sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(), expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findCount(), pageIndex, pageSize);
    }


    public static Provider getByIdentificationDoc(String IdentificationDoc){
        return finder.query().where().eq("identificationdoc_provider",IdentificationDoc).findUnique();
    }

    public List<Provider> getProvidersByName(String fullname_provider, String order){
        String sql = "select t0.id_provider prov_id, t0.identificationdoc_provider identification_doc," +
                " t0.fullname_provider full_name, " +
                "t0.address_provider address, t0.phonenumber_provider phone_number, t0.email_provider email," +
                " t0.photo_provider photo," +
                " t0.contactname_provider  contact_name, t0.id_providertype providerType" +
                " from providers t0" +
                " where  t0.status_delete=0"
                + " ";

        if(!fullname_provider.equals(""))     sql += " and  fullname_provider like '%"+fullname_provider+"%'  ";

        sql += "  order by fullname_provider "+order+"";


        List<SqlRow>  sqlRows = Ebean.createSqlQuery(sql)
                .findList();

        return toProviders(sqlRows);

    }

    public  List<Provider> getByNameDocByTypeProvider(String nameDoc,Long id_providertype, String order)
    {
        String sql = "select t0.id_provider prov_id, t0.identificationdoc_provider identification_doc," +
                " t0.fullname_provider full_name, " +
                "t0.address_provider address, t0.phonenumber_provider phone_number, t0.email_provider email," +
                " t0.photo_provider photo," +
                " t0.contactname_provider  contact_name, t0.id_providertype providerType" +
                " from providers t0" +
                " where id_providertype = :id_providertype and t0.status_delete=0 " +
                "and (identificationdoc_provider like '%"+nameDoc+"%' or fullname_provider like '%"+nameDoc+"%')"

                + "  order by fullname_provider  "+order+"";

        List<SqlRow>  sqlRows = Ebean.createSqlQuery(sql)
                .setParameter("id_providertype",id_providertype)
                .findList();

        return toProviders(sqlRows);
    }

    public List<Provider> toProviders(List<SqlRow>  sqlRows){
        List<Provider> providers = new ArrayList<>();
        Provider provider;

        for(int i=0; i < sqlRows.size(); ++i){
            provider = new Provider();

            provider.setContactNameProvider(sqlRows.get(i).getString("contact_name"));
            provider.setFullNameProvider(sqlRows.get(i).getString("full_name"));
            provider.setPhoneNumberProvider(sqlRows.get(i).getString("phone_number"));
            provider.setIdentificationDocProvider(sqlRows.get(i).getString("identification_doc"));
            provider.setAddressProvider(sqlRows.get(i).getString("address"));
            provider.setIdProvider(sqlRows.get(i).getLong("prov_id"));
            provider.setEmailProvider(sqlRows.get(i).getString("email"));
            provider.setPhotoProvider(sqlRows.get(i).getString("photo"));
            provider.setProviderType(providerTypeDao.findById(sqlRows.get(i).getLong("providerType")));

            providers.add(provider);
        }

        return providers;
    }


    public List<Integer> getExist(String identificationdoc_provider){
        List<Integer> aux = new ArrayList<Integer>();
        Provider provider = finder.query().where().eq("identificationdoc_provider",identificationdoc_provider).findUnique();
        if(provider==null) aux.add(0,-1);
        else
        {
            aux.add(0,provider.getStatusDelete());
            aux.add(1,Integer.parseInt(provider.getIdProvider().toString()));
        }
        return aux;
    }

    public static boolean existIdentfy(String identify){
        if(finder.query().where().eq("identificationdoc_provider", identify).findUnique() != null) return true;
        return false;
    }

    public String uploadPhoto(String base64Photo, String ext) {

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
