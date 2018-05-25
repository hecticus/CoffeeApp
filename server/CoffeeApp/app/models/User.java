
package models;



import com.fasterxml.jackson.databind.JsonNode;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import io.ebean.Finder;
import io.ebean.annotation.Formula;
import controllers.multimediaUtils.Multimedia;
import org.joda.time.DateTime;
import play.data.format.Formats;
import play.data.validation.Constraints;
import security.models.AuthUser;

import javax.persistence.*;
import java.util.List;



/*
 * Bean for users registered in system.
 *
 * @author Yenny Fung
 * @since 2016
 */

@Entity
@Table(name = "user")
public class User extends AbstractEntity {


    @Id
    private Long id;

    @Constraints.Required
    @OneToOne(optional = false)
    @JoinColumn(name = "auth_user_id", referencedColumnName = "id", updatable = false) // se debe especificar "name" y "referencedColumnName" para que "updatable" funcione
    @PrimaryKeyJoinColumn
    private AuthUser authUser;

    @Formula(select="(select concat(first_name,' ',last_name) from user u where u.id = ${ta}.id) ")
    private String name;;

    @Constraints.MaxLength(100)
    @Constraints.Required
    @Column(length = 100, nullable = false)
    protected String firstName;

    @Constraints.MaxLength(100)
    @Constraints.Required
    @Column(length = 100, nullable = false)
    protected String lastName;


    /*
     * Email to send the confirmation of register. This email is used like username for login in sytem.
     *//*

    @Constraints.Required
    @Column(unique = true, nullable = false)
    protected String email;


    /*
     * Last login in system
     */

    @Constraints.Required
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime", nullable = false)
    protected DateTime lastLogin;

    private static Finder<Long, User> finder = new Finder<>(User.class);

    @PrePersist
    public void createAuthUser() throws MySQLIntegrityConstraintViolationException {
        if(this.authUser != null) {
            this.authUser.setPassword(this.authUser.getPassword()); //TODO ENCRYP
            this.authUser.insert();
            this.id = this.authUser.getId();
        }else{
            throw new MySQLIntegrityConstraintViolationException("Violation constrain: Cannot add or update a child row: a foreign key constraint fails");
        }
    }

    @PreUpdate
    public void updateAuthUser(){
        if(this.id != null) {
            AuthUser authUser = User.findById(this.id).getAuthUser();
            authUser.setEmail(this.authUser.getEmail());
            authUser.update();
            this.authUser = authUser;
        }
    }

    //No clear
    public static User findById(Long id){
        return finder.byId(id);
    }

    public static User findByMediaProfileId(Long mediaProfileId){
        return finder.query().where().eq("mediaProfile.id", mediaProfileId).findUnique();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public DateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(DateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public User findByEmail(String email){
        List<User> users = finder.query()
                .where()
                .eq("email", email)
                .findList();

        if(!users.isEmpty())
            return users.get(0);
        return null;
    }



    public User findUniqueByEmail(String email){
        return finder.query()
                .where()
                .eq("email", email)
                .findUnique();
    }

    public User findUniqueByEmail(String email, Long id){
        return finder.query()
                .where()
                .ne("id", id)
                .eq("email", email)
                .findUnique();
    }

    
    public JsonNode uploadPhoto(JsonNode request)
    {
        Multimedia multimedia = new Multimedia();

        return multimedia.uploadPhoto(request);

    }
}

