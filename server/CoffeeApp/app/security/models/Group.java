package security.models;

//import com.avaje.ebean.Ebean;

import io.ebean.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.text.PathProperties;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nisa on 24/10/17.
 */
@Entity
@Table(name="auth_group")
public class Group extends AbstractEntity {

//    Se agrego campo name
    @Constraints.Required
    @Constraints.MaxLength(50)
    @Column(length = 50, unique = true, nullable = false)
    private String name;

    @Constraints.MaxLength(255)
    private String description;

    @ManyToMany(mappedBy = "groups", cascade = CascadeType.ALL)
    private List<Role> roles = new ArrayList<>();

    @ManyToMany(mappedBy = "groups", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AuthUser> authUsers = new ArrayList<>();

    private static Finder<Long, Group> finder = new Finder<>(Group.class);

    public static Group findById(Long id){
        return finder.byId(id);
    }

    public static Group findByName(String name){
        return finder.query().where().eq("name", name).findUnique();
    }

    public static void deleteAll(){
        List<Group> groups = finder.all();
        if(groups != null && !groups.isEmpty())
            Ebean.deleteAll(groups);
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<AuthUser> getAuthUsers() {
        return authUsers;
    }

    public void setAuthUsers(List<AuthUser> authUsers) {
        this.authUsers = authUsers;
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
