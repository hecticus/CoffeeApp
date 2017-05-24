package models.dao.impl;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import models.dao.LotDao;
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

    public int getExist(String name_item_type)
    {
        if(find.where().eq("name_lot",name_item_type).eq("status_delete",0).findUnique()!=null) return 0;
        else
        {
            if(find.where().eq("name_lot",name_item_type).eq("status_delete",1).findUnique()!=null)  return 1;
            else return 2;

        }

    }

    public List<Lot> getByNameLot(String NameLot, String order)
    {
        String sql="select t0.id_lot c0, t0.status_delete c1, t0.name_lot c2," +
                " t0.area_lot c3, t0.farm_lot c4, t0.heigh_lot c5, t0.created_at c6, " +
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
                " t0.area_lot c3, t0.farm_lot c4, t0.heigh_lot c5, t0.created_at c6, " +
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

            lot.setId(sqlRows.get(i).getLong("c0"));
            lot.setStatusDelete(sqlRows.get(i).getInteger("c1"));
            lot.setName(sqlRows.get(i).getString("c2"));
            lot.setArea(sqlRows.get(i).getString("c3"));
            lot.setFarm(sqlRows.get(i).getString("c4"));
            lot.setHeigh(sqlRows.get(i).getDouble("c5"));
            lots.add(lot);
        }

        return lots;
    }
}