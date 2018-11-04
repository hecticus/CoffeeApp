package models.userModels;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import io.ebean.*;
import io.ebean.annotation.Formula;
import io.ebean.text.PathProperties;
import models.AbstractEntity;
import models.Contact;
import models.Multimedia;
import play.data.validation.Constraints;
import security.models.*;

import javax.persistence.*;

/**
 * Bean for users registered in system.
 *
 * @author Yenny Fung
 * @since 2016
 */

/*  http://ebean-orm.github.io/docs/mapping/jpa/
    Only single table inheritance is supported. This enhancement request is to add support for JOINED or TABLE PER CLASS inheritance strategies.
    or
    https://stackoverflow.com/questions/33972767/is-unique-foreign-keys-across-multiple-tables-via-normalization-and-without-null
*/

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="dtype", length = 50)
@DiscriminatorValue("generic")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = User.class)
public class User extends AbstractEntity {

    @Transient
    protected String dtype;

    @Constraints.Required
    @OneToOne(optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "auth_user_id", referencedColumnName = "id", updatable = false) // se debe especificar "name" y "referencedColumnName" para que "updatable" funcione
    @PrimaryKeyJoinColumn
    protected AuthUser authUser;

    @Formula(select = "(SELECT CONCAT(first_name,' ',last_name) FROM user u WHERE u.id = ${ta}.id) ")
    protected String fullName;

    @Constraints.MaxLength(100)
    @Constraints.Required
    @Column(length = 100, nullable = false)
    protected String firstName;

    @Constraints.MaxLength(100)
    @Constraints.Required
    @Column(length = 100, nullable = false)
    protected String lastName;

    @Column(length = 200)
    protected String description;

    @Embedded
    protected Contact contact;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnore
    protected RecoverPassword recoverPassword;

    @OneToOne(cascade = CascadeType.REMOVE)
    protected Multimedia multimediaProfile;

    protected static Finder<Long, User> finder = new Finder<>(User.class);

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
            authUser.setAuthGroup(this.authUser.getAuthGroup());
            authUser.update();
            this.authUser = authUser;
        }
    }

    public static User findById(Long id){
        return finder.byId(id);
    }

    public static User findByEmail(String email){
        return finder.query().where()
                .eq("authUser.email", email)
                .findUnique();
    }

    public static User findByAuthUserId(Long authUserId){
        return finder.query().where()
                .eq("authUser.id", authUserId)
                .findUnique();
    }

    public static User findByAuthUserId(Long authUserId, PathProperties pathProperties){
        if(pathProperties != null && !pathProperties.getPathProps().isEmpty()) {
            return finder.query().where()
                    .eq("authUser.id", authUserId)
                    .apply(pathProperties)
                    .findUnique();
        }
        return finder.query().where()
                .eq("authUser.id", authUserId)
                .findUnique();
    }

    public static User findByIdAuthUserId(Long id, Long authUserId){
        return finder.query().where()
                .idEq(id)
                .eq("authUser.id", authUserId)
                .findUnique();
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public RecoverPassword getRecoverPassword() {
        return recoverPassword;
    }

    public void setRecoverPassword(RecoverPassword recoverPassword) {
        this.recoverPassword = recoverPassword;
    }

    public Multimedia getMultimediaProfile() {
        return multimediaProfile;
    }

    public void setMultimediaProfile(Multimedia multimediaProfile) {
        this.multimediaProfile = multimediaProfile;
    }
}
