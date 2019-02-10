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

    @Constraints.Required
    @Constraints.MaxLength(50)
    @Column(length = 50, unique = true, nullable = false)
    private String name;

    @Constraints.MaxLength(255)
    private String description;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private List<Permission> permissions = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Group> groups = new ArrayList<>();

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AuthUser> authUsers = new ArrayList<>();

    private static Finder<Long, Role> finder = new Finder<>(Role.class);

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

    public static void deleteAll(){
        List<Role> roles = finder.all();
        if(roles != null && !roles.isEmpty())
            Ebean.deleteAll(roles);
    }

    public static Role findById(Long id){
        return finder.byId(id);
    }

    public static Role findByName(String name){
        return finder.query().where().eq("name", name).findUnique();
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

    public static PagedList findAll(Integer index, Integer size, PathProperties pathProperties,
                                              String sort, String name, boolean deleted) {

        ExpressionList expressionList = finder.query().where();

        if (pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if (name != null)
            expressionList.startsWith("name", name);

        if (deleted)
            expressionList.setIncludeSoftDeletes();

        if (sort != null) {
            if (sort.contains(" ")) {
                String[] aux = sort.split(" ", 2);
                expressionList.orderBy(sort(aux[0], aux[1]));
            } else {
                expressionList.orderBy(sort("name", sort));
            }
        }

        if(index == null || size == null){
            return expressionList
                    .setFirstRow(0)
                    .setMaxRows(expressionList.findCount()).findPagedList();
        }

        return expressionList.setFirstRow(index).setMaxRows(size).findPagedList();
    }
}
