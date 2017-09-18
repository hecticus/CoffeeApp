package models.manager;

import play.mvc.Result;

/**
 * Created by drocha on 26/04/17.
 */
public interface PurityManager {

    public Result create();

    public Result update();

    public Result delete(Long id);

    public Result findById(Long id);

   // public Result findAll(Integer index, Integer size);

    public Result getByNamePurity(String NamePurity, String order);

    public Result getByStatusPurity(String StatusPurity, String order);

    Result findAll(Integer index, Integer size, String sort, String collection);

    Result findAllSearch(String name, Integer index, Integer size, String sort, String collection);

    Result preCreate();
}
