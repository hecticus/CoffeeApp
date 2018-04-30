package controllers;

import models.Security.HSecurity;
import models.manager.StoreManager;
import models.manager.impl.StoreManagerImpl;
import models.manager.requestUtils.queryStringBindable.Pager;
import play.mvc.Result;

/**
 * Created by drocha on 30/05/17.
 */
public class Stores {

    private static StoreManager storeManager = new StoreManagerImpl();

    public Result create() {
        return storeManager.create();
    }

    public Result update() {
        return storeManager.update();
    }

    public Result delete(Long id) {
        return storeManager.delete(id);
    }

    public Result findById(Long id) {
        return storeManager.findById(id);
    }

    /*@HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findAll(Integer index, Integer size) {
        return storeManager.findAll(index, size);
    }*/



    public Result getByStatusStore(String StatusPurity, String order) {
        return storeManager.getByStatusStore(StatusPurity, order);
    }

    public Result findAll(Pager pager, String sort, String collection) {
        return storeManager.findAll(pager.index, pager.size, sort, collection);
    }

    public Result findAllSearch(String name, Pager pager, String sort, String collection) {
        return storeManager.findAllSearch(name, pager.index, pager.size, sort, collection);
    }

    public Result preCreate() {
        return storeManager.preCreate();
    }
}
