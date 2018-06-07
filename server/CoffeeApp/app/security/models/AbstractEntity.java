package security.models;

import io.ebean.*;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.annotation.SoftDelete;
import play.data.format.Formats;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Created by nisa on 30/10/17.
 * modify sm21 on 01/06/20
 */
@MappedSuperclass
public abstract class AbstractEntity extends Model {

    @Id
    private Long id;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    @CreatedTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable=false)
    protected ZonedDateTime createdAt;

    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    @UpdatedTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
    protected ZonedDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonIgnore
    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    @JsonIgnore
    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
