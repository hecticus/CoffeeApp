package controllers;

import models.Security.HSecurity;
import models.manager.ItemTypeManager;
import models.manager.impl.ItemTypeManagerImpl;
import play.mvc.Result;

/**
 * Created by drocha on 25/04/17.
 */
public class ItemTypes {


    private static ItemTypeManager itemTypeManager = new ItemTypeManagerImpl();

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result create() {
        return itemTypeManager.create();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result update() {
        return itemTypeManager.update();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result delete(Long id) {
        return itemTypeManager.delete(id);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findById(Long id) {
        return itemTypeManager.findById(id);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findAll(Integer index, Integer size) {
        return itemTypeManager.findAll(index, size);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result getByProviderTypeId(Long id_ProviderType, Integer status){return itemTypeManager.getByProviderTypeId(id_ProviderType,status);}

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result getByNameItemType(String NameItemType, String order)
    { return itemTypeManager.getByNameItemType( NameItemType,order);}
}
