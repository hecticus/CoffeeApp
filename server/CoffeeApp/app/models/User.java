package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import controllers.utils.ListPagerCollection;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.annotation.CreatedTimestamp;
import io.ebean.annotation.Formula;
import controllers.multimediaUtils.Multimedia;
import io.ebean.annotation.JsonIgnore;
import io.ebean.text.PathProperties;
import org.joda.time.DateTime;
import play.data.format.Formats;
import play.data.validation.Constraints;
import security.models.AuthUser;

import javax.persistence.*;
import java.util.List;

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

    @Id
    private Long id;

    @Constraints.Required
    @OneToOne(optional = false)//, cascade = CascadeType.ALL)
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

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedTimestamp
    @Column(columnDefinition = "datetime", updatable = false, nullable = false)//, insertable = false)
    protected DateTime lastLogin;

    private static Finder<Long, User> finder = new Finder<>(User.class);

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

    public static JsonNode uploadPhoto(JsonNode request) {
        Multimedia multimedia = new Multimedia();
        return multimedia.uploadPhoto(request);
    }

    public static User findById(Long id){
        return finder.byId(id);
    }

    public static ListPagerCollection findAll(Integer index, Integer size, PathProperties pathProperties,
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

        if (sort != null) {
            if (sort.contains(" ")) {
                String[] aux = sort.split(" ", 2);
                expressionList.orderBy(sort(aux[0], aux[1]));
            } else {
                expressionList.orderBy(sort("id", sort));
            }
        }

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

