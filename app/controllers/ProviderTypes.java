package controllers;

import models.Security.HSecurity;
import models.manager.ProviderTypeManager;
import models.manager.impl.ProviderTypeManagerImpl;
import play.mvc.Result;

/**
 * Created by drocha on 12/05/17.
 */
public class ProviderTypes {
    private static ProviderTypeManager providerTypeManager = new ProviderTypeManagerImpl();

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result create() {
        return providerTypeManager.create();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result update() {
        return providerTypeManager.update();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result delete(Long id) {
        return providerTypeManager.delete(id);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findById(Long id) {
        return providerTypeManager.findById(id);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findAll(Integer index, Integer size) {
        return providerTypeManager.findAll(index, size);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result  getProviderTypesByName(String name, String order) {
        return providerTypeManager.getProviderTypesByName(name,order);
    }
}
