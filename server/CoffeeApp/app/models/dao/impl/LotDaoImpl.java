package models.dao.impl;

import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;
import io.ebean.text.PathProperties;
import models.dao.LotDao;
import controllers.utils.ListPagerCollection;
import models.domain.Lot;
import models.manager.responseUtils.PropertiesCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 26/04/17.
 */
public class LotDaoImpl  extends AbstractDaoImpl<Long, Lot> implements LotDao {

   private static FarmDao farmDao = new FarmDaoImpl();

    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public LotDaoImpl(){

        super(Lot.class);
        propertiesCollection.putPropertiesCollection("s", "(idLot, nameLot)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

    public List<Integer> getExist(String name_lot, int id_farm)
    {
        List<Integer> aux = new ArrayList<Integer>();
        Lot lot = find.query().where().eq("name_lot",name_lot).eq("id_farm",id_farm).setMaxRows(1).findUnique(); //findUnique();
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
    public ListPagerCollection findAllSearch(String name, Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties, Integer all, Integer idFarm) {

        ExpressionList expressionList = null;
        if(idFarm.equals(-1)) {
            if (all.equals(1)) expressionList = find.query().where().eq("status_delete", 0);
            else expressionList = find.query().where().eq("status_delete", 0).eq("status_lot", 1);
        }
        else
        {
            if (all.equals(1)) expressionList = find.query().where().eq("status_delete", 0).eq("id_farm",idFarm);
            else expressionList = find.query().where().eq("status_delete", 0).eq("status_lot", 1).eq("id_farm",idFarm);
        }

        if(pathProperties != null)
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.icontains("name_lot", name);

        if(sort != null)
            expressionList.orderBy(AbstractDaoImpl.Sort(sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(), expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findCount(), pageIndex, pageSize);
    }

    @Override
    public  ListPagerCollection  getByIdFarm(Long idFarm, Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties)
    {
        ExpressionList expressionList =  find.query().where().eq("status_delete",0).eq("id_farm",idFarm).eq("status_lot",1);

        if(pathProperties != null)
            expressionList.apply(pathProperties);

        if(sort != null)
            expressionList.orderBy(AbstractDaoImpl.Sort(sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(), expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findCount(), pageIndex, pageSize);
    }
}