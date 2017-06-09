package models.dao;

import models.domain.SecurityRoute;
import models.domain.User;

import java.util.List;

/**
 * Created by yenny on 9/30/16.
 */
public interface SecurityRouteDao extends AbstractDao<Long, SecurityRoute> {

    SecurityRoute CheckAndInsert(String resourceName, int type);

    List<SecurityRoute> GetMenu(User user);

}
