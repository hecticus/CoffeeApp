/*
package models.userModels;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.avaje.ebean.text.PathProperties;
import com.fasterxml.jackson.annotation.*;
import io.ebean.Finder;
import models.companyModels.Company;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

*/
/**
 * Bean for users registered that are customer. A client inherits user.
 *
 * @author  Yenny Fung
 * @since   2016
 *//*

@Entity
@DiscriminatorValue("client")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = UserClient.class)
public class UserClient extends User {

    @ManyToMany(cascade = CascadeType.REMOVE)
    private List<Company> companies = new ArrayList<>();

    private static Finder<Long, UserClient> finder = new Finder<>(UserClient.class);

    public static UserClient findById(Long id){
        return finder.byId(id);
    }

    public static List<UserClient> findAllByIds(List<Long> ids){
        return finder.where().idIn(ids).findList();
    }

    public static PagedList findAll(String email, String firstName, String lastName, Long companyId, Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties) {
        ExpressionList expressionList = finder.where();

        if(pathProperties != null) {
            if (pathProperties.hasPath("authUser"))
                finder.fetch("authUser");
            if (pathProperties.hasPath("mediaPhoto"))
                finder.fetch("mediaPhoto");
            if (pathProperties.hasPath("companies")) //TODO filterMany
                finder.fetch("companies");
            expressionList.apply(pathProperties);
        }

        if(email != null)
            expressionList.startsWith("authUser.email", email);
        if(firstName != null)
            expressionList.startsWith("firstName", firstName);
        if(lastName != null)
            expressionList.startsWith("lastName", lastName);
        if(companyId != null)
            expressionList.eq("companies.id", companyId);

        if(sort != null)
            expressionList.orderBy(sort(sort));

        if(pageIndex == null || pageSize == null)
            return expressionList.findPagedList(0, expressionList.findRowCount());
        return expressionList.findPagedList(pageIndex, pageSize);
    }

    public static void delete(List<Long> ids) {
        List<UserClient> entities = finder.where().idIn(ids).findList();
        if(!entities.isEmpty())
            Ebean.deleteAll(entities);
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}
*/
