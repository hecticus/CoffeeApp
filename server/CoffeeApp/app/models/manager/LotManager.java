package models.manager;

import play.mvc.Result;

/**
 * Created by drocha on 26/04/17.
 */
public interface LotManager {

    public Result create();

    public Result update();

    public Result delete(Long id);

    public Result findById(Long id);

   // public Result findAll(Integer index, Integer size);

    public Result getByNameLot(String NameLot, String order);

    public Result getByStatusLot(String StatusLot, String order);

    Result findAll(Integer index, Integer size, String sort, String collection);

    Result findAllSearch(String name, Integer index, Integer size, String sort, String collection);

    Result preCreate();
}
