package models.dao.impl;

import io.ebean.Ebean;
import io.ebean.SqlRow;
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

    public List<ProviderType> getProviderTypesByName(String name_providertype, String order)
    {
        String sql = " select t0.id_providertype, t0.status_delete, " +
                " t0.name_providertype, t0.created_at, t0.updated_at  " +
                " from provider_types t0 " +
                " where status_delete = 0 "+
                " ";

       if(!name_providertype.equals(""))     sql += "  and name_providertype like '%"+name_providertype+"%' ";

        sql += "  order by name_providertype "+order+"";


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

            providerType.setIdProviderType(sqlRows.get(i).getLong("id_providertype"));
            providerType.setNameProviderType(sqlRows.get(i).getString("name_providertype"));


            providerTypes.add(providerType);
        }

        return providerTypes;
    }

    public int getExist(String name_providertype)
    {
        if(find.query().where().eq("name_providertype",name_providertype).eq("status_delete",0).findUnique()!=null) return 0;
        else
        {
            if(find.query().where().eq("name_providertype",name_providertype).eq("status_delete",1).findUnique()!=null)  return 1;
            else return 2;

        }

    }
}
