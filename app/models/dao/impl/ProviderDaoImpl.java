package models.dao.impl;

/**
 * Created by drocha on 25/04/17.
 */


import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import models.dao.ProviderTypeDao;
import models.domain.Provider;
import models.dao.ProviderDao;
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
        return find.where().eq("identification_doc_provider",IdentificationDoc).findUnique();
    }

    public List<Provider> getProvidersByName(String full_name_provider, String order)
    {
      String sql = "select t0.id_provider prov_id, t0.identification_doc_provider identification_doc," +
              " t0.full_name_provider full_name, " +
              "t0.address_provider address, t0.phone_number_provider phone_number, t0.email_provider email," +
              " t0.photo_provider photo," +
              " t0.contact_name_provider contact_name, t0.id_providerType providerType" +
              " from providers t0" +
              " where  t0.status_delete=0"
              + " ";

        if(!full_name_provider.equals(""))     sql += " and  full_name_provider like '%"+full_name_provider+"%'  ";

        sql += "  order by full_name_provider "+order+"";


        List<SqlRow>  sqlRows = Ebean.createSqlQuery(sql)
                .findList();



        return toProviders(sqlRows);

    }

    public List<Provider> getByTypeProvider(Long id_providertype, String order)
    {
        String sql = "select t0.id_provider prov_id, t0.identification_doc_provider identification_doc," +
                " t0.full_name_provider full_name, " +
                "t0.address_provider address, t0.phone_number_provider phone_number, t0.email_provider email," +
                " t0.photo_provider photo," +
                " t0.contact_name_provider contact_name, t0.id_providerType providerType" +
                " from providers t0" +
                " where t0.status_delete=0 ";

        if(id_providertype!=-1) sql += "and id_providertype = :id_providertype ";

        sql +=  "  order by full_name_provider  "+order+"";


        List<SqlRow>  sqlRows = Ebean.createSqlQuery(sql)
                .setParameter("id_providertype",id_providertype)
                .findList();



        return toProviders(sqlRows);

    }

    public  List<Provider> getByNameDocByTypeProvider(String nameDoc,Long id_providertype, String order)
    {
        String sql = "select t0.id_provider prov_id, t0.identification_doc_provider identification_doc," +
                " t0.full_name_provider full_name, " +
                "t0.address_provider address, t0.phone_number_provider phone_number, t0.email_provider email," +
                " t0.photo_provider photo," +
                " t0.contact_name_provider contact_name, t0.id_providerType providerType" +
                " from providers t0" +
                " where id_providertype = :id_providertype and t0.status_delete=0 " +
                "and (identification_doc_provider like '%"+nameDoc+"%' or full_name_provider like '%"+nameDoc+"%')"

                + "  order by full_name_provider  "+order+"";


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

            provider.setContactName(sqlRows.get(i).getString("contact_name"));
            provider.setFullName(sqlRows.get(i).getString("full_name"));
            provider.setPhoneNumber(sqlRows.get(i).getString("phone_number"));
            provider.setIdentificationDoc(sqlRows.get(i).getString("identification_doc"));
            provider.setAddress(sqlRows.get(i).getString("address"));
            provider.setId(sqlRows.get(i).getLong("prov_id"));
            provider.setEmail(sqlRows.get(i).getString("email"));
            provider.setPhoto(sqlRows.get(i).getString("photo"));
            provider.setProviderType(providerTypeDao.findById(sqlRows.get(i).getLong("providerType")));

            providers.add(provider);
        }

        return providers;
    }

}
