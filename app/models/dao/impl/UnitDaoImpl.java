package models.dao.impl;

import models.dao.UnitDao;
import models.domain.Unit;

/**
 * Created by drocha on 25/04/17.
 */
public class UnitDaoImpl  extends AbstractDaoImpl<Long, Unit> implements UnitDao {

    public UnitDaoImpl() {
        super(Unit.class);
    }
}
