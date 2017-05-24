package models.dao.impl;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import models.dao.ItemTypeDao;
import models.dao.UnitDao;
import models.domain.ItemType;
import models.manager.requestUtils.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 25/04/17.
 */

public class ItemTypeDaoImpl extends AbstractDaoImpl<Long, ItemType> implements ItemTypeDao {

    public ItemTypeDaoImpl() {
        super(ItemType.class);
    }
    private static UnitDao unitDao = new UnitDaoImpl();

    public int getExist(String name_item_type)
    {
       if(find.where().eq("name_item_type",name_item_type).eq("status_delete",0).findUnique()!=null) return 0;
       else
       {
           if(find.where().eq("name_item_type",name_item_type).eq("status_delete",1).findUnique()!=null)  return 1;
           else return 2;

       }

    }

    public List<ItemType> getOpenByUnitId(Long idUnit)
    {
        return find.where().eq("id_unit",idUnit).eq("status_delete",0).findList();
    }

    public List<ItemType> getOpenByProviderTypeId(Long idProviderType)
    {
        return find.where().eq("id_providertype",idProviderType).eq("status_delete",0).findList();
    }

    public List<ItemType> getByProviderTypeId(Long id_ProviderType, Integer status)
    {
        String sql="SELECT item.id_item_type c0, item.status_delete c1, item.name_item_type c2, " +
                " item.cost_item_type c3, item.status_item_type c4, item.created_at c5, " +
                " item.updated_at c6, item.id_providerType c7, item.id_unit c8  " +
                " FROM item_types item "+
                " inner join provider_types protype on protype.id_provider_type=item.id_providertype " +
                " where item.status_delete=0  and item.id_providerType= :idProviderType ";

        if(status!=-1) sql+= " and protype.status_delete=:status ";

        SqlQuery query = Ebean.createSqlQuery(sql)
                .setParameter("idProviderType", id_ProviderType )
                .setParameter("status",status);

        List<SqlRow>   results = query.findList();

return toItemTypes(results);
    }

    public List<ItemType> getByNameItemType(String NameItemType, String order)
    {
        String sql="SELECT item.id_item_type c0, item.status_delete c1, item.name_item_type c2, " +
                " item.cost_item_type c3, item.status_item_type c4, item.created_at c5, " +
                " item.updated_at c6, item.id_providerType c7, item.id_unit c8  " +
                " FROM item_types item "+
                " where item.status_delete=0  and item.name_item_type like '%"+NameItemType+"%' "+
                " order by item.name_item_type "+order;

        SqlQuery query = Ebean.createSqlQuery(sql);

        List<SqlRow>   results = query.findList();

        return toItemTypes(results);
    }

    public List<ItemType> toItemTypes(List<SqlRow>  sqlRows)
    {
        List<ItemType> itemTypes = new ArrayList<>();

        ItemType itemType;
        for(int i=0; i < sqlRows.size(); ++i)
        {
            itemType = new ItemType();

            itemType.setId(sqlRows.get(i).getLong("c0"));
            itemType.setName(sqlRows.get(i).getString("c2"));
            itemType.setCost(sqlRows.get(i).getFloat("c3"));
            itemType.setUnit(unitDao.findById(sqlRows.get(i).getLong("c8")));
            itemType.setStatus(sqlRows.get(i).getInteger("c4"));
            itemTypes.add(itemType);
        }

        return itemTypes;
    }
}