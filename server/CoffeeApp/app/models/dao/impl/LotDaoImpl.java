package models.dao.impl;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.text.PathProperties;
import models.dao.FarmDao;
import models.dao.LotDao;
import models.dao.utils.ListPagerCollection;
import models.domain.Lot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 26/04/17.
 */
public class LotDaoImpl  extends AbstractDaoImpl<Long, Lot> implements LotDao {

    public LotDaoImpl() {
        super(Lot.class);
    }
    private static FarmDao farmDao = new FarmDaoImpl();

    public List<Integer> getExist(String name_lot, int id_farm)
    {
        List<Integer> aux = new ArrayList<Integer>();
        Lot lot = find.where().eq("name_lot",name_lot).eq("id_farm",id_farm).findUnique();
        if(lot==null) aux.add(0,-1);
        else
        {
           aux.add(0,lot.getStatusDelete());
           aux.add(1,Integer.parseInt(lot.getIdLot().toString()));
        }
        return aux;
    }

    public List<Lot> getByNameLot(String NameLot, String order)
    {
        String sql="select t0.id_lot c0, t0.status_delete c1, t0.name_lot c2," +
                " t0.area_lot c3, t0.id_farm c4, t0.heigh_lot c5, t0.created_at c6, " +
                "t0.updated_at c7 from lots t0 " +
                "where t0.status_delete= 0 and t0.name_lot like '%"+NameLot+"%' "+
                " order by t0.name_lot "+order;

        SqlQuery query = Ebean.createSqlQuery(sql);

        List<SqlRow>   results = query.findList();

        return toLots(results);
    }

    public List<Lot> getByStatusLot(String StatusLot, String order)
    {
        String sql="select t0.id_lot c0, t0.status_delete c1, t0.name_lot c2," +
                " t0.area_lot c3, t0.id_farm c4, t0.heigh_lot c5, t0.created_at c6, " +
                "t0.updated_at c7 from lots t0 ";

        if(!StatusLot.equals("-1"))  sql+="where t0.status_delete= "+StatusLot;

        sql+=" order by t0.name_lot "+order;

        SqlQuery query = Ebean.createSqlQuery(sql);

        List<SqlRow>   results = query.findList();

        return toLots(results);
    }

    public List<Lot> toLots(List<SqlRow>  sqlRows)
    {
        List<Lot> lots = new ArrayList<>();

        Lot lot;
        for(int i=0; i < sqlRows.size(); ++i)
        {
            lot = new Lot();

            lot.setIdLot(sqlRows.get(i).getLong("c0"));
            lot.setStatusDelete(sqlRows.get(i).getInteger("c1"));
            lot.setNameLot(sqlRows.get(i).getString("c2"));
            lot.setAreaLot(sqlRows.get(i).getString("c3"));
            lot.setFarm(farmDao.findById(sqlRows.get(i).getLong("c4")));
            lot.setHeighLot(sqlRows.get(i).getDouble("c5"));
            lots.add(lot);
        }

        return lots;
    }

    @Override
    public ListPagerCollection findAllSearch(String name, Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties) {
        ExpressionList expressionList = find.where().eq("status_delete",0);

        if(pathProperties != null)
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.icontains("name_lot", name);

        if(sort != null)
            expressionList.orderBy(AbstractDaoImpl.Sort(sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(expressionList.findPagedList(pageIndex, pageSize).getList(), expressionList.findRowCount(), pageIndex, pageSize);
    }
}