package models.dao.impl;

import models.dao.InvoiceDao;
import models.domain.Invoice;
import models.domain.Provider;
import models.manager.requestUtils.Request;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 25/04/17.
 */
public class InvoiceDaoImpl extends AbstractDaoImpl<Long, Invoice> implements InvoiceDao {

    public InvoiceDaoImpl() {
        super(Invoice.class);
    }

    @Override
    public List<Invoice> getByDateByTypeProvider(String date, Integer typeProvider)
    {




        String sql = "SELECT invo.id_invoice AS invo_id, invo.status_invoice as status, invo.duedate_invoice as start_date," +
                " invo.closeddate_invoice as closed_date, invo.total_invoice as total," +
                " prov.id_provider AS prov_id, prov.fullname_provider as full_name," +
                " prov.address_provider as address," +
                " prov.contactname_provider as contact_name, prov.identificationdoc_provider as identification_doc," +
                " prov.phonenumber_provider as phone_number" +
                " FROM invoices invo " +
                " INNER JOIN  providers prov ON prov.id_provider = invo.id_provider " +
                " where invo.duedate_invoice= :date ";

        if(typeProvider!=-1)  sql+=" and prov.id_providertype= :typeProvider ";

        sql+=" and invo.status_delete=0 and prov.status_delete=0 order by prov.fullname_provider ASC ";


        List<SqlRow>  sqlRows = Ebean.createSqlQuery(sql)
                .setParameter("typeProvider", typeProvider)
                .setParameter("date", date)
                .findList();



        return toInvoices(sqlRows);
    }


    @Override
    public List<Invoice> getByDateByProviderId(String date, Long providerId)
    {
      return find.where().eq("id_provider",providerId).eq("start_date_invoice",date).findList();

    }

    @Override
    public Boolean deletedInvoice( Long invoiceId)
    {
       try {
           String sql = " CALL `deletedInvoicesAndInvoicesDetails`(:invoiceId) ";

           SqlQuery query = Ebean.createSqlQuery(sql)
                   .setParameter("invoiceId", invoiceId);

           SqlRow result = query.findUnique();
           return false;
       }catch (Exception e)
       {
        return true;
       }
    }

    public    List<Invoice> getOpenByProviderId(Long providerId)
    {
        return find.where().eq("id_provider",providerId).eq("status_delete",0).findList();
    }

    public List<Invoice> toInvoices(List<SqlRow>  sqlRows)
    {
        List<Invoice> invoices = new ArrayList<>();
        Provider provider;
        Invoice invoice;
        for(int i=0; i < sqlRows.size(); ++i)
        {
            provider = new Provider();
            invoice = new Invoice();
            provider.setContactNameProvider(sqlRows.get(i).getString("contact_name"));
            provider.setFullNameProvider(sqlRows.get(i).getString("full_name"));
            provider.setPhoneNumberProvider(sqlRows.get(i).getString("phone_number"));
            provider.setIdentificationDocProvider(sqlRows.get(i).getString("identification_doc"));
            provider.setAddressProvider(sqlRows.get(i).getString("address"));
            provider.setIdProvider(sqlRows.get(i).getLong("prov_id"));

            invoice.setProvider(provider);
            invoice.setStatusInvoice(sqlRows.get(i).getInteger("status"));
            invoice.setIdInvoice(sqlRows.get(i).getLong("invo_id"));
            invoice.setStartDateInvoice( Request.dateFormatter.parseDateTime(sqlRows.get(i).getString("start_date")));
            invoice.setClosedDateInvoice(Request.dateFormatter.parseDateTime(sqlRows.get(i).getString("closed_date")));
            invoice.setTotalInvoice(sqlRows.get(i).getDouble("total"));

            invoices.add(invoice);
        }

        return invoices;
    }
}

