package security.models;

import com.avaje.ebean.Ebean;
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
@Table(name = "auth_permission")
public class Permission extends AbstractEntity  {

    @Id
    private Long id;

    @Constraints.MaxLength(50)
    @Column(length = 70)//, unique = false)
    private String name; //TODO evaluar para poner name como primarykey y no depender de un id autogenerado para representar el permiso

    @Constraints.Required
    @Constraints.MaxLength(100)
    @Column(length = 100, nullable = false) //, unique = true
    private String route;

    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Role> roles = new ArrayList<>();

    private static Finder<Long, Permission> finder = new Finder<>(Permission.class);

    public static Permission findByRoute(String route){
        return finder
                .query()
                .where()
                .eq("route", route)
                .findUnique();
    }

    public static void deleteAll(){
        List<Permission> permissions = finder.all();
        if(permissions != null || !permissions.isEmpty())
            Ebean.delete(permissions); //   No se si realiza lo mismo.deleteAll(permissions);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
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

    public static Boolean hasPermmission(String route, Long authUserId) {
        if( (finder
                .query()
                .where()
                .eq("route", route)
                .eq("roles.groups.authUsers.id", authUserId)
                .findUnique() != null) ||
             (finder
                     .query()
                     .where()
                     .eq("route", route)
                     .eq("roles.authUsers.id", authUserId)
                     .findUnique() != null) ){
            return true;
        }
        return false;
    }
}
