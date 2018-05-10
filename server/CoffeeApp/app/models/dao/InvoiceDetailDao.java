package models.dao;

import io.ebean.text.PathProperties;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.ListPagerCollection;
import models.domain.InvoiceDetail;

import java.util.List;

/**
 * Created by drocha on 26/04/17.
 */
public interface InvoiceDetailDao extends AbstractDao<Long, InvoiceDetail>{

    List<InvoiceDetail>  findAllByIdInvoice( Long IdInvoice);

    List<InvoiceDetail>  getOpenByItemTypeId( Long idItemType);

    List<InvoiceDetail>  getOpenByLotId( Long idLot);

    List<InvoiceDetail>  getOpenByStoreId( Long idStore);
    ListPagerCollection findAllSearch(Integer index, Integer size, String sort, PathProperties pathProperties);

    JsonNode findAllByIdInvoiceSummary(Long idInvoice);

    int  deleteAllByIdInvoiceAndDate( Long IdInvoice, String  date);
}

