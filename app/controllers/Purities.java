package controllers;

import models.Security.HSecurity;
import models.manager.PurityManager;
import models.manager.impl.PurityManagerImpl;
import play.mvc.Result;

/**
 * Created by drocha on 26/04/17.
 */
public class Purities {

    private static PurityManager purityManager = new PurityManagerImpl();

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result create() {
        return purityManager.create();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result update() {
        return purityManager.update();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result delete(Long id) {
        return purityManager.delete(id);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findById(Long id) {
        return purityManager.findById(id);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findAll(Integer index, Integer size) {
        return purityManager.findAll(index, size);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result getByNamePurity(String NamePurity, String order) {
        return purityManager.getByNamePurity(NamePurity, order);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result getByStatusPurity(String StatusPurity, String order) {
        return purityManager.getByStatusPurity(StatusPurity, order);
    }
}