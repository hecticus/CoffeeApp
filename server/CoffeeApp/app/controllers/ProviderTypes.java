package controllers;

import models.manager.ProviderTypeManager;
import models.manager.impl.ProviderTypeManagerImpl;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

/**
 * Created by drocha on 12/05/17.
 */
public class ProviderTypes {
    private static ProviderTypeManager providerTypeManager = new ProviderTypeManagerImpl();

    @CoffeAppsecurity
    public Result create() {
        return providerTypeManager.create();
    }

    @CoffeAppsecurity
    public Result update() {
        return providerTypeManager.update();
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        return providerTypeManager.delete(id);
    }

    @CoffeAppsecurity
    public Result findById(Long id) {
        return providerTypeManager.findById(id);
    }

    @CoffeAppsecurity
    public Result findAll(Integer index, Integer size) {
        return providerTypeManager.findAll(index, size);
    }

    @CoffeAppsecurity
    public Result  getProviderTypesByName(String name, String order) {
        return providerTypeManager.getProviderTypesByName(name,order);
    }
}
