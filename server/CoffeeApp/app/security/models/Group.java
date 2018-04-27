/*
package security.models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by nisa on 24/10/17.
 *//*

@Entity
@Table(name="auth_group")
public class Group extends AbstractEntity {

    @Id
    @Constraints.MaxLength(50)
    @Column(length = 50)
    private String id;

    private String description;

    @ManyToMany(mappedBy = "groups", cascade = CascadeType.ALL)
    private List<Role> roles = new ArrayList<>();

    @ManyToMany(mappedBy = "groups", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AuthUser> authUsers = new ArrayList<>();

    private static Model.Finder<String, Group> finder = new Model.Finder<>(Group.class);

    public static Group findById(String id){
        return finder.byId(id);
    }

    public static void deleteAll(){
        List<Group> groups = finder.all();
        if(groups != null || !groups.isEmpty())
            Ebean.deleteAll(groups);
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
}
*/
