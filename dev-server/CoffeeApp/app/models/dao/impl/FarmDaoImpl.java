package models.dao.impl;

import models.dao.FarmDao;
import models.domain.Farm;

/**
 * Created by drocha on 31/05/17.
 */
public class FarmDaoImpl  extends AbstractDaoImpl<Long, Farm> implements FarmDao {

    public FarmDaoImpl() {
        super(Farm.class);
    }
    
}
