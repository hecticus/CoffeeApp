package security.models;

import com.avaje.ebean.Model;
import models.domain.AbstractEntity;
import play.data.validation.Constraints;

import javax.persistence.*;

/**
 * Created by nisa on 09/10/17.
 */
@Entity
@Table(name = "auth_token")
public class SecurityToken extends AbstractEntity {

    @Id
    private Long id;

    @Constraints.Required
    @Column(columnDefinition = "text", nullable = false)
    private String token;

    @Constraints.Required
    @ManyToOne(optional = false) //TODO maximo una cantidad per userAuth
    private AuthUser authUser;

    private static Model.Finder<Long, SecurityToken> finder = new Model.Finder<>(SecurityToken.class);

    public static SecurityToken findByAuthUserIdToken(Long authUserId, String token){
        return finder
                .where()
                .eq("authUser.id", authUserId)
                .eq("token", token)
                .findUnique();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
