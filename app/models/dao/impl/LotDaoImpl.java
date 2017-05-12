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
}