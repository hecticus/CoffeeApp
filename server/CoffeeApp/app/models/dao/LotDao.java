package models.dao;

import models.domain.Lot;

import java.util.List;

/**
 * Created by drocha on 26/04/17.
 */
public interface LotDao extends AbstractDao<Long, Lot>{

    int getExist(String name);
    List<Lot> getByNameLot(String NameLot, String order);
    List<Lot> getByStatusLot(String StatusLot, String order);
}
