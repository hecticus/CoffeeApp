/*
package models.userModels;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.avaje.ebean.annotation.JsonIgnore;
import com.avaje.ebean.text.PathProperties;
import com.fasterxml.jackson.annotation.*;
import models.proposalModels.Proposal;
import models.serviceModels.ServiceTask;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

*/
/**
 * Bean for users registered that are employees. A employees inherits user
 *
 * @author  Yenny Fung
 * @since   2016
 *//*

@Entity
@DiscriminatorValue("employee")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = UserEmployee.class)
@JsonIgnoreProperties({"serviceTasks", "proposals"})
public class UserEmployee extends User {

    @OneToMany(mappedBy = "userEmployee")
    @JsonIgnore
    private List<ServiceTask> serviceTasks = new ArrayList<>();

    @OneToMany(mappedBy = "userEmployee")
    @JsonIgnore
    private List<Proposal> proposals = new ArrayList<>();

    private static Finder<Long, UserEmployee> finder = new Finder<>(UserEmployee.class);

    public static UserEmployee findById(Long id){
        return finder.byId(id);
    }

    public static List<UserEmployee> findAllByIds(List<Long> ids){
        return finder.where().idIn(ids).findList();
    }

    public static PagedList findAll(String email, String firstName, String lastName, Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties){
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
        if(firstName != null)
            expressionList.startsWith("firstName", firstName);
        if(lastName != null)
            expressionList.startsWith("lastName", lastName);

        if(sort != null)
            expressionList.orderBy(sort(sort));

        if(pageIndex == null || pageSize == null)
            return expressionList.findPagedList(0, expressionList.findRowCount());
        return expressionList.findPagedList(pageIndex, pageSize);
    }

    public List<ServiceTask> getServiceTasks() {
        return serviceTasks;
    }

    public void setServiceTasks(List<ServiceTask> serviceTasks) {
        this.serviceTasks = serviceTasks;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }
}
*/
