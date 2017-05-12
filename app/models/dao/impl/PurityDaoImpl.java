package models.dao.impl;

import models.dao.PurityDao;
import models.domain.Purity;

/**
 * Created by drocha on 26/04/17.
 */
public class PurityDaoImpl  extends AbstractDaoImpl<Long,Purity> implements PurityDao {

    public PurityDaoImpl() {
        super(Purity.class);
    }
}
