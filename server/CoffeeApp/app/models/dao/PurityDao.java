package models.dao;

import io.ebean.text.PathProperties;
import controllers.utils.ListPagerCollection;
import models.domain.Purity;

import java.util.List;

/**
 * Created by drocha on 26/04/17.
 */
public interface PurityDao extends AbstractDao<Long, Purity>{

    int getExist(String name);
    List<Purity> getByNamePurity(String NamePurity, String order);
    List<Purity> getByStatusPurity(String StatusPurity, String order);
    ListPagerCollection findAllSearch(String name, Integer index, Integer size, String sort, PathProperties pathProperties);
}

