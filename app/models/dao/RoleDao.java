package models.dao;

import models.domain.Role;
import models.domain.User;

/**
 * Created by yenny on 9/30/16.
 */
public interface RoleDao extends AbstractDao<Long, Role> {

    boolean AccessResource(User user, String route);
    //Result AccessResultResource(User user, String token, String route);

}
