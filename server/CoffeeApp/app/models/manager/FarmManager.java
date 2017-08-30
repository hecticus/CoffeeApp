package models.manager;

import play.mvc.Result;

/**
 * Created by darwin on 30/08/17.
 */
public interface FarmManager {


    public Result create();

    public Result update();

    public Result delete(Long id);

    public Result findById(Long id);

    // public Result findAll(Integer index, Integer size);

    Result findAll(Integer index, Integer size, String sort, String collection);

    Result findAllSearch(String name, Integer index, Integer size, String sort, String collection);

    Result preCreate();
}
