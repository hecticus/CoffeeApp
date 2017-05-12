package models.dao.impl;

/**
 * Created by drocha on 25/04/17.
 */


import models.domain.Provider;
import models.dao.ProviderDao;

public class ProviderDaoImpl extends AbstractDaoImpl<Long, Provider> implements ProviderDao {

    public ProviderDaoImpl() {
        super(Provider.class);
    }



}
