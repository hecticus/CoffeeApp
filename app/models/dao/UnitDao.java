package models.dao;

/**
 * Created by drocha on 25/04/17.
 */
import models.domain.Unit;
public interface UnitDao  extends AbstractDao<Long, Unit>{

    int getExist(String name);
}
