package models.dao.impl;

import models.dao.ItemTypeDao;
import models.domain.ItemType;

import java.util.List;

/**
 * Created by drocha on 25/04/17.
 */

public class ItemTypeDaoImpl extends AbstractDaoImpl<Long, ItemType> implements ItemTypeDao {

    public ItemTypeDaoImpl() {
        super(ItemType.class);
    }

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
}