package models.manager;

import play.mvc.Result;

/**
 * Created by drocha on 12/05/17.
 */
public interface ProviderTypeManager {

    public Result create();

    public Result update();

    public Result delete(Long id);

    public Result findById(Long id);

    public Result findAll(Integer index, Integer size);


}