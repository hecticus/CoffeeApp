package models.dao.impl;

import models.dao.InvoiceDetailPurityDao;
import models.domain.InvoiceDetailPurity;

/**
 * Created by drocha on 26/04/17.
 */
public class InvoiceDetailPurityDaoImpl extends AbstractDaoImpl<Long, InvoiceDetailPurity> implements InvoiceDetailPurityDao {

    public InvoiceDetailPurityDaoImpl() {
        super(InvoiceDetailPurity.class);
    }
}

