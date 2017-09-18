package models.dao.impl;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.text.PathProperties;
import models.dao.StoreDao;
import models.dao.utils.ListPagerCollection;
import models.domain.Store;
import models.domain.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 12/05/17.
 */
public class StoreDaoImpl  extends AbstractDaoImpl<Long,Store> implements StoreDao {

    public StoreDaoImpl() {
        super(Store.class);
    }

    public int getExist(String name_store)
    {
        if(find.where().eq("name_store",name_store).eq("status_delete",0).findUnique()!=null) return 0;
        else
        {
            if(find.where().eq("name_store",name_store).eq("status_delete",1).findUnique()!=null)  return 1;
            else return 2;

        }

    }

    public List<Store> getByStatusStore(String StatusStore, String order)
    {
        String sql="select t0.id_store c0, t0.status_delete c1, t0.name_store c2, t0.status_store c3, " +
                " t0.created_at c5, t0.updated_at c6 " +
                " from stores t0 "+
                " where t0.status_delete=0 ";

        if(!StatusStore.equals("-1"))  sql+=" and t0.status_store= "+StatusStore;

        sql+=" order by t0.name_Store "+order;

        SqlQuery query = Ebean.createSqlQuery(sql);

        List<SqlRow>   results = query.findList();

        return toStores(results);
    }

    public List<Store> toStores(List<SqlRow>  sqlRows)
    {
        List<Store> stores = new ArrayList<>();

        Store Store;
        for(int i=0; i < sqlRows.size(); ++i)
        {
            Store = new Store();

            Store.setIdStore(sqlRows.get(i).getLong("c0"));
            Store.setStatusDelete(sqlRows.get(i).getInteger("c1"));
            Store.setNameStore(sqlRows.get(i).getString("c2"));
            Store.setStatusStore(sqlRows.get(i).getInteger("c3"));

            stores.add(Store);
        }

        return stores;
    }

    @Override
    public ListPagerCollection findAllSearch(String name, Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties) {
        ExpressionList expressionList = find.where().eq("status_delete",0);

        if(pathProperties != null)
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.icontains("name_store", name);

        if(sort != null)
            expressionList.orderBy(AbstractDaoImpl.Sort(sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(expressionList.findPagedList(pageIndex, pageSize).getList(), expressionList.findRowCount(), pageIndex, pageSize);
    }
}
