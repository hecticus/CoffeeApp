package controllers;

import models.manager.ItemTypeManager;
import models.manager.impl.ItemTypeManagerImpl;
import play.mvc.Result;

/**
 * Created by drocha on 25/04/17.
 */
public class ItemTypes {


    private static ItemTypeManager itemTypeManager = new ItemTypeManagerImpl();

    public Result create() {
        return itemTypeManager.create();
    }

    public Result update() {
        return itemTypeManager.update();
    }

    public Result delete(Long id) {
        return itemTypeManager.delete(id);
    }

    public Result findById(Long id) {
        return itemTypeManager.findById(id);
    }

    public Result findAll(Integer index, Integer size) {
        return itemTypeManager.findAll(index, size);
    }

    public Result getByProviderTypeId(Long id_ProviderType, Integer status){return itemTypeManager.getByProviderTypeId(id_ProviderType,status);}

    public Result getByNameItemType(String NameItemType, String order)
    { return itemTypeManager.getByNameItemType( NameItemType,order);}
}
