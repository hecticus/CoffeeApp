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

    @Override
    public InvoiceDetailPurity getByIdInvopiceDetailsByIdPurity(Long IdInvopiceDetail, Long IdPurity)
    {
        return find.where().eq("id_invoicedetail",IdInvopiceDetail).eq("id_purity",IdPurity).eq("status_delete",0).findUnique();
    }
}

