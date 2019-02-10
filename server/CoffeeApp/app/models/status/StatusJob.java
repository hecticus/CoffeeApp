package models.status;

import com.fasterxml.jackson.annotation.JsonIgnore;

import daemonTask.Job;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.text.PathProperties;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("job")
public class StatusJob extends Status {

    public static final String ACTIVE = "Active";
    public static final String INACTIVE = "Inactive";
    public static final String CANCELED = "Canceled";

    @OneToMany(mappedBy = "statusJob")
    @JsonIgnore
//    @JsonManagedReference
    private List<Job> jobs = new ArrayList<>();

    public static String getACTIVE() {
        return ACTIVE;
    }

    public static String getINACTIVE() {
        return INACTIVE;
    }

    public static String getCANCELED() {
        return CANCELED;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    private static Finder<Long, StatusJob> finder = new Finder<>(StatusJob.class);

    public static StatusJob findById(Long id){
        return finder.byId(id);
    }


    public static PagedList findAll(Integer index, Integer size, String sort,PathProperties pathProperties){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(sort != null)
            expressionList.orderBy(sort(sort));

        if(index == null || size == null)
            return new ListPagerCollection(expressionList.findList());

        return new ListPagerCollection(
                expressionList.setFirstRow(index).setMaxRows(size).findList(),
                expressionList.setFirstRow(index).setMaxRows(size).findCount(),
                index,
                size);
    }

}