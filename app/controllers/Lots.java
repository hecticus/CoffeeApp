package controllers;

import models.manager.LotManager;
import models.manager.impl.LotManagerImpl;
import play.mvc.Result;

/**
 * Created by drocha on 26/04/17.
 */
public class Lots {


    private static LotManager lotManager = new LotManagerImpl();

    public Result create() {
        return lotManager.create();
    }

    public Result update() {
        return lotManager.update();
    }

    public Result delete(Long id) {
        return lotManager.delete(id);
    }

    public Result findById(Long id) {
        return lotManager.findById(id);
    }

    public Result findAll(Integer index, Integer size) {
        return lotManager.findAll(index, size);
    }

    public Result getByNameLot(String NameLot, String order) {
        return lotManager.getByNameLot(NameLot, order);
    }

    public Result getByStatusLot(String StatusLot, String order) {
        return lotManager.getByStatusLot(StatusLot, order);
    }
}
