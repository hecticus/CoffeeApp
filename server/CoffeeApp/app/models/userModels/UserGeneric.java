/*
package models.userModels;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.avaje.ebean.PagedList;
import com.avaje.ebean.text.PathProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

*/
/**
 * Something to make fun of darwin ask for the lilian one
 *
 * @author  palenge
 * @since   2017
 *//*

@Entity
@DiscriminatorValue("generic")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = UserGeneric.class)
public class UserGeneric extends User{

    private static Finder<Long, UserGeneric> finder = new Finder<>(UserGeneric.class);

    public static UserGeneric findById(Long id){
        return finder.byId(id);
    }

    public static List<UserGeneric> findAllByIds(List<Long> ids){
        return finder.where().idIn(ids).findList();
    }

    public static PagedList findAll(String email, String firstName, String lastName, Long roleId, Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties) {
        ExpressionList expressionList = finder.where();

        if(pathProperties != null && !pathProperties.isEmpty()){
            if (pathProperties.hasPath("authUser"))
                finder.fetch("authUser");
            if (pathProperties.hasPath("mediaPhoto"))
                finder.fetch("mediaPhoto");
            expressionList.apply(pathProperties);
        }

        if(email != null)
            expressionList.startsWith("authUser.email", email);
        if (firstName != null)
            expressionList.startsWith("firstName", firstName);
        if (lastName != null)
            expressionList.startsWith("lastName", lastName);

        if(sort != null)
            expressionList.orderBy(sort(sort));

        if(pageIndex == null || pageSize == null)
            return expressionList.findPagedList(0, expressionList.findRowCount());
        return expressionList.findPagedList(pageIndex, pageSize);
    }
}*/
