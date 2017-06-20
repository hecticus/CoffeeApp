package controllers;

import models.Security.HSecurity;
import models.manager.UnitManager;
import models.manager.impl.UnitManagerImpl;
import play.mvc.Result;

/**
 * Created by drocha on 25/04/17.
 */
public class Units {

    private static UnitManager unitManager = new UnitManagerImpl();

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result create() {
        return unitManager.create();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result update() {
        return unitManager.update();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result delete(Long id) {
        return unitManager.delete(id);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findById(Long id) {
        return unitManager.findById(id);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findAll(Integer index, Integer size) {
        return unitManager.findAll(index, size);
    }
}
