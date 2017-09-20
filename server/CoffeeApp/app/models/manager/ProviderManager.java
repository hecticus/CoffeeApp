package models.manager;

import play.mvc.Result;

import java.util.List;

/**
 * Created by drocha on 25/04/17.
 */
public interface ProviderManager {

    public Result create();

    public Result update();

    public Result delete(Long id);

    public Result findById(Long id);

   // public Result findAll(Integer index, Integer size);

    public Result  getByIdentificationDoc(String IdentificationDoc);

    public Result  getProvidersByName(String name, String order);

    public Result  getByTypeProvider(Long id_providertype, String order);

    public Result getByNameDocByTypeProvider(String nameDoc, Long id_providertype, String order);

    Result findAll(Integer index, Integer size, String sort, String collection);

    Result findAllSearch(String name, Integer index, Integer size, String sort, String collection);

    Result preCreate();

    Result deletes();

    Result uploadPhotoProvider();


}
