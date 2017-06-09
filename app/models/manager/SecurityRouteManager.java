package models.manager;

import models.domain.User;
import play.mvc.Result;

/**
 * Created by Mumumba on 10/3/16.
 */
public interface SecurityRouteManager {
    Result CheckNgRoute(User user);
}
