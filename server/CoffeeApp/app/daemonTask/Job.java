package daemonTask;

import io.ebean.Finder;
import models.status.StatusInvoice;
import models.status.StatusJob;
import multimedia.models.BaseModel;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.ws.rs.DefaultValue;
import java.sql.Time;

@Entity
@Table(name="job")
public class Job extends BaseModel {

    private String description;

    @ManyToOne
//    @JsonBackReference
    private StatusJob statusJob;

    private Time closeTime;

    @Constraints.Required
    private Integer delay;

    @DefaultValue("false")
    @Constraints.Required
    private Boolean stop;

    public Time getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStop() {
        return stop;
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }

    public StatusJob getStatusJob() {
        return statusJob;
    }

    public void setStatusJob(StatusJob statusJob) {
        this.statusJob = statusJob;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    private static Finder<Long, Job> finder = new Finder<>(Job.class);

    public static Job findById(Long id){
        return finder.byId(id);
    }
}
