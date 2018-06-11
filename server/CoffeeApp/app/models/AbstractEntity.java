package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.ebean.Model;
import io.ebean.annotation.CreatedTimestamp;
import io.ebean.annotation.SoftDelete;
import io.ebean.annotation.UpdatedTimestamp;
import play.data.format.Formats;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

/**
 *
 *
 * @author  Yenny Fung 2016
 * modify sm21 2018
 */
@MappedSuperclass
public abstract class AbstractEntity extends Model {

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, insertable = false)
    protected ZonedDateTime createdAt;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdatedTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable = false)
    protected ZonedDateTime updatedAt;

//    @Column(nullable = false)
//    private Integer statusDelete;

    @SoftDelete
    private boolean statusDelete;

    public boolean isStatusDelete() {
        return statusDelete;
    }

    public void setStatusDeleted(boolean statusDelete) {
        this.statusDelete = statusDelete;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    //@JsonIgnore
    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    //@JsonIgnore
    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static String sort(String sort) {
        if (sort == null)
            return "";
        if (sort.startsWith("-"))
            return sort.substring(1) + " desc";
        return sort + " asc";
    }

    public static String sort(String sort, String order) {
        if (!order.equals("ASC") || !order.equals("DESC") || !order.equals("asc") || !order.equals("desc")) {
            if (order.equals("DESC") || order.equals("desc") )
                return sort + " desc" ;
            return sort + " asc";
        }else{
            return null;
        }
    }
}