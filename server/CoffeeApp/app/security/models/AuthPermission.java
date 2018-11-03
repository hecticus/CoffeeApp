package security.models;

import io.ebean.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.text.PathProperties;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nisa on 24/10/17.
 *
 * reference: https://cloudify.co/2016/04/15/simple-secure-role-based-access-control-rest-api-rbac-server-devops-cloud-orchestration.html
 */
@Entity
@Table
public class AuthPermission extends BaseModel {

    @Constraints.Required
    @Constraints.MaxLength(100)
    @Column(length = 100, unique = true, nullable = false)
    private String name;

    private String description;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<AuthRole> authRoles = new ArrayList<>();

    private static Finder<Long, AuthPermission> finder = new Finder<>(AuthPermission.class);

    public static AuthPermission findByName(String name) {
        return finder
                .query()
                .where()
                .eq("name", name)
                .findUnique();
    }

    public static AuthPermission findByNameRoleId(String name, Long roleId) {
        return finder
                .query()
                .where()
                .eq("name", name)
                .eq("authRoles.id", roleId)
                .findUnique();
    }

    public static List<AuthPermission> findAllByName(List<String> names) {
        return finder
                .query()
                .where()
                .in("name", names)
                .findList();
    }

    public static PagedList findAll(Long authUserId, Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties){
        ExpressionList expressionList = finder.query().where();

        if(authUserId != null) {
            expressionList.disjunction()
                    .add(io.ebean.Expr.eq("authRoles.authGroups.authUsers.id", authUserId))
                    .add(io.ebean.Expr.eq("authRoles.authUsers.id", authUserId));
        }

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(sort != null)
            expressionList.orderBy(sort.startsWith("-") ? sort.substring(1) + " desc" : sort + " asc");

        if(pageIndex == null || pageSize == null)
            return expressionList.setFirstRow(0).setMaxRows(expressionList.findCount()).findPagedList();
        return expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findPagedList();
    }

    public static void deleteAll(){
        List<AuthPermission> authPermissions = finder.all();
        if(authPermissions != null || !authPermissions.isEmpty())
            Ebean.deleteAll(authPermissions);
    }

    public static Boolean hasPermmission(String name, Long authUserId) {
        /*if( (finder.where() // the same result than below
                .eq("name", name)
                .eq("authRoles.authGroups.authUsers.id", authUserId)
                .findUnique() != null) ||
                (finder.where()
                        .eq("name", name)
                        .eq("authRoles.authUsers.id", authUserId)
                        .findUnique() != null) ){
            return true;
        }*/

        return finder
                .query()
                .where()
                .eq("name", name)
                .disjunction()
                .add(io.ebean.Expr.eq("authRoles.authGroups.authUsers.id", authUserId))
                .add(io.ebean.Expr.eq("authRoles.authUsers.id", authUserId))
                .findUnique() != null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AuthRole> getAuthRoles() {
        return authRoles;
    }

    public void setAuthRoles(List<AuthRole> authRoles) {
        this.authRoles = authRoles;
    }
}
