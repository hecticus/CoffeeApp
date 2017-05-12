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

    public Result findAll(Integer index, Integer size);
}
