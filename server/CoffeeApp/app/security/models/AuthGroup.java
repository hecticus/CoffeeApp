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
public class AuthGroup extends BaseModel {


    @Constraints.Required
    @Constraints.MaxLength(50)
    @Column(length = 50, unique = true, nullable = false)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "authGroups", cascade = CascadeType.REMOVE)
    private List<AuthRole> authRoles = new ArrayList<>();

    @OneToMany(mappedBy = "authGroup")
    @JsonIgnore
    private List<AuthUser> authUsers = new ArrayList<>();

    private static Finder<Long, AuthGroup> finder = new Finder<>(AuthGroup.class);

    public static AuthGroup findById(Long id){
        return finder.byId(id);
    }

    public static AuthGroup findByName(String name){
        return finder
                .query()
                .where()
                .eq("name", name)
                .findUnique();
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
        List<AuthGroup> authGroups = finder.all();
        if(authGroups != null || !authGroups.isEmpty()) {
            for(AuthGroup authGroup: authGroups){ // TODO buscar una mejor manera de setear a null la relación
                List<AuthUser> authUsers = authGroup.authUsers;
                for(AuthUser authUser: authUsers) {
                    authUser.setAuthGroup(null);
                }
                Ebean.updateAll(authUsers);
            }
            Ebean.deleteAll(authGroups);
        }
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

    public List<AuthUser> getAuthUsers() {
        return authUsers;
    }

    public void setAuthUsers(List<AuthUser> authUsers) {
        this.authUsers = authUsers;
    }
}
