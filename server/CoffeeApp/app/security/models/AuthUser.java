package security.models;

import io.ebean.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by nisa on 02/11/17.
 */
@Entity
@JsonIgnoreProperties({"recoverPassword"})
public class AuthUser extends BaseModel {

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssX") // , timezone = "UTC"
    @Column(columnDefinition = "datetime")
    protected ZonedDateTime lastLogin;

    @ManyToOne
    protected AuthGroup authGroup;

    @ManyToMany(cascade = CascadeType.REMOVE)
    protected List<AuthRole> authRoles;

    @OneToMany(mappedBy = "authUser", cascade = CascadeType.REMOVE)
    @JsonIgnore
    protected List<SecurityToken> securityTokens;

    private static Finder<Long, AuthUser> finder = new Finder<>(AuthUser.class);

    public static AuthUser findById(Long id){
        return finder.byId(id);
    }

    public static AuthUser findByEmail(String email){
        return finder
                .query()
                .where()
                .eq("email", email)
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

    public List<AuthRole> getAuthRoles() {
        return authRoles;
    }

    public void setAuthRoles(List<AuthRole> authRoles) {
        this.authRoles = authRoles;
    }

    public AuthGroup getAuthGroup() {
        return authGroup;
    }

    public void setAuthGroup(AuthGroup authGroup) {
        this.authGroup = authGroup;
    }

    public List<SecurityToken> getSecurityTokens() {
        return securityTokens;
    }

    public void setSecurityTokens(List<SecurityToken> securityTokens) {
        this.securityTokens = securityTokens;
    }
}
