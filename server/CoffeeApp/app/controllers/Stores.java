package controllers;

import models.manager.StoreManager;
import models.manager.impl.StoreManagerImpl;
import models.manager.requestUtils.queryStringBindable.Pager;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

/**
 * Created by drocha on 30/05/17.
 */
public class Stores {

    private static StoreManager storeManager = new StoreManagerImpl();

    @CoffeAppsecurity
    public Result create() {
        return storeManager.create();
    }

    @CoffeAppsecurity
    public Result update() {
        return storeManager.update();
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        return storeManager.delete(id);
    }

    @CoffeAppsecurity
    public Result findById(Long id) {
        return storeManager.findById(id);
    }

    /*@HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    @CoffeAppsecurity
    public Result findAll(Integer index, Integer size) {
        return storeManager.findAll(index, size);
    }*/



    @CoffeAppsecurity
    public Result getByStatusStore(String StatusPurity, String order) {
        return storeManager.getByStatusStore(StatusPurity, order);
    }

    @CoffeAppsecurity
    public Result findAll(Pager pager, String sort, String collection) {
        return storeManager.findAll(pager.index, pager.size, sort, collection);
    }

    @CoffeAppsecurity
    public Result findAllSearch(String name, Pager pager, String sort, String collection) {
        return storeManager.findAllSearch(name, pager.index, pager.size, sort, collection);
    }

    @CoffeAppsecurity
    public Result preCreate() {
        return storeManager.preCreate();
    }
}
