package models.dao;

import models.domain.Purity;

/**
 * Created by drocha on 26/04/17.
 */
public interface PurityDao extends AbstractDao<Long, Purity>{

    int getExist(String name);
}

