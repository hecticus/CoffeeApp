package security.models;

//import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import controllers.parsers.jsonParser.CustomDeserializer.CustomDateTimeDeserializer;
import controllers.parsers.jsonParser.customSerializer.CustomDateTimeSerializer;
import io.ebean.Ebean;
import io.ebean.Finder;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by nisa on 02/11/17.
 */
@Entity
public class AuthUser extends AbstractEntity{

    @Id
    protected Long id;

    /*@Constraints.MaxLength(100)
    @Column(length = 100, unique = true, nullable = false, updatable = false)
    protected String username;*/

    @Constraints.Email
    @Constraints.Required
    @Constraints.MaxLength(100)
    @Column(length = 100, unique = true, nullable = false)
    protected String email;

    @Constraints.Required
    @Column(columnDefinition = "text", nullable = false) //TODO cambiar a BLOB
    protected String password;

    @Constraints.Required
    @Column(columnDefinition = "tinyint default 0", nullable = false, insertable = false)
    protected Boolean archived = false;

    @Formats.DateTime(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssX")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(columnDefinition = "datetime")
    protected ZonedDateTime lastLogin;

    @ManyToMany
    protected List<Role> roles;

    @ManyToMany
    protected List<Group> groups;

    @OneToOne(mappedBy = "authUser", cascade = CascadeType.ALL)
    @JsonIgnore
    protected SecurityPin SecurityPin;

    @OneToMany(mappedBy = "authUser", cascade = CascadeType.ALL)
    @JsonIgnore
    protected List<SecurityToken> securityTokens;


    public static final Finder<Long, AuthUser> find = new Finder<>(AuthUser.class);

    //private static Task .Finder<Long, AuthUser> finder = new Model.Finder<>(AuthUser.class);

    public static AuthUser findById(Long id){
        return find.byId(id);
    }

    public static AuthUser findByEmail(String email){
        return find
                .query()
                .where()
                .eq("email", email)
                .findUnique();
    }

    public static AuthUser findByEmailPassword(String email, String password){
        return find
                .query()
                .where()
                .eq("email", email)
                .eq("password", password)
                .findUnique();
    }

    public static void softDelete(Long id) {
        AuthUser entity = find.byId(id);
        entity.setArchived(true);
        entity.update();
    }

    public static void softDelete(List<Long> ids) {
        List<AuthUser> entities = find.query().where().idIn(ids).findList();
        for (AuthUser entity: entities){
            entity.setArchived(true);
            entity.update();
        }
    }

    public static void delete(Long id) {
        AuthUser entity = find.byId(id);
        if(entity != null)
            Ebean.delete(entity);
    }

    public static void delete(List<Long> ids) {
        List<AuthUser> entities = find.query().where().idIn(ids).findList();
        if(!entities.isEmpty())
            Ebean.deleteAll(entities);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public ZonedDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(ZonedDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public security.models.SecurityPin getSecurityPin() {
        return SecurityPin;
    }

    public void setSecurityPin(security.models.SecurityPin securityPin) {
        SecurityPin = securityPin;
    }

    public List<SecurityToken> getSecurityTokens() {
        return securityTokens;
    }

    public void setSecurityTokens(List<SecurityToken> securityTokens) {
        this.securityTokens = securityTokens;
    }
}
