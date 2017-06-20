package controllers;

import models.Security.HSecurity;
import play.mvc.Result;
import models.manager.ProviderManager;
import models.manager.impl.ProviderManagerImpl;
/**
 * Created by drocha on 25/04/17.
 */
public class Providers
{

    private static ProviderManager providerManager = new ProviderManagerImpl();

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result create() {
        return providerManager.create();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result update() {
        return providerManager.update();
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result delete(Long id) {
        return providerManager.delete(id);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findById(Long id) {
        return providerManager.findById(id);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findAll(Integer index, Integer size) {
        return providerManager.findAll(index, size);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result  getByIdentificationDoc(String IdentificationDoc) {
        return providerManager.getByIdentificationDoc(IdentificationDoc);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result  getProvidersByName(String name, String order) {
        return providerManager.getProvidersByName(name,order);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result  getByTypeProvider(Long id_providertype, String order) {
        return providerManager.getByTypeProvider(id_providertype,order);
    }

    @HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result getByNameDocByTypeProvider(String nameDoc, Long id_providertype, String order) {
        return providerManager.getByNameDocByTypeProvider(nameDoc, id_providertype, order);
    }

}
