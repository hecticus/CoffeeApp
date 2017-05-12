package models.dao.impl;

import models.dao.ItemTypeDao;
import models.domain.ItemType;

/**
 * Created by drocha on 25/04/17.
 */

public class ItemTypeDaoImpl extends AbstractDaoImpl<Long, ItemType> implements ItemTypeDao {

    public ItemTypeDaoImpl() {
        super(ItemType.class);
    }
}