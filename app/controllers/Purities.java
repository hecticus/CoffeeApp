package controllers;

import models.manager.PurityManager;
import models.manager.impl.PurityManagerImpl;
import play.mvc.Result;

/**
 * Created by drocha on 26/04/17.
 */
public class Purities {

    private static PurityManager purityManager = new PurityManagerImpl();

    public Result create() {
        return purityManager.create();
    }

    public Result update() {
        return purityManager.update();
    }

    public Result delete(Long id) {
        return purityManager.delete(id);
    }

    public Result findById(Long id) {
        return purityManager.findById(id);
    }

    public Result findAll(Integer index, Integer size) {
        return purityManager.findAll(index, size);
    }

    public Result getByNamePurity(String NamePurity, String order) {
        return purityManager.getByNamePurity(NamePurity, order);
    }

    public Result getByStatusPurity(String StatusPurity, String order) {
        return purityManager.getByStatusPurity(StatusPurity, order);
    }
}