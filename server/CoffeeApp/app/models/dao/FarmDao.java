package models.dao;

import com.avaje.ebean.text.PathProperties;
import models.dao.utils.ListPagerCollection;
import models.domain.Farm;

/**
 * Created by drocha on 31/05/17.
 */
public interface FarmDao extends AbstractDao<Long, Farm>{
    ListPagerCollection findAllSearch(String name, Integer index, Integer size, String sort, PathProperties pathProperties);
}
