package models.dao;

import models.domain.ItemType;

import java.util.List;

/**
 * Created by drocha on 25/04/17.
 */

public interface ItemTypeDao extends AbstractDao<Long, ItemType>{

    int getExist(String name);

    List<ItemType> getOpenByUnitId(Long idUnit);
    List<ItemType> getOpenByProviderTypeId(Long idProviderType);
}
