package models.dao.impl;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.text.PathProperties;
import models.dao.InvoiceDetailDao;
import models.dao.utils.ListPagerCollection;
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

    public    List<InvoiceDetail> getOpenByItemTypeId( Long idItemType)
    {
        return find.where().eq("id_itemtype",idItemType).eq("status_delete",0).findList();
    }

    public    List<InvoiceDetail> getOpenByLotId( Long idLot)
    {
        return find.where().eq("id_lot",idLot).eq("status_delete",0).findList();
    }

    public    List<InvoiceDetail> getOpenByStoreId( Long idStore)
    {
        return find.where().eq("id_store",idStore).eq("status_delete",0).findList();
    }

    @Override
    public ListPagerCollection findAllSearch(Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties) {
        ExpressionList expressionList = find.where().eq("status_delete",0);

        if(pathProperties != null)
            expressionList.apply(pathProperties);


        if(sort != null)
            expressionList.orderBy(AbstractDaoImpl.Sort(sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(expressionList.findPagedList(pageIndex, pageSize).getList(), expressionList.findRowCount(), pageIndex, pageSize);
    }
}

