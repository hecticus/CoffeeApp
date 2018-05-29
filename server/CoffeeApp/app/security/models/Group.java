package security.models;

//import com.avaje.ebean.Ebean;
import io.ebean.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
}
