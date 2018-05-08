package controllers;

import models.manager.UnitManager;
import models.manager.impl.UnitManagerImpl;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

/**
 * Created by drocha on 25/04/17.
 */
public class Units {

    private static UnitManager unitManager = new UnitManagerImpl();

    @CoffeAppsecurity
    public Result create() {
        return unitManager.create();
    }

    @CoffeAppsecurity
    public Result update() {
        return unitManager.update();
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        return unitManager.delete(id);
    }

    @CoffeAppsecurity
    public Result findById(Long id) {
        return unitManager.findById(id);
    }

    @CoffeAppsecurity
    public Result findAll(Integer index, Integer size) {
        return unitManager.findAll(index, size);
    }
}
