package models.dao;

import models.domain.Store;


import java.util.List;

/**
 * Created by drocha on 12/05/17.
 */
public interface StoreDao extends AbstractDao<Long, Store>{

    int getExist(String name);
    List<Store> getByStatusStore(String StatusStore, String order);
}
