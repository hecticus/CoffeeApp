package models.dao;

/**
 * Created by drocha on 12/05/17.
 */
import models.domain.ProviderType;

import java.util.List;

public interface ProviderTypeDao extends AbstractDao<Long, ProviderType>
{
    List<ProviderType> getProviderTypesByName(String name, String order);

    int getExist(String name);
}

