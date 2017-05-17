package models.dao.impl;

import models.dao.LotDao;
import models.domain.Lot;

/**
 * Created by drocha on 26/04/17.
 */
public class LotDaoImpl  extends AbstractDaoImpl<Long, Lot> implements LotDao {

    public LotDaoImpl() {
        super(Lot.class);
    }

    public int getExist(String name_item_type)
    {
        if(find.where().eq("name_lot",name_item_type).eq("status_delete",0).findUnique()!=null) return 0;
        else
        {
            if(find.where().eq("name_lot",name_item_type).eq("status_delete",1).findUnique()!=null)  return 1;
            else return 2;

        }

    }
}