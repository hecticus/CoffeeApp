package controllers;

import models.manager.ItemTypeManager;
import models.manager.impl.ItemTypeManagerImpl;
import models.manager.requestUtils.queryStringBindable.Pager;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

/**
 * Created by drocha on 25/04/17.
 */
public class ItemTypes {


    private static ItemTypeManager itemTypeManager = new ItemTypeManagerImpl();

    @CoffeAppsecurity
    public Result create() {
        return itemTypeManager.create();
    }

    @CoffeAppsecurity
    public Result update() {
        return itemTypeManager.update();
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        return itemTypeManager.delete(id);
    }

    @CoffeAppsecurity
    public Result findById(Long id) {
        return itemTypeManager.findById(id);
    }

   /* @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    @CoffeAppsecurity
    public  Result findAll(Integer index, Integer size) {
        return itemTypeManager.findAll(index, size);
    }*/

    @CoffeAppsecurity
    public Result getByProviderTypeId(Long id_ProviderType, Integer status){return itemTypeManager.getByProviderTypeId(id_ProviderType,status);}

    @CoffeAppsecurity
    public Result getByNameItemType(String NameItemType, String order)
    { return itemTypeManager.getByNameItemType( NameItemType,order);}

    @CoffeAppsecurity
    public Result findAll(Pager pager, String sort, String collection) {
        return itemTypeManager.findAll(pager.index, pager.size, sort, collection);
    }

    @CoffeAppsecurity
    public Result findAllSearch(String name, Pager pager, String sort, String collection) {
        return itemTypeManager.findAllSearch(name, pager.index, pager.size, sort, collection);
    }

    @CoffeAppsecurity
    public Result preCreate() {
        return itemTypeManager.preCreate();
    }
}
