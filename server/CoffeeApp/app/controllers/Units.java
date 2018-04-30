package controllers;

import models.manager.UnitManager;
import models.manager.impl.UnitManagerImpl;
import play.mvc.Result;

/**
 * Created by drocha on 25/04/17.
 */
public class Units {

    private static UnitManager unitManager = new UnitManagerImpl();

    public Result create() {
        return unitManager.create();
    }

    public Result update() {
        return unitManager.update();
    }

    public Result delete(Long id) {
        return unitManager.delete(id);
    }

    public Result findById(Long id) {
        return unitManager.findById(id);
    }

    public Result findAll(Integer index, Integer size) {
        return unitManager.findAll(index, size);
    }
}
