package models.dao;

import models.domain.InvoiceDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 26/04/17.
 */
public interface InvoiceDetailDao extends AbstractDao<Long, InvoiceDetail>{

    List<InvoiceDetail>  findAllByIdInvoice( Long IdInvoice);

    List<InvoiceDetail>  getOpenByItemTypeId( Long idItemType);

    List<InvoiceDetail>  getOpenByLotId( Long idLot);

    List<InvoiceDetail>  getOpenByStoreId( Long idStore);

}

