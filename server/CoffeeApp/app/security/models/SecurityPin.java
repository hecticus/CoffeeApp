package security.models;

import com.avaje.ebean.Ebean;
import io.ebean.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Created by nisa on 29/09/17.
 */
@Entity
@Table(name = "auth_pin")
public class SecurityPin extends AbstractEntity {

    @OneToOne
    @PrimaryKeyJoinColumn
    @JoinColumn(nullable = false)
    private AuthUser authUser;

    @Constraints.Required
    @Constraints.MaxLength(50)
    @Column(length = 50, nullable = false)
    @JsonIgnore
    private String pin;

    @Transient
    private Integer expiresIn;

    private Integer tries;

    @Formats.DateTime(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssX")
    @Column(columnDefinition = "datetime")
    private ZonedDateTime expiration;

    private static Finder<Long, SecurityPin> finder = new Finder<>(SecurityPin.class);

    public SecurityPin(){}

    public SecurityPin(String pin, ZonedDateTime expiration, Integer expiresIn, Integer tries, AuthUser authuser){
        this.setId(authuser.getId());
        this.pin = pin;
        this.expiration = expiration;
        this.expiresIn = expiresIn;
        this.tries = tries;
        this.authUser = authUser;
    }

    public SecurityPin(ZonedDateTime expiration, Integer tries){
        this.expiration = expiration;
        this.tries = tries;
    }

    public static SecurityPin findByUserId(Long authUserId){
        return finder
                .query()
                .where()
                .eq("authUser.id", authUserId)
                .findUnique();
    }

    public static void delete(Long id) {
        SecurityPin entity = finder.byId(id);
        if(entity != null)
            Ebean.delete(entity);
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public ZonedDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(ZonedDateTime expiration) {
        this.expiration = expiration;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Integer getTries() {
        return tries;
    }

    public void setTries(Integer tries) {
        this.tries = tries;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }
}
