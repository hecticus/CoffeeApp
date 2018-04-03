package models.dao.impl;

import io.ebean.Ebean;
import io.ebean.RawSql;
import io.ebean.RawSqlBuilder;
import models.dao.SecurityRouteDao;
import models.dao.SecurityTagDao;
import models.domain.Role;
import models.domain.SecurityRoute;
import models.domain.User;

import java.util.Calendar;
import java.util.List;

/**
 * Created by yenny on 9/30/16.
 */
public class SecurityRouteDaoImpl extends AbstractDaoImpl<Long, SecurityRoute> implements SecurityRouteDao {

    public SecurityRouteDaoImpl() {
        super(SecurityRoute.class);
    }

    ///Fucnion Ultra sensual que permitira fillear dese lleno
    @Override
    public SecurityRoute CheckAndInsert(String resourceName, int type)
    {
        List<SecurityRoute> sroutes = find.query()
                .where()
                .eq("name", resourceName)
                .findList();

        if(!sroutes.isEmpty())
            return sroutes.get(0);

        SecurityRoute obj =  new SecurityRoute();
        obj.setName(resourceName);
        obj.setDescription("AUTOMATIC ADD");
        /// quitar en debug
        SecurityTagDao securityTagDao = new SecurityTagDaoImpl();
        obj.getSecurityTag().add(securityTagDao.GetTagByName("basic"));
        obj.setType(type);
        obj.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
        obj.setUpdatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
        obj.save();
        //String name = new Object(){}.getClass().getEnclosingMethod().getName();
        return obj;
    }

    @Override
    public List<SecurityRoute> GetMenu(User user)
    {
        Role rol = user.getRole();
        StringBuilder sql = new StringBuilder();
        sql.append(
                "SELECT m.id " +
                        "FROM route As m "+
                        "INNER JOIN route_tag mm ON mm.route_id = m.id "+
                        "INNER JOIN tag mb ON mb.id = mm.tag_id "+
                        "INNER JOIN tag_role mt ON mt.tag_id = mb.id  WHERE  mt.role_id= :roleId AND m.route_type = 1"
        );

        RawSql rawSql = RawSqlBuilder
                .parse(sql.toString())
                .create();

        List<SecurityRoute> securityRoutes = Ebean.find(SecurityRoute.class)
                .setRawSql(rawSql)
                .setParameter("roleId", rol.getIdRole())
                .findList();
        return securityRoutes;
    }
}
