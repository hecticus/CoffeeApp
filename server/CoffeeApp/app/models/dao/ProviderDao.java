package models.dao;

/**
 * Created by drocha on 25/04/17.
 */

import io.ebean.text.PathProperties;
import models.dao.utils.ListPagerCollection;
import models.domain.Provider;

import java.util.List;
import java.util.List;

public interface ProviderDao extends AbstractDao<Long, Provider>{

    Provider getByIdentificationDoc(String IdentificationDoc);
    List<Provider> getProvidersByName(String name, String order);
    List<Provider> getByTypeProvider(Long typeProvider, String order);
    //nameDoc: busca por nombre o por documento, es un solo parametro pero peude traer un valor para buscar por name o por iddoc
    List<Provider> getByNameDocByTypeProvider(String nameDoc,Long typeProvider, String order);

    List<Integer>  getExist(String IdentificationDoc);

    ListPagerCollection findAllSearch(String name, Integer index, Integer size, String sort, PathProperties pathProperties, boolean all, Integer listALL, boolean
            inside, Integer idProviderType);

    String uploadPhoto(String base64Photo, String ext);

}
