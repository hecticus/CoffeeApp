package models.domain;

import org.joda.time.DateTime;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;

/**
 * Bean for users registered in system.
 *
 * @author Yenny Fung
 * @since 2016
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user")
public class User extends AbstractEntity {


    @Id
    private Long idUser;
    /**
     * Username. This attribute is'nt used, email attribute is used in your place.
     */
    @Constraints.MaxLength(100)
    @Column(length = 100)
    protected String name;

    /**
     * Password.
     */
    @Constraints.MaxLength(100)
    @Column(length = 100)
    private String password;

    @Constraints.MaxLength(100)
    @Constraints.Required
    @Column(length = 100, nullable = false)
    protected String firstName;

    @Constraints.MaxLength(100)
    @Constraints.Required
    @Column(length = 100, nullable = false)
    protected String lastName;

    /**
     * Email to send the confirmation of register. This email is used like username for login in sytem.
     */
    @Constraints.Required
    @Column(unique = true, nullable = false)
    protected String email;

    /**
     * Check if user confirm your register via email
     */
    //@Constraints.Required
    //@Column(nullable = false)
    protected Short emailValidated;

    /**
     * Status in system: enable or disable
     */
    @Constraints.Required
    @Column(columnDefinition = "tinyint default 0", nullable = false)
    protected Boolean archived = false;

    /**
     * Last login in system
     */
    //@Constraints.Required
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime"/*, nullable = false*/)
    protected DateTime lastLogin;


    @Constraints.MaxLength(100)
    @Column(nullable = true)
    protected String token;

    @Constraints.Required
    @ManyToOne
    @Column(nullable = false)
    private Role role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Short getEmailValidated() {
        return emailValidated;
    }

    public void setEmailValidated(Short emailValidated) {
        this.emailValidated = emailValidated;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public DateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(DateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}
