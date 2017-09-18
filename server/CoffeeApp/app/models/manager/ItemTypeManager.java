package models.manager;

import play.mvc.Result;

/**
 * Created by drocha on 25/04/17.
 */
public interface ItemTypeManager {

    public Result create();

    public Result update();

    public Result delete(Long id);

    public Result findById(Long id);

   // public Result findAll(Integer index, Integer size);
    public Result getByProviderTypeId(Long idProviderType, Integer status);
    public Result getByNameItemType(String NameItemType, String order);


    Result findAll(Integer index, Integer size, String sort, String collection);

    Result findAllSearch(String name, Integer index, Integer size, String sort, String collection);

    Result preCreate();
}
