package models.dao;

/**
 * Created by drocha on 25/04/17.
 */

import io.ebean.text.PathProperties;
import controllers.utils.ListPagerCollection;
import models.Invoice;

import java.util.List;

public interface InvoiceDao extends AbstractDao<Long, Invoice>
{
   // List<Invoice>
    ListPagerCollection getByDateByTypeProvider(String date, Integer typeProvider, Integer pageIndex, Integer pagesize);
    List<Invoice> getByDateByProviderId(String date, Long providerId);
    List<Invoice> getOpenByProviderId(Long providerId);
    Boolean deletedInvoice( Long invoiceId);
    ListPagerCollection findAllSearch(Integer index, Integer size, String sort, PathProperties pathProperties,Integer id_provider,Integer id_providertype,String startDate, String endDate);
    double calcularTotalInvoice(Long idInvioce);
}

