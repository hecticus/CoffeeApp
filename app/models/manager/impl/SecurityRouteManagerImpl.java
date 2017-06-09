package models.manager.impl;

import models.dao.RoleDao;
import models.dao.SecurityRouteDao;
import models.dao.UserDao;
import models.dao.impl.RoleDaoImpl;
import models.dao.impl.SecurityRouteDaoImpl;
import models.dao.impl.UserDaoImpl;
import models.domain.User;
import models.manager.SecurityRouteManager;
import models.manager.responseUtils.Response;
import play.mvc.Result;

import static play.mvc.Controller.request;


/**
 * Created by yenny on 10/3/16.
 */
public class SecurityRouteManagerImpl implements SecurityRouteManager {

    private static UserDao userDao = new UserDaoImpl();
    private static RoleDao roleDao = new RoleDaoImpl();
    private static SecurityRouteDao securityRouteDao = new SecurityRouteDaoImpl();

    public SecurityRouteManagerImpl() {

    }

    @Override
    public Result CheckNgRoute(User user)
    {
        securityRouteDao.CheckAndInsert( request().body().asJson().get("route").asText(),1);
        boolean aux =  roleDao.AccessResource(user, request().body().asJson().get("route").asText());
        return aux? Response.accessGranted(): Response.accessDenied();
    }



}