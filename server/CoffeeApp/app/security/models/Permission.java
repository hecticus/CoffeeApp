package security.models;

import io.ebean.Ebean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.Finder;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nisa on 24/10/17.
 */
@Entity
@Table(name = "auth_permission")
public class Permission extends AbstractEntity  {

    @Constraints.MaxLength(100)
    @Column(length = 100, nullable = false, unique = true)
    private String name;

    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Role> roles = new ArrayList<>();

    private static Finder<Long, Permission> finder = new Finder<>(Permission.class);

    public static Permission findByName(String name){
        return finder
                .query()
                .where()
                .eq("name", name)
                .findUnique();
    }

    public static void deleteAll(){
        List<Permission> permissions = finder.all();
        if(permissions != null || !permissions.isEmpty())
            Ebean.deleteAll(permissions); //   No se si realiza lo mismo.deleteAll(permissions);
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

    public static Boolean hasPermmission(String name, Long authUserId) {
        if( (finder
                .query()
                .where()
                .eq("name", name)
                .eq("roles.groups.authUsers.id", authUserId)
                .findUnique() != null) ||
             (finder
                     .query()
                     .where()
                     .eq("name", name)
                     .eq("roles.authUsers.id", authUserId)
                     .findUnique() != null) ){
            return true;
        }
        return false;
    }
}
