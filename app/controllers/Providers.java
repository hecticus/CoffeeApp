package controllers;

import play.mvc.Result;
import models.manager.ProviderManager;
import models.manager.impl.ProviderManagerImpl;
/**
 * Created by drocha on 25/04/17.
 */
public class Providers
{

    private static ProviderManager providerManager = new ProviderManagerImpl();

    public Result create() {
        return providerManager.create();
    }

    public Result update() {
        return providerManager.update();
    }

    public Result delete(Long id) {
        return providerManager.delete(id);
    }

    public Result findById(Long id) {
        return providerManager.findById(id);
    }

    public Result findAll(Integer index, Integer size) {
        return providerManager.findAll(index, size);
    }



}
