package security.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.PagedList;
import io.ebean.text.PathProperties;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nisa on 5/17/17.
 */
@Entity
@Table(name = "auth_role")
public class Role extends AbstractEntity{

    @Id
    @Constraints.MaxLength(50)
    @Column(length = 50)
    private String id;

    private String description;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private List<Permission> permissions = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Group> groups = new ArrayList<>();

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AuthUser> authUsers = new ArrayList<>();

    private static Finder<String, Role> finder = new Finder<>(Role.class);

    public static Role findById(String id){
        return finder.byId(id);
    }

    public static List<Role> findAllByUserId(Long authUserId){
        List<Role> roles = finder
                .query()
                .where()
                .eq("authUsers.id", authUserId)
                .findList();

        roles.addAll(finder
                .query()
                .where()
                .eq("groups.authUsers.id", authUserId)
                .findList());
        return roles;
    }

    //Se modifico no estoy muy claro Page List
    public static PagedList findAll(Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties){
        ExpressionList expressionList = finder.query().where();

        if(pathProperties.hasPath(pathProperties.toString()))
            expressionList.apply(pathProperties);

        if(sort != null)
            expressionList.orderBy(sort.startsWith("-") ? sort.substring(1) + " desc" : sort + " asc");

        if(pageIndex == null || pageSize == null)
            return expressionList.findPagedList();
        return expressionList.findPagedList();
    }

    public static void deleteAll(){
        List<Role> roles = finder.all();
        if( roles != null || !roles.isEmpty())
            Ebean.deleteAll(roles);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<AuthUser> getAuthUsers() {
        return authUsers;
    }

    public void setAuthUsers(List<AuthUser> authUsers) {
        this.authUsers = authUsers;
    }
}
