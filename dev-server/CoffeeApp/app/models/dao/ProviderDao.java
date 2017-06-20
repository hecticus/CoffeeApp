package models.dao;

/**
 * Created by drocha on 25/04/17.
 */

import models.domain.Provider;

import java.util.List;
import java.util.List;

public interface ProviderDao extends AbstractDao<Long, Provider>{

    Provider getByIdentificationDoc(String IdentificationDoc);
    List<Provider> getProvidersByName(String name, String order);
    List<Provider> getByTypeProvider(Long typeProvider, String order);
    //nameDoc: busca por nombre o por documento, es un solo parametro pero peude traer un valor para buscar por name o por iddoc
    List<Provider> getByNameDocByTypeProvider(String nameDoc,Long typeProvider, String order);

    int getExist(String IdentificationDoc);



}
