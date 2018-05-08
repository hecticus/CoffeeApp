package controllers;

import models.manager.PurityManager;
import models.manager.impl.PurityManagerImpl;
import models.manager.requestUtils.queryStringBindable.Pager;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

/**
 * Created by drocha on 26/04/17.
 */
public class Purities {

    private static PurityManager purityManager = new PurityManagerImpl();

    @CoffeAppsecurity
    public Result create() {
        return purityManager.create();
    }

    @CoffeAppsecurity
    public Result update() {
        return purityManager.update();
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        return purityManager.delete(id);
    }

    @CoffeAppsecurity
    public Result findById(Long id) {
        return purityManager.findById(id);
    }

   /* @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    @CoffeAppsecurity
    public Result findAll(Integer index, Integer size) {
        return purityManager.findAll(index, size);
    }*/

    @CoffeAppsecurity
    public Result getByNamePurity(String NamePurity, String order) {
        return purityManager.getByNamePurity(NamePurity, order);
    }

    @CoffeAppsecurity
    public Result getByStatusPurity(String StatusPurity, String order) {
        return purityManager.getByStatusPurity(StatusPurity, order);
    }

    @CoffeAppsecurity
    public Result findAll(Pager pager, String sort, String collection) {
        return purityManager.findAll(pager.index, pager.size, sort, collection);
    }

    @CoffeAppsecurity
    public Result findAllSearch(String name, Pager pager, String sort, String collection) {
        return purityManager.findAllSearch(name, pager.index, pager.size, sort, collection);
    }

    @CoffeAppsecurity
    public Result preCreate() {
        return purityManager.preCreate();
    }
}