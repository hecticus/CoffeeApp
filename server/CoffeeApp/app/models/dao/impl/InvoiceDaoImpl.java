package models.dao.impl;

import io.ebean.ExpressionList;
import io.ebean.text.PathProperties;
import models.dao.InvoiceDao;
import models.dao.InvoiceDetailDao;
import models.dao.LotDao;
import controllers.utils.ListPagerCollection;
import models.Invoice;
import models.domain.InvoiceDetail;
import models.domain.Lot;
import models.domain.Provider;
import models.manager.requestUtils.Request;
import io.ebean.Ebean;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;
import models.manager.responseUtils.Response;

import java.util.ArrayList;
import java.util.List;


import java.util.Date;
import java.text.SimpleDateFormat;
/**
 * Created by drocha on 25/04/17.
 */
public class InvoiceDaoImpl extends AbstractDaoImpl<Long, Invoice> implements InvoiceDao {

    private static InvoiceDetailDao invoiceDetailDao = new InvoiceDetailDaoImpl();
    private static LotDao lotDao = new LotDaoImpl();
    public InvoiceDaoImpl() {
        super(Invoice.class);
    }

    @Override
   // public  List<Invoice>  getByDateByTypeProvider(String date, Integer typeProvider, Integer pageIndex, Integer pagesize)
    public ListPagerCollection  getByDateByTypeProvider(String date, Integer typeProvider, Integer pageIndex, Integer pagesize)
    {




        String sql = "SELECT invo.id_invoice AS invo_id, invo.status_invoice as status, invo.duedate_invoice as start_date, " +
                " invo.closeddate_invoice as closed_date, invo.total_invoice as total," +
                " prov.id_provider AS prov_id, prov.fullname_provider as full_name," +
                " prov.address_provider as address," +
                " prov.contactname_provider as contact_name, prov.identificationdoc_provider as identification_doc," +
                " prov.phonenumber_provider as phone_number, prov.photo_provider as photo_provider" +
                " FROM invoices invo " +
                " INNER JOIN  providers prov ON prov.id_provider = invo.id_provider " +
                " where invo.duedate_invoice like '"+date+"%'";

        if(typeProvider!=-1)  sql+=" and prov.id_providertype= :typeProvider ";

        sql+=" and invo.status_delete=0 and prov.status_delete=0 order by prov.fullname_provider ASC ";

    //    sql += " limit :pageIndex, :pagesize";


        List<SqlRow>  sqlRows = Ebean.createSqlQuery(sql)
                .setParameter("typeProvider", typeProvider)
                .setParameter("date", date)
                .setParameter("pageIndex",pageIndex)
                .setParameter("pagesize",pagesize)
                .findList();

        List<Invoice> invoiceList =toInvoices(sqlRows);

        return new ListPagerCollection(invoiceList.subList(pageIndex,Math.min(pageIndex+pagesize, invoiceList.size())), invoiceList.size(), pageIndex, pagesize);
        //return toInvoices(sqlRows)
    }


    @Override
    public List<Invoice> getByDateByProviderId(String date, Long providerId)
    {
      return find.query().where().eq("id_provider",providerId).eq("dueDate_invoice",date).findList();

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
        return find.query().where().eq("id_provider",providerId)
                .eq("status_delete",0)
                .eq("status_invoice",1)
                .orderBy("dueDate_invoice desc")
                .findList();
    }

    public List<Invoice> toInvoices(List<SqlRow>  sqlRows)
    {
        List<Invoice> invoices = new ArrayList<>();
        Provider provider;
        Invoice invoice;
        try {
            for (int i = 0; i < sqlRows.size(); ++i) {
                provider = new Provider();
                invoice = new Invoice();
                provider.setContactNameProvider(sqlRows.get(i).getString("contact_name"));
                provider.setFullNameProvider(sqlRows.get(i).getString("full_name"));
                provider.setPhoneNumberProvider(sqlRows.get(i).getString("phone_number"));
                provider.setIdentificationDocProvider(sqlRows.get(i).getString("identification_doc"));
                provider.setAddressProvider(sqlRows.get(i).getString("address"));
                provider.setIdProvider(sqlRows.get(i).getLong("prov_id"));
                provider.setPhotoProvider(sqlRows.get(i).getString("photo_provider"));

                invoice.setProvider(provider);
                invoice.setStatusInvoice(sqlRows.get(i).getInteger("status"));
                invoice.setIdInvoice(sqlRows.get(i).getLong("invo_id"));
                //invoice.setStartDateInvoice(Request.dateTimeFormatter.parseDateTime(sqlRows.get(i).getString("start_date")));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s");
                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = sdf.parse(sqlRows.get(i).getString("start_date"));
                String formattedTime = output.format(d);

                invoice.setStartDateInvoice(Request.dateTimeFormatter.parseDateTime(formattedTime));


                 d = sdf.parse(sqlRows.get(i).getString("closed_date"));
                formattedTime = output.format(d);

                invoice.setClosedDateInvoice(Request.dateTimeFormatter.parseDateTime(formattedTime));
                invoice.setTotalInvoice(sqlRows.get(i).getDouble("total"));

                invoices.add(invoice);
            }
        }   catch(Exception e){
            Throwable eRoot = Response.getCause(e);
            eRoot.printStackTrace();
            return null;
        }

        return invoices;
    }

    @Override
    public ListPagerCollection findAllSearch(Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties,Integer id_provider,Integer id_providertype,String startDate, String endDate)
    {
        ExpressionList expressionList;
        try
        {

            SimpleDateFormat Datetemp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(startDate.equals(""))
            {
                startDate = "1970-01-01";
            }

            if(endDate.equals(""))
            {
                endDate="5000-01-01";
            }
            String startDateTemp = startDate.concat(" 00:00:00");
            String endDateTemp = endDate.concat(" 23:59:59");
           // Date startDateTemp = Datetemp.parse(startDate);
           // Date endDateTemp = Datetemp.parse(endDate);

           if(!id_provider.equals(-2))
           {
               //to invoices t1 provider
              if(!id_provider.equals(-1)) expressionList = find.query().fetch("provider").where().eq("t0.status_delete", 0).eq("t0.id_provider",id_provider).between("t0.duedate_invoice",startDateTemp,endDateTemp);
              else
              { expressionList = find.query()
                      .fetch("provider")
                      .where()
                      .eq("t1.id_providertype",id_providertype)
                      .eq("t0.status_delete",0)
                      .between("duedate_invoice",startDateTemp,endDateTemp);

              }

              if (pathProperties != null)
                   expressionList.apply(pathProperties);

              if (sort != null)
                   expressionList.orderBy(AbstractDaoImpl.Sort(sort));

              if (pageIndex == null || pageSize == null)
                   return new ListPagerCollection(expressionList.findList());

              //to invoices t1 provider
              int findRowCount = expressionList.eq("t1.id_providertype",id_providertype).eq("t0.status_delete",0).findList().size();

              return new ListPagerCollection(expressionList.eq("t1.id_providertype",id_providertype).eq("t0.status_delete",0).setFirstRow(pageIndex).setMaxRows(pageSize).findList(), findRowCount, pageIndex, pageSize);
           }
        }
        catch(Exception e)
        {
            Throwable eRoot = Response.getCause(e);
            eRoot.printStackTrace();

        }

        expressionList = find.query().where().eq("status_delete", -2);
        return new ListPagerCollection(expressionList.findList());

    }

    @Override
   public double calcularTotalInvoice(Long idInvioce)
    {
        List<InvoiceDetail> invoiceDetails = invoiceDetailDao.findAllByIdInvoice(idInvioce);
        Lot lot;
        Invoice invoice = this.findById(idInvioce);
        double total = 0;
        boolean harvest = true;
        if(invoice.getProvider().getProviderType().getNameProviderType().toUpperCase().equals("VENDEDOR")) harvest=false;

        for(InvoiceDetail invoiceDetail: invoiceDetails)
        {
            if (!harvest) total=total+(invoiceDetail.getCostItemType()*invoiceDetail.getAmountInvoiceDetail());
            else
            {
                lot = lotDao.findById(invoiceDetail.getLot().getIdLot());
                total=total+(lot.getPrice_lot()*invoiceDetail.getAmountInvoiceDetail());
            }
        }

        return total;
    }



}

