package models.dao.impl;

import models.dao.ProviderTypeDao;
import models.domain.ProviderType;

/**
 * Created by drocha on 12/05/17.
 */
public class ProviderTypeDaoImpl extends AbstractDaoImpl<Long, ProviderType> implements ProviderTypeDao {

    public ProviderTypeDaoImpl() {
        super(ProviderType.class);
    }



}
