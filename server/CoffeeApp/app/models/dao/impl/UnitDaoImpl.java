package models.dao.impl;

import io.ebean.ExpressionList;
import io.ebean.text.PathProperties;
import models.dao.UnitDao;
import models.dao.utils.ListPagerCollection;
import models.domain.Unit;

/**
 * Created by drocha on 25/04/17.
 */
public class UnitDaoImpl  extends AbstractDaoImpl<Long, Unit> implements UnitDao {

    public UnitDaoImpl() {
        super(Unit.class);
    }

    public int getExist(String name_unit)
    {
        if(find.query().where().eq("name_unit",name_unit).eq("status_delete",0).findUnique()!=null) return 0;
        else
        {
            if(find.query().where().eq("name_unit",name_unit).eq("status_delete",1).findUnique()!=null)  return 1;
            else return 2;

        }
    }
}
