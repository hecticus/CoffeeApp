package models.dao.impl;

import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;
import io.ebean.text.PathProperties;
import models.dao.ItemTypeDao;
import models.dao.UnitDao;
import models.dao.utils.ListPagerCollection;
import models.domain.ItemType;
import models.manager.requestUtils.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 25/04/17.
 */

public class ItemTypeDaoImpl extends AbstractDaoImpl<Long, ItemType> implements ItemTypeDao {

    public ItemTypeDaoImpl() {
        super(ItemType.class);
    }
    private static UnitDao unitDao = new UnitDaoImpl();

    public int getExist(String name_itemtype)
    {
       if(find.query().where().eq("name_itemtype",name_itemtype).eq("status_delete",0).findUnique()!=null) return 0;
       else
       {
           if(find.query().where().eq("name_itemtype",name_itemtype).eq("status_delete",1).findUnique()!=null)  return 1;
           else return 2;

       }

    }

    public List<ItemType> getOpenByUnitId(Long idUnit)
    {
        return find.query().where().eq("id_unit",idUnit).eq("status_delete",0).findList();
    }

    public List<ItemType> getOpenByProviderTypeId(Long idProviderType)
    {
        return find.query().where().eq("id_providertype",idProviderType).eq("status_delete",0).findList();
    }

    public List<ItemType> getByProviderTypeId(Long idProviderType, Integer status)
    {
        String sql="SELECT item.id_itemtype  c0, item.status_delete c1, item.name_itemtype   c2, " +
                " item.cost_itemtype c3, item.status_itemtype c4, item.created_at c5, " +
                " item.updated_at c6, item.id_providertype c7, item.id_unit c8  " +
                " FROM item_types item "+
                " inner join provider_type protype on protype.id_providertype=item.id_providertype " +
                " where item.status_delete=0  and item.id_providertype= :idprovidertype ";

        if(status!=-1) sql+= " and protype.status_delete=:status ";

        SqlQuery query = Ebean.createSqlQuery(sql)
                .setParameter("idprovidertype", idProviderType )
                .setParameter("status",status);

        List<SqlRow>   results = query.findList();

return toItemTypes(results);
    }

    public List<ItemType> getByNameItemType(String NameItemType, String order)
    {
        String sql="SELECT item.id_itemtype  c0, item.status_delete c1, item.name_itemtype   c2, " +
                " item.cost_itemtype c3, item.status_itemtype c4, item.created_at c5, " +
                " item.updated_at c6, item.id_providertype c7, item.id_unit c8  " +
                " FROM item_types item "+
                " where item.status_delete=0  and item.name_itemtype like '%"+NameItemType+"%' "+
                " order by item.id_itemtype   "+order;

        SqlQuery query = Ebean.createSqlQuery(sql);

        List<SqlRow>   results = query.findList();

        return toItemTypes(results);
    }

    public List<ItemType> toItemTypes(List<SqlRow>  sqlRows)
    {
        List<ItemType> itemTypes = new ArrayList<>();

        ItemType itemType;
        for(int i=0; i < sqlRows.size(); ++i)
        {
            itemType = new ItemType();

            itemType.setIdItemType(sqlRows.get(i).getLong("c0"));
            itemType.setNameItemType(sqlRows.get(i).getString("c2"));
            itemType.setCostItemType(sqlRows.get(i).getFloat("c3"));
            itemType.setUnit(unitDao.findById(sqlRows.get(i).getLong("c8")));
            itemType.setStatusItemType(sqlRows.get(i).getInteger("c4"));
            itemTypes.add(itemType);
        }

        return itemTypes;
    }

    @Override
    public ListPagerCollection findAllSearch(String name, Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties) {
        ExpressionList expressionList = find.query().where().eq("status_delete",0);

        if(pathProperties != null)
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.icontains("name_itemtype", name);

        if(sort != null)
            expressionList.orderBy(AbstractDaoImpl.Sort(sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(), expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findCount(), pageIndex, pageSize);
    }
}