package controllers;

import models.manager.StoreManager;
import models.manager.impl.StoreManagerImpl;
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

    public Result findAll(Integer index, Integer size) {
        return storeManager.findAll(index, size);
    }

    public Result getByStatusStore(String StatusPurity, String order) {
        return storeManager.getByStatusStore(StatusPurity, order);
    }
}
