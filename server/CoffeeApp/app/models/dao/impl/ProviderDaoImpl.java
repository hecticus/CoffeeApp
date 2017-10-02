package models.dao.impl;

/**
 * Created by drocha on 25/04/17.
 */


import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.text.PathProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.dao.ProviderTypeDao;
import models.dao.utils.ListPagerCollection;
import models.domain.Provider;
import models.dao.ProviderDao;
import models.manager.multimediaUtils.Multimedia;
import models.manager.requestUtils.Request;

import java.util.ArrayList;
import java.util.List;

public class ProviderDaoImpl extends AbstractDaoImpl<Long, Provider> implements ProviderDao {

    private static ProviderTypeDao providerTypeDao = new ProviderTypeDaoImpl();
    public ProviderDaoImpl() {
        super(Provider.class);
    }

    public Provider getByIdentificationDoc(String IdentificationDoc)
    {
        return find.where().eq("identificationdoc_provider",IdentificationDoc).findUnique();
    }

    public List<Provider> getProvidersByName(String fullname_provider, String order)
    {
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

    public List<Provider> getByTypeProvider(Long id_providertype, String order)
    {
        String sql = "select t0.id_provider prov_id, t0.identificationdoc_provider identification_doc," +
                " t0.fullname_provider full_name, " +
                "t0.address_provider address, t0.phonenumber_provider phone_number, t0.email_provider email," +
                " t0.photo_provider photo," +
                " t0.contactname_provider  contact_name, t0.id_providertype providerType" +
                " from providers t0" +
                " where t0.status_delete=0 ";

        if(id_providertype!=-1) sql += "and id_providertype = :id_providertype ";

        sql +=  "  order by fullname_provider  "+order+"";


        List<SqlRow>  sqlRows = Ebean.createSqlQuery(sql)
                .setParameter("id_providertype",id_providertype)
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

    public List<Provider> toProviders(List<SqlRow>  sqlRows)
    {
        List<Provider> providers = new ArrayList<>();
        Provider provider;

        for(int i=0; i < sqlRows.size(); ++i)
        {
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


    public List<Integer> getExist(String identificationdoc_provider)
    {
        List<Integer> aux = new ArrayList<Integer>();
        Provider provider = find.where().eq("identificationdoc_provider",identificationdoc_provider).findUnique();
        if(provider==null) aux.add(0,-1);
        else
        {
            aux.add(0,provider.getStatusDelete());
            aux.add(1,Integer.parseInt(provider.getIdProvider().toString()));
        }
        return aux;
    }

    @Override
    public ListPagerCollection findAllSearch(String name, Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties, boolean all) {

        ExpressionList expressionList;
       if(all) expressionList = find.where();
       else  expressionList = find.where().eq("status_delete",0);

        if(pathProperties != null)
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.icontains("fullName_Provider", name);

        if(sort != null)
            expressionList.orderBy(AbstractDaoImpl.Sort(sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(expressionList.findPagedList(pageIndex, pageSize).getList(), expressionList.findRowCount(), pageIndex, pageSize);
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
