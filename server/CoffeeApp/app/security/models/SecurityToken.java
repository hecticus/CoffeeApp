package security.models;

import io.ebean.*;
import play.data.validation.Constraints;

import javax.persistence.*;

/**
 * Created by nisa on 09/10/17.
 */
@Entity
@Table(name = "auth_token")
public class SecurityToken extends AbstractEntity {

    @Constraints.Required
    @Column(columnDefinition = "text", nullable = false)
    private String token;

    @Constraints.Required
    @ManyToOne(optional = false) //TODO maximo una cantidad per userAuth
    private AuthUser authUser;

    private static Finder<Long, SecurityToken> finder = new Finder<>(SecurityToken.class);

    public static SecurityToken findByAuthUserIdToken(Long authUserId, String token){
        return finder
                .query()
                .where()
                .eq("authUser.id", authUserId)
                .eq("token", token)
                .findUnique();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }
}
