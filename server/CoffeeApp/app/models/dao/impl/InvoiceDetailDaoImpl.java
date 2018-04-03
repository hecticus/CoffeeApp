package models.dao.impl;

import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.SqlQuery;
import io.ebean.SqlUpdate;
import io.ebean.SqlRow;
import io.ebean.text.PathProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.dao.InvoiceDetailDao;
import models.dao.utils.ListPagerCollection;
import models.domain.InvoiceDetail;
import play.libs.Json;

import java.util.ArrayList;
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
        return find.query()
                .where()
                .eq("id_invoice", IdInvoice)
                .eq("status_delete",0)
                .orderBy("duedate_invoicedetail")
                .findList();

    }

    public    List<InvoiceDetail> getOpenByItemTypeId( Long idItemType)
    {
        return find.query().where().eq("id_itemtype",idItemType).eq("status_delete",0).findList();
    }

    public    List<InvoiceDetail> getOpenByLotId( Long idLot)
    {
        return find.query().where().eq("id_lot",idLot).eq("status_delete",0).findList();
    }

    public    List<InvoiceDetail> getOpenByStoreId( Long idStore)
    {
        return find.query().where().eq("id_store",idStore).eq("status_delete",0).findList();
    }

    @Override
    public ListPagerCollection findAllSearch(Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties) {
        ExpressionList expressionList = find.query().where().eq("status_delete",0);

        if(pathProperties != null)
            expressionList.apply(pathProperties);


        if(sort != null)
            expressionList.orderBy(AbstractDaoImpl.Sort(sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(), expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findCount(), pageIndex, pageSize);
    }

    @Override
    public JsonNode findAllByIdInvoiceSummary(Long idInvoice)
    {
        ObjectNode aux;
        List<ObjectNode> result = new ArrayList<>();
        String sql="SELECT duedate_invoicedetail as c0, SUM(amount_invoicedetail) as c1 " +
                "FROM invoice_details " +
                "where id_invoice=:idInvoice " +
                "and status_delete=0 "+
                "group by duedate_invoicedetail " +
                "order by duedate_invoicedetail";

        SqlQuery query = Ebean.createSqlQuery(sql).setParameter("idInvoice", idInvoice);

        List<SqlRow> results = query.findList();



        for(int i=0; i < results.size(); ++i)
        {
            aux= Json.newObject();

            aux.put("startDateInvoiceDetail",results.get(i).getTimestamp("c0").toString());
            aux.put("amountTotal",results.get(i).getString("c1"));

            result.add(aux);

        }



        return Json.toJson(result);
    }

    @Override
    public int deleteAllByIdInvoiceAndDate(Long idInvoice, String date)
    {

        date = "'"+date+"'";

        String sql="update invoice_details " +
                "set status_delete=1 " +
                "where duedate_invoicedetail= "+date+"  and id_invoice= "+idInvoice+";";

        SqlUpdate query = Ebean.createSqlUpdate(sql);

        return query.execute();

    }
}

