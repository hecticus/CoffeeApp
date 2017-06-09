package models.dao.impl;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import models.dao.RoleDao;
import models.domain.Role;
import models.domain.SecurityRoute;
import models.domain.User;

import java.util.List;

/**
 * Created by yenny on 9/30/16.
 */
public class RoleDaoImpl extends AbstractDaoImpl<Long, Role> implements RoleDao {


    public RoleDaoImpl() {
        super(Role.class);
    }

    ///Fucnion Ultra sensual que permitira fillear dese lleno
    @Override
    public boolean AccessResource(User user, String route)
    {
        Role rol = user.getRole();
        StringBuilder sql = new StringBuilder();
        sql.append(
                "SELECT m.id " +
                        "FROM route As m "+
                        "INNER JOIN route_tag mm ON mm.route_id = m.id "+
                        "INNER JOIN tag mb ON mb.id = mm.tag_id "+
                        "INNER JOIN tag_role mt ON mt.tag_id = mb.id  WHERE  mt.role_id= :roleId " +
                        "AND m.name LIKE :routeName"
        );

        RawSql rawSql = RawSqlBuilder
                .parse(sql.toString())
                .create();

        List<SecurityRoute> securityRoutes = Ebean.find(SecurityRoute.class)
                .setRawSql(rawSql)
                .setParameter("roleId", rol.getIdRole())
                .setParameter("routeName", route)
                .findList();
        return securityRoutes.size()> 0;
    }



}
