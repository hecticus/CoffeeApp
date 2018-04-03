package models.dao;

import io.ebean.text.PathProperties;
import models.dao.utils.ListPagerCollection;
import models.domain.ItemType;

import java.util.List;

/**
 * Created by drocha on 25/04/17.
 */

public interface ItemTypeDao extends AbstractDao<Long, ItemType>{

    int getExist(String name);

    List<ItemType> getOpenByUnitId(Long idUnit);
    List<ItemType> getOpenByProviderTypeId(Long idProviderType);
    List<ItemType> getByProviderTypeId(Long id_ProviderType, Integer status);
    List<ItemType> getByNameItemType(String NameItemType, String order);
    ListPagerCollection findAllSearch(String name, Integer index, Integer size, String sort, PathProperties pathProperties);
}
