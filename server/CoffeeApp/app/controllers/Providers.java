package controllers;

import models.manager.ProviderManager;
import models.manager.impl.ProviderManagerImpl;
import models.manager.requestUtils.queryStringBindable.Pager;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

/**
 * Created by drocha on 25/04/17.
 */
public class Providers
{

    private static ProviderManager providerManager = new ProviderManagerImpl();

    @CoffeAppsecurity
    public Result create() {
        return providerManager.create();
    }

    @CoffeAppsecurity
    public Result update() {
        return providerManager.update();
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        return providerManager.delete(id);
    }

    @CoffeAppsecurity
    public Result findById(Long id) {
        return providerManager.findById(id);
    }

    /*@HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    @CoffeAppsecurity
    public Result findAll(Integer index, Integer size) {
        return providerManager.findAll(index, size);
    }
*/
    @CoffeAppsecurity
    public Result  getByIdentificationDoc(String IdentificationDoc) {
        return providerManager.getByIdentificationDoc(IdentificationDoc);
    }

    @CoffeAppsecurity
    public Result  getProvidersByName(String name, String order) {
        return providerManager.getProvidersByName(name,order);
    }

    @CoffeAppsecurity
    public Result  getByTypeProvider(Long id_providertype, String order) {
        return providerManager.getByTypeProvider(id_providertype,order);
    }

    @CoffeAppsecurity
    public Result getByNameDocByTypeProvider(String nameDoc, Long id_providertype, String order) {
        return providerManager.getByNameDocByTypeProvider(nameDoc, id_providertype, order);
    }

    @CoffeAppsecurity
    public Result findAll(Pager pager, String sort, String collection) {
        return providerManager.findAll(pager.index, pager.size, sort, collection);
    }

    @CoffeAppsecurity
    public Result findAllSearch(String name, Pager pager, String sort, String collection, Integer listaAll, Integer idProviderType ) {
        return providerManager.findAllSearch(name, pager.index, pager.size, sort, collection, listaAll, idProviderType);
    }

    @CoffeAppsecurity
    public Result preCreate() {
        return providerManager.preCreate();
    }

    @CoffeAppsecurity
    public Result deletes() {
        return providerManager.deletes();
    }

    @CoffeAppsecurity
    public Result uploadPhotoProvider() {
        return providerManager.uploadPhotoProvider();
    }

}
