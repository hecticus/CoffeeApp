package models.dao.impl;

import models.dao.StoreDao;
import models.domain.Store;

/**
 * Created by drocha on 12/05/17.
 */
public class StoreDaoImpl  extends AbstractDaoImpl<Long,Store> implements StoreDao {

    public StoreDaoImpl() {
        super(Store.class);
    }

    public int getExist(String name_store)
    {
        if(find.where().eq("name_store",name_store).eq("status_delete",0).findUnique()!=null) return 0;
        else
        {
            if(find.where().eq("name_store",name_store).eq("status_delete",1).findUnique()!=null)  return 1;
            else return 2;

        }

    }
}
