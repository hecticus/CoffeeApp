package controllers;

import models.manager.FarmManager;
import models.manager.impl.FarmManagerImpl;
import models.manager.requestUtils.queryStringBindable.Pager;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

/**
 * Created by darwin on 30/08/17.
 */
public class Farms {

    private static FarmManager farmManager = new FarmManagerImpl();

    @CoffeAppsecurity
    public Result findById(Long id) {
        return farmManager.findById(id);
    }

    @CoffeAppsecurity
    public Result findAll(Pager pager, String sort, String collection) {
        return farmManager.findAll(pager.index, pager.size, sort, collection);
    }

    @CoffeAppsecurity
    public Result findAllSearch(String name, Pager pager, String sort, String collection) {
        return farmManager.findAllSearch(name, pager.index, pager.size, sort, collection);
    }
}
