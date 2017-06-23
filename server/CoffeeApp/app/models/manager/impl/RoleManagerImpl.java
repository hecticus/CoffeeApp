package models.manager.impl;

import models.dao.RoleDao;
import models.dao.SecurityRouteDao;
import models.dao.UserDao;
import models.dao.impl.RoleDaoImpl;
import models.dao.impl.SecurityRouteDaoImpl;
import models.dao.impl.UserDaoImpl;
import models.domain.SecurityRoute;
import models.domain.User;
import models.manager.RoleManager;
import models.manager.responseUtils.JsonUtils;
import models.manager.responseUtils.Response;
import play.mvc.Result;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yenny on 10/3/16.
 */
public class RoleManagerImpl implements RoleManager {

    private static UserDao userDao = new UserDaoImpl();
    private static RoleDao roleDao = new RoleDaoImpl();
    private static SecurityRouteDao securityRouteDao = new SecurityRouteDaoImpl();

    public RoleManagerImpl() {

    }


    @Override
    @Deprecated
    public Result CheckRouteByUser(String route, String token, long id)
    {
        User aux = userDao.findByTokenAndID(id, token);
        if(aux != null)
        {
            SecurityRoute sR = securityRouteDao.CheckAndInsert(route, 0);
            return roleDao.AccessResource(aux, route)? null: Response.accessDenied();
        }
        return Response.accessDenied();
    }

    @Override
    public Result GetMenu(User user)
    {
        //User aux = userDao.findByTokenAndID(id, token);
        List <SecurityRoute> SC = securityRouteDao.GetMenu(user);
        if(SC.size() > 0)
            return Response.foundEntity(
                JsonUtils.merge(Arrays.asList(
                        JsonUtils.toJson(SC, SecurityRoute.class, "SecurityRoutes"))));
        else
            return Response.accessDenied();
    }
}