package security.models;

import io.ebean.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.text.PathProperties;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by nisa on 5/17/17.
 *
 * reference: https://cloudify.co/2016/04/15/simple-secure-role-based-access-control-rest-api-rbac-server-devops-cloud-orchestration.html
 */
@Entity
@Table
public class AuthRole extends BaseModel {

    @Constraints.Required
    @Constraints.MaxLength(50)
    @Column(length = 50, unique = true, nullable = false)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "authRoles", cascade = CascadeType.REMOVE)
    private List<AuthPermission> authPermissions = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<AuthGroup> authGroups = new ArrayList<>();

    @ManyToMany(mappedBy = "authRoles", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<AuthUser> authUsers = new ArrayList<>();

    private static Finder<Long, AuthRole> finder = new Finder<>(AuthRole.class);

    public static AuthRole findById(Long id){
        return finder.byId(id);
    }

    public static AuthRole findByName(String name){
        return finder.query().where().eq("name", name).findUnique();
    }

    public static AuthRole findByNameGroupId(String name, Long groupId) {
        return finder
                .query()
                .where()
                .eq("name", name)
                .eq("authGroups.id", groupId)
                .findUnique();
    }

    public static List<AuthRole> findAllByName(List<String> names) {
        return finder
                .query()
                .where()
                .in("name", names)
                .findList();
    }

    public static PagedList findAll(Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties){
        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(sort != null)
            expressionList.orderBy(sort.startsWith("-") ? sort.substring(1) + " desc" : sort + " asc");

        if(pageIndex == null || pageSize == null)
            return expressionList.setFirstRow(0).setMaxRows(expressionList.findCount()).findPagedList();
        return expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findPagedList();
    }

    public static void deleteAll(){
        List<AuthRole> authRoles = finder.all();
        if(authRoles != null || !authRoles.isEmpty())
            Ebean.deleteAll(authRoles);
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

    public List<AuthPermission> getAuthPermissions() {
        return authPermissions;
    }

    public void setAuthPermissions(List<AuthPermission> authPermissions) {
        this.authPermissions = authPermissions;
    }

    public List<AuthGroup> getAuthGroups() {
        return authGroups;
    }

    public void setAuthGroups(List<AuthGroup> authGroups) {
        this.authGroups = authGroups;
    }

    public List<AuthUser> getAuthUsers() {
        return authUsers;
    }

    public void setAuthUsers(List<AuthUser> authUsers) {
        this.authUsers = authUsers;
    }
}
