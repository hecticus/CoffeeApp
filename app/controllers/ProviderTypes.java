package controllers;

import models.manager.ProviderTypeManager;
import models.manager.impl.ProviderTypeManagerImpl;
import play.mvc.Result;

/**
 * Created by drocha on 12/05/17.
 */
public class ProviderTypes {
    private static ProviderTypeManager providerTypeManager = new ProviderTypeManagerImpl();

    public Result create() {
        return providerTypeManager.create();
    }

    public Result update() {
        return providerTypeManager.update();
    }

    public Result delete(Long id) {
        return providerTypeManager.delete(id);
    }

    public Result findById(Long id) {
        return providerTypeManager.findById(id);
    }

    public Result findAll(Integer index, Integer size) {
        return providerTypeManager.findAll(index, size);
    }
}
