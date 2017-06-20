package controllers;

import models.Security.HSecurity;
import models.manager.StoreManager;
import models.manager.impl.StoreManagerImpl;
import play.mvc.Result;

/**
 * Created by drocha on 30/05/17.
 */
public class Stores {

    private static StoreManager storeManager = new StoreManagerImpl();

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result create() {
        return storeManager.create();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result update() {
        return storeManager.update();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result delete(Long id) {
        return storeManager.delete(id);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findById(Long id) {
        return storeManager.findById(id);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findAll(Integer index, Integer size) {
        return storeManager.findAll(index, size);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result getByStatusStore(String StatusPurity, String order) {
        return storeManager.getByStatusStore(StatusPurity, order);
    }
}
