package daemonTask;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import controllers.parsers.jsonParser.CustomDateTimeDeserializer;
import controllers.parsers.jsonParser.CustomDateTimeSerializer;
import io.ebean.Finder;
import models.status.StatusJob;
import multimedia.models.BaseModel;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.ws.rs.DefaultValue;
import java.sql.Time;
import java.time.ZonedDateTime;

@Entity
@Table(name="job")
public class Job extends BaseModel {

//    Sun Jan 01 2017 00:00:00 GMT-0400

    private String description;

    @ManyToOne
//    @JsonBackReference
    private StatusJob statusJob;

    private Time closeTime;

    @Formats.DateTime(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssX")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(columnDefinition = "datetime")
    private ZonedDateTime closedDate;

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

    public ZonedDateTime getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(ZonedDateTime closedDate) {
        this.closedDate = closedDate;
    }
}
