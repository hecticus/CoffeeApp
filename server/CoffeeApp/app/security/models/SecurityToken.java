package security.models;

import io.ebean.Finder;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

/**
 * Created by nisa on 09/10/17.
 */
@Entity
@Table(name = "auth_token")
public class SecurityToken extends BaseModel {

    @Constraints.Required
    @Column(columnDefinition = "text", nullable = false)
    private String token;

    @Constraints.Required
    @Constraints.MaxLength(20)
    @Column(nullable = false, length = 20)
    private String type;

    @OneToOne
    private SecurityToken refreshToken; //oneToOne mapping for self: table accessToken with refreshToken property

    @OneToOne(mappedBy = "refreshToken", cascade = CascadeType.REMOVE)
    private SecurityToken accessToken; //oneToOne mapping for self: table refreshToken with accessToken property

    @Constraints.Required
    @ManyToOne(optional = false) //TODO maximo una cantidad per userAuth
    private AuthUser authUser;

    private static Finder<Long, SecurityToken> finder = new Finder<>(SecurityToken.class);

    public static SecurityToken findById(Long id){
        return finder.byId(id);
    }

    public static SecurityToken findByIdType(Long id, String type){
        return finder.query().where()
                .idEq(id)
                .eq("type", type)
                .findUnique();
    }

    public static List<SecurityToken> findAllByType(String type){
        return finder.query().where()
                .eq("type", type)
                .findList();
    }

    public static List<SecurityToken> findAll(){
        return finder.all();
    }

    public static List<SecurityToken> findAllByAuthUserIdType(Long authUserId, String type){
        return finder
                .query()
                .where()
                .eq("authUser.id", authUserId)
                .eq("type", type)
                .findList();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SecurityToken getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(SecurityToken refreshToken) {
        this.refreshToken = refreshToken;
    }

    public SecurityToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(SecurityToken accessToken) {
        this.accessToken = accessToken;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }
}
