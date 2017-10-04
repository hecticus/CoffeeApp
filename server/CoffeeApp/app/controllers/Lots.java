package controllers;

import models.Security.HSecurity;
import models.manager.LotManager;
import models.manager.impl.LotManagerImpl;
import play.mvc.Result;
import models.manager.requestUtils.queryStringBindable.Pager;

/**
 * Created by drocha on 26/04/17.
 */
public class Lots {


    private static LotManager lotManager = new LotManagerImpl();

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result create() {
        return lotManager.create();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result update() {
        return lotManager.update();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result delete(Long id) {
        return lotManager.delete(id);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findById(Long id) {
        return lotManager.findById(id);
    }

   /* @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findAll(Integer index, Integer size) {
        return lotManager.findAll(index, size);
    }*/

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result getByNameLot(String NameLot, String order) {
        return lotManager.getByNameLot(NameLot, order);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result getByStatusLot(String StatusLot, String order) {
        return lotManager.getByStatusLot(StatusLot, order);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findAll(Pager pager, String sort, String collection) {
        return lotManager.findAll(pager.index, pager.size, sort, collection);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findAllSearch(String name, Pager pager, String sort, String collection, Integer all, Integer idFarm) {
        return lotManager.findAllSearch(name, pager.index, pager.size, sort, collection, all, idFarm);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result preCreate() {
        return lotManager.preCreate();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result deletes() {
        return lotManager.deletes();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public  Result getByIdFarm(Long id_farm, Pager pager,  String sort, String collection){
        return lotManager.getByIdFarm(id_farm, pager.index, pager.size, sort, collection);
    }
}
