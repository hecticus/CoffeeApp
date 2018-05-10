package models.domain;

import models.AbstractEntity;
import play.data.validation.Constraints;

import javax.persistence.*;

/**
 * Created by darwin on 31/10/17.
 */
@Entity
@Table(name="tokens")
public class Token extends AbstractEntity
{

    @Id
    @Column(name = "id_token")
    private Long idToken;

    @Constraints.Required
    @Constraints.MaxLength(500)
    @Column(nullable = true, length = 500)
    protected String token;



    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    public Long getIdToken() {
        return idToken;
    }

    public void setIdToken(Long idToken) {
        this.idToken = idToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
