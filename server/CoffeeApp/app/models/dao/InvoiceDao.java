package models.dao;

/**
 * Created by drocha on 25/04/17.
 */

import com.avaje.ebean.text.PathProperties;
import models.dao.utils.ListPagerCollection;
import models.domain.Invoice;

import java.util.List;

public interface InvoiceDao extends AbstractDao<Long, Invoice>
{
    List<Invoice> getByDateByTypeProvider(String date, Integer typeProvider);
    List<Invoice> getByDateByProviderId(String date, Long providerId);
    List<Invoice> getOpenByProviderId(Long providerId);
    Boolean deletedInvoice( Long invoiceId);
    ListPagerCollection findAllSearch(Integer index, Integer size, String sort, PathProperties pathProperties);
}

