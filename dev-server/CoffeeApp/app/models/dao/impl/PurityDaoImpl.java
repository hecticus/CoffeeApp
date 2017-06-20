package models.dao.impl;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import models.dao.PurityDao;
import models.domain.Purity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 26/04/17.
 */
public class PurityDaoImpl  extends AbstractDaoImpl<Long,Purity> implements PurityDao {

    public PurityDaoImpl() {
        super(Purity.class);
    }

    public int getExist(String name_purity)
    {
        if(find.where().eq("name_purity",name_purity).eq("status_delete",0).findUnique()!=null) return 0;
        else
        {
            if(find.where().eq("name_purity",name_purity).eq("status_delete",1).findUnique()!=null)  return 1;
            else return 2;

        }

    }

    public List<Purity> getByNamePurity(String NamePurity, String order)
    {
        String sql="select t0.id_purity c0, t0.status_delete c1, t0.name_purity c2, t0.status_purity c3," +
                " t0.discountrate_purity c4, t0.created_at c5, t0.updated_at c6 " +
                "from purities t0" +
                " where name_purity like '%"+NamePurity+"%'  and status_delete = 0"+
                " order by t0.name_purity "+order;

        SqlQuery query = Ebean.createSqlQuery(sql);

        List<SqlRow>   results = query.findList();

        return toPuritys(results);
    }

    public List<Purity> getByStatusPurity(String StatusPurity, String order)
    {
        String sql="select t0.id_purity c0, t0.status_delete c1, t0.name_purity c2, t0.status_purity c3, " +
                " t0.discountrate_purity c4, t0.created_at c5, t0.updated_at c6 " +
                " from purities t0 "+
                " where t0.status_delete=0 ";

        if(!StatusPurity.equals("-1"))  sql+=" and t0.status_purity= "+StatusPurity;

        sql+=" order by t0.name_purity "+order;

        SqlQuery query = Ebean.createSqlQuery(sql);

        List<SqlRow>   results = query.findList();

        return toPuritys(results);
    }

    public List<Purity> toPuritys(List<SqlRow>  sqlRows)
    {
        List<Purity> purities = new ArrayList<>();

        Purity purity;
        for(int i=0; i < sqlRows.size(); ++i)
        {
            purity = new Purity();

            purity.setIdPurity(sqlRows.get(i).getLong("c0"));
            purity.setStatusDelete(sqlRows.get(i).getInteger("c1"));
            purity.setNamePurity(sqlRows.get(i).getString("c2"));
            purity.setStatusPurity(sqlRows.get(i).getInteger("c3"));
            purity.setDiscountRatePurity(sqlRows.get(i).getInteger("c4"));
            purities.add(purity);
        }

        return purities;
    }
}
