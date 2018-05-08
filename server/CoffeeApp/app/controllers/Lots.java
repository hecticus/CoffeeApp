package controllers;

import models.manager.LotManager;
import models.manager.impl.LotManagerImpl;
import models.manager.requestUtils.queryStringBindable.Pager;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

/**
 * Created by drocha on 26/04/17.
 */
public class Lots {


    private static LotManager lotManager = new LotManagerImpl();

    @CoffeAppsecurity
    public Result create() {
        return lotManager.create();
    }

    @CoffeAppsecurity
    public Result update() {
        return lotManager.update();
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        return lotManager.delete(id);
    }

    @CoffeAppsecurity
    public Result findById(Long id) {
        return lotManager.findById(id);
    }

   /* @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    @CoffeAppsecurity
    public Result findAll(Integer index, Integer size) {
        return lotManager.findAll(index, size);
    }*/

    @CoffeAppsecurity
    public Result getByNameLot(String NameLot, String order) {
        return lotManager.getByNameLot(NameLot, order);
    }

    @CoffeAppsecurity
    public Result getByStatusLot(String StatusLot, String order) {
        return lotManager.getByStatusLot(StatusLot, order);
    }

    @CoffeAppsecurity
    public Result findAll(Pager pager, String sort, String collection) {
        return lotManager.findAll(pager.index, pager.size, sort, collection);
    }

    @CoffeAppsecurity
    public Result findAllSearch(String name, Pager pager, String sort, String collection, Integer all, Integer idFarm) {
        return lotManager.findAllSearch(name, pager.index, pager.size, sort, collection, all, idFarm);
    }

    @CoffeAppsecurity
    public Result preCreate() {
        return lotManager.preCreate();
    }

    @CoffeAppsecurity
    public Result deletes() {
        return lotManager.deletes();
    }

    @CoffeAppsecurity
    public  Result getByIdFarm(Long id_farm, Pager pager,  String sort, String collection){
        return lotManager.getByIdFarm(id_farm, pager.index, pager.size, sort, collection);
    }
}
