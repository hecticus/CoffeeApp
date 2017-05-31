package models.manager;

import play.mvc.Result;

/**
 * Created by drocha on 12/05/17.
 */
public interface StoreManager {


    public Result create();

    public Result update();

    public Result delete(Long id);

    public Result findById(Long id);

    public Result findAll(Integer index, Integer size);

    public Result getByStatusStore(String StatusPurity, String order);
}
