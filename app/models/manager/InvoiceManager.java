package models.manager;

import play.mvc.Result;

/**
 * Created by drocha on 25/04/17.
 */
public interface InvoiceManager {


    public Result create();

    public Result update();

    public Result delete(Long id);

    public Result findById(Long id);

    public Result findAll(Integer index, Integer size);

    public Result getByDateByTypeProvider(String date, Integer typeProvider);

    public Result getByDateByProviderId(String date, Long providerId);
}
