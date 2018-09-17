package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.ListPagerCollection;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.annotation.Formula;
import io.ebean.annotation.JsonIgnore;
import io.ebean.annotation.UpdatedTimestamp;
import io.ebean.text.PathProperties;
import play.data.format.Formats;
import play.data.validation.Constraints;
import security.models.AuthUser;

import javax.persistence.*;
import java.time.ZonedDateTime;

/*
 * Bean for users registered in system.
 * @author Yenny Fung
 * @since 2016
 *
 * modify sn21 since 2018
 *
 */

@Entity
@Table(name = "user")
public class User extends AbstractEntity {

    @Constraints.Required
    @OneToOne(optional = false)
    @JoinColumn(name = "auth_user_id", referencedColumnName = "id", updatable = false ) // se debe especificar "name" y "referencedColumnName" para que "updatable" funcione
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private AuthUser authUser;

    @Formula(select="(select concat(first_name,' ',last_name) from user u where u.id = ${ta}.id) ")
    private String name;;

    @Constraints.MaxLength(50)
    @Constraints.Required
    @Column(length = 50, nullable = false)
    protected String firstName;

    @Constraints.MaxLength(50)
    @Constraints.Required
    @Column(length = 50, nullable = false)
    protected String lastName;

    @Column(columnDefinition = "text")
    private String description;

    @Embedded
    private Contact contact;

    @Temporal(TemporalType.TIMESTAMP)
    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @UpdatedTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    protected ZonedDateTime lastLogin;

    private static Finder<Long, User> finder = new Finder<>(User.class);

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

    public ZonedDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(ZonedDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }


    public static User findById(Long id){
        return finder.byId(id);
    }

    public static User findByEmail(String email){
        return finder.query().where()
                .startsWith("authUser.email", email)
                .findUnique();
    }

    public static User findByAuthUserId(Long authUserId){
        return finder.query().where()
                .eq("authUser.id", authUserId)
                .findUnique();
    }    public static ListPagerCollection findAll(Integer index, Integer size, PathProperties pathProperties,
                                              String sort, String name, String firstName, String lastName, boolean deleted) {

        ExpressionList expressionList = finder.query().where();

        if (pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if (name != null)
            expressionList.startsWith("name", name);

        if (firstName != null)
            expressionList.startsWith("firstName", name);

        if (lastName != null)
            expressionList.startsWith("lastName", name);

        if (deleted)
            expressionList.setIncludeSoftDeletes();

        if (sort != null)
            expressionList.orderBy(sort(sort));

        if (index == null || size == null)
            return new ListPagerCollection(expressionList.findList());

        return new ListPagerCollection(expressionList.setFirstRow(index).setMaxRows(size).findList(),
                expressionList.setFirstRow(index).setMaxRows(size).findCount(),
                index, size);
    }

    public static User findByMediaProfileId(Long mediaProfileId) {
        return finder.query().where().eq("mediaProfile.id", mediaProfileId).findUnique();
    }

}

