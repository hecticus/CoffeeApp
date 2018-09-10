package daemonTask;

import io.ebean.Finder;
import multimedia.models.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.DefaultValue;
import java.sql.Time;

@Entity
@Table(name="job")
public class Job extends BaseModel {

    private String Description;

    @DefaultValue("true")
    private Boolean status;

    private Time closeTime;

    private Integer delay;

    private static Finder<Long, Job> finder = new Finder<>(Job.class);

    public Time getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }


}
