package models.dao.impl;

import models.dao.InvoiceDetailDao;
import models.domain.InvoiceDetail;

import java.util.List;

/**
 * Created by drocha on 26/04/17.
 */
public class InvoiceDetailDaoImpl extends AbstractDaoImpl<Long, InvoiceDetail> implements InvoiceDetailDao {

    public InvoiceDetailDaoImpl() {
        super(InvoiceDetail.class);
    }

    @Override
    public List<InvoiceDetail> findAllByIdInvoice(Long IdInvoice)
    {
        return find
                .where()
                .eq("id_invoice", IdInvoice)
                .findList();

    }
}

