package models.dao;

import com.avaje.ebean.text.PathProperties;
import models.dao.utils.ListPagerCollection;
import models.domain.Lot;

import java.util.List;

/**
 * Created by drocha on 26/04/17.
 */
public interface LotDao extends AbstractDao<Long, Lot>{

    List<Integer> getExist(String name_lot, int id_farm);
    List<Lot> getByNameLot(String NameLot, String order);
    List<Lot> getByStatusLot(String StatusLot, String order);
    ListPagerCollection findAllSearch(String name, Integer index, Integer size, String sort, PathProperties pathProperties, Integer all, Integer idFarm);
    ListPagerCollection getByIdFarm(Long idFarm, Integer index, Integer size, String sort, PathProperties pathProperties);
}
