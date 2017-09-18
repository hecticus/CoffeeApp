package models.dao;

import com.avaje.ebean.text.PathProperties;
import models.dao.utils.ListPagerCollection;
import models.domain.Store;


import java.util.List;

/**
 * Created by drocha on 12/05/17.
 */
public interface StoreDao extends AbstractDao<Long, Store>{

    int getExist(String name);
    List<Store> getByStatusStore(String StatusStore, String order);
    ListPagerCollection findAllSearch(String name, Integer index, Integer size, String sort, PathProperties pathProperties);
}
