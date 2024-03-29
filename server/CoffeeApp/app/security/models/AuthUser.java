package security.models;

import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.ebean.Ebean;
import io.ebean.Finder;
import io.ebean.annotation.SoftDelete;
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

    @Constraints.MaxLength(50)
    @Constraints.Required
    @Column(length = 50, unique = true, nullable = false, updatable = false)
    protected String username;

    @Constraints.Email
    @Constraints.Required
    @Constraints.MaxLength(50)
    @Column(length = 50, unique = true, nullable = false)
    protected String email;

    @Constraints.Required
    @Column(columnDefinition = "text", nullable = false) //TODO cambiar a BLOB
    protected String password;

    @SoftDelete
    private boolean deleted;

    @Temporal(TemporalType.TIMESTAMP)
    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @UpdatedTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    protected ZonedDateTime lastLogin;

    @OneToOne(mappedBy = "authUser", cascade = CascadeType.ALL)
    @JsonIgnore
    protected SecurityPin SecurityPin;

    @ManyToMany
    protected List<Role> roles;

    @ManyToMany
    protected List<Group> groups;

    @OneToMany(mappedBy = "authUser", cascade = CascadeType.ALL)
    @JsonIgnore
    protected List<SecurityToken> securityTokens;


    public static final Finder<Long, AuthUser> finder = new Finder<>(AuthUser.class);

    public static AuthUser findById(Long id){
        return finder.byId(id);
    }

    public static void deleteAll(){
        List<AuthUser> authUsers = finder.all();
        if( authUsers != null && !authUsers.isEmpty())
            Ebean.deleteAll(authUsers);
    }

    public boolean isStatusDelete() {
        return deleted;
    }

    public void setStatusDelete(boolean statusDelete) {
        this.deleted = statusDelete;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static AuthUser findByEmail(String email){
        return finder
                .query()
                .where()
                .eq("email", email)
                .findUnique();
    }

    public static AuthUser findByName(String name){
        return finder
                .query()
                .where()
                .eq("username", name)
                .findUnique();
    }

    public static AuthUser findByEmailPassword(String email, String password){
        return finder
                .query()
                .where()
                .eq("email", email)
                .eq("password", password)
                .findUnique();
    }

    public static void delete(Long id) {
        AuthUser entity = finder.byId(id);
        if(entity != null)
            Ebean.delete(entity);
    }

    public static void delete(List<Long> ids) {
        List<AuthUser> entities = finder.query().where().idIn(ids).findList();
        if(!entities.isEmpty())
            Ebean.deleteAll(entities);
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

