package models.userModels;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.Finder;
import models.AbstractEntity;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Created by nisa on 29/09/17.
 */
@Entity
@Table(name = "user_recover_pass")
public class RecoverPassword extends AbstractEntity {

    @OneToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private User user;

    @Constraints.Required
    @Constraints.MaxLength(50)
    @Column(length = 50, nullable = false)
    @JsonIgnore
    private String pin;

    @Formats.DateTime(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssX")
    @Column(columnDefinition = "datetime")
    private ZonedDateTime expiration;

    @Transient
    private Integer expiresIn;

    private static Finder<Long, RecoverPassword> finder = new Finder<>(RecoverPassword.class);

    public RecoverPassword(String pin, ZonedDateTime expiration, Integer expiresIn, User user){
        this.setId(user.getId());
        this.pin = pin;
        this.expiration = expiration;
        this.expiresIn = expiresIn;
        this.user = user;
    }

    public static RecoverPassword findByPin(String pin){
        return finder
                .query()
                .where()
                .eq("pin", pin)
                .findUnique();
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
