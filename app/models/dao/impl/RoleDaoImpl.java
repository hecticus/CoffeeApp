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
                "SELECT m.id_security_route " +
                        "FROM route As m "+
                        "INNER JOIN route_tag mm ON mm.route_id_security_route  = m.id_security_route "+
                        "INNER JOIN tag mb ON mb.id_security_tag = mm.tag_id_security_tag "+
                        "INNER JOIN tag_role mt ON mt.tag_id_security_tag  = mb.id_security_tag " +
                        " WHERE  mt.role_id_role= :roleId " +
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
