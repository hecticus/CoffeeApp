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
}
