package controllers;

import models.manager.ProviderManager;
import models.manager.impl.ProviderManagerImpl;
import models.manager.requestUtils.queryStringBindable.Pager;
import play.mvc.Result;
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

    /*@HSecurity("/user/verify/@Ordenes,Reportes,SuperUsuario,Basic")
    public Result findAll(Integer index, Integer size) {
        return providerManager.findAll(index, size);
    }
*/
    public Result  getByIdentificationDoc(String IdentificationDoc) {
        return providerManager.getByIdentificationDoc(IdentificationDoc);
    }

    public Result  getProvidersByName(String name, String order) {
        return providerManager.getProvidersByName(name,order);
    }

    public Result  getByTypeProvider(Long id_providertype, String order) {
        return providerManager.getByTypeProvider(id_providertype,order);
    }

    public Result getByNameDocByTypeProvider(String nameDoc, Long id_providertype, String order) {
        return providerManager.getByNameDocByTypeProvider(nameDoc, id_providertype, order);
    }

    public Result findAll(Pager pager, String sort, String collection) {
        return providerManager.findAll(pager.index, pager.size, sort, collection);
    }

    public Result findAllSearch(String name, Pager pager, String sort, String collection, Integer listaAll, Integer idProviderType ) {
        return providerManager.findAllSearch(name, pager.index, pager.size, sort, collection, listaAll, idProviderType);
    }

    public Result preCreate() {
        return providerManager.preCreate();
    }

    public Result deletes() {
        return providerManager.deletes();
    }

    public Result uploadPhotoProvider() {
        return providerManager.uploadPhotoProvider();
    }

}
