package models.dao.impl;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import models.dao.ProviderTypeDao;
import models.domain.ProviderType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 12/05/17.
 */
public class ProviderTypeDaoImpl extends AbstractDaoImpl<Long, ProviderType> implements ProviderTypeDao {

    public ProviderTypeDaoImpl() {
        super(ProviderType.class);
    }

    public List<ProviderType> getProviderTypesByName(String name_provider_type, String order)
    {
        String sql = " select t0.id_provider_type, t0.status_delete, " +
                " t0.name_provider_type, t0.created_at, t0.updated_at  " +
                " from provider_types t0 " +
                " where status_delete = 0 "+
                " ";

       if(!name_provider_type.equals(""))     sql += "  and name_provider_type like '%"+name_provider_type+"%' ";

        sql += "  order by name_provider_type "+order+"";


        List<SqlRow>  sqlRows = Ebean.createSqlQuery(sql)
                .findList();



       return toProviderTypes(sqlRows);


    }

    public List<ProviderType> toProviderTypes(List<SqlRow>  sqlRows)
    {
        List<ProviderType> providerTypes = new ArrayList<>();
        ProviderType providerType;

        for(int i=0; i < sqlRows.size(); ++i)
        {
            providerType = new ProviderType();

            providerType.setId(sqlRows.get(i).getLong("id_provider_type"));
            providerType.setName(sqlRows.get(i).getString("name_provider_type"));


            providerTypes.add(providerType);
        }

        return providerTypes;
    }


}
