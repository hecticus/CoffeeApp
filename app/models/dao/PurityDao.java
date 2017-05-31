package models.dao;

import models.domain.Purity;

import java.util.List;

/**
 * Created by drocha on 26/04/17.
 */
public interface PurityDao extends AbstractDao<Long, Purity>{

    int getExist(String name);
    List<Purity> getByNamePurity(String NamePurity, String order);
    List<Purity> getByStatusPurity(String StatusPurity, String order);
}

