package controllers;

import models.Security.HSecurity;
import models.domain.User;
import models.manager.RoleManager;
import models.manager.impl.RoleManagerImpl;
import play.Configuration;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

/**
 * Created by yenny on 10/3/16.
 */
public class Roles extends Controller {
    @Inject
    private Configuration configuration;

    private RoleManager roleManager = new RoleManagerImpl();
    //Resultado que devuelve todas las rutas de fronteend disponibles
    @HSecurity("/role/getmenu")
    public Result getMenu(){  return roleManager.GetMenu((User) ctx().args.get("CurrentUser"));  }
}
