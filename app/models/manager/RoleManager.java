package models.manager;

import models.domain.User;
import play.mvc.Result;

/**
 * Created by yenny on 10/3/16.
 */
public interface RoleManager {

    Result CheckRouteByUser(String route, String token, long id);
    Result GetMenu(User user);
}
