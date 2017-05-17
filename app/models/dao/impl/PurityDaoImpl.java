package models.dao.impl;

import models.dao.PurityDao;
import models.domain.Purity;

/**
 * Created by drocha on 26/04/17.
 */
public class PurityDaoImpl  extends AbstractDaoImpl<Long,Purity> implements PurityDao {

    public PurityDaoImpl() {
        super(Purity.class);
    }

    public int getExist(String name_purity)
    {
        if(find.where().eq("name_purity",name_purity).eq("status_delete",0).findUnique()!=null) return 0;
        else
        {
            if(find.where().eq("name_purity",name_purity).eq("status_delete",1).findUnique()!=null)  return 1;
            else return 2;

        }

    }
}
