package controllers;

import models.Security.HSecurity;
import models.domain.User;
import models.manager.SecurityRouteManager;
import models.manager.impl.SecurityRouteManagerImpl;
import play.Configuration;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

/**
 * Created by yenny on 10/3/16.
 */
public class SecurityRoutes extends Controller {

    @Inject
    private Configuration configuration;

    private SecurityRouteManager securityRouteManager = new SecurityRouteManagerImpl();
    //private SecurityRoute

    //Resultado que devuelve todas las rutas de fronteend disponibles
    @HSecurity("/route/checkngroute")
    public Result CheckNgRoute(){  return securityRouteManager.CheckNgRoute((User) ctx().args.get("CurrentUser"));   }
}
