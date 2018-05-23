package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import controllers.parsers.jsonParser.CustomDeserializer.CustomDateTimeDeserializer;
import controllers.parsers.jsonParser.customSerializer.CustomDateTimeSerializer;
import io.ebean.Model;
import io.ebean.annotation.CreatedTimestamp;
import io.ebean.annotation.UpdatedTimestamp;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;
import java.util.InputMismatchException;

/**
 *
 *
 * @author  Yenny Fung
 * @since   2016
 */
@MappedSuperclass
public abstract class AbstractEntity extends Model {

    @Formats.DateTime(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @CreatedTimestamp //Automatically gets set to the current date and time when the record is first created
    //@Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    protected ZonedDateTime createdAt;

    @Formats.DateTime(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @UpdatedTimestamp //Automatically gets set to the current date and time whenever the record is updated
    //@Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable = false)
    protected ZonedDateTime updatedAt;

    @Constraints.Required
    @Column(nullable = false)
    private Integer statusDelete = 0;

    public Integer getStatusDelete() {
        return statusDelete;
    }

    public void setStatusDelete(Integer statusDelete) {
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

    public static String sort(String sort, String name, Long id) {
        try {
            String[] sorting = sort.split(" ", 2);

            System.out.println(sorting[0]);
            System.out.println("*********************");
            System.out.println(sorting[1]);
            if (sorting[0].equals(name) || sorting[0].equals(id)) {
                if (sorting[1].equals("desc") || sorting[1].equals("asc"))
                    return sorting.toString();
            }
            return "";
        }catch(InputMismatchException e){
            System.out.println("That is not an integer, please try again." );
            return "";
        }
    }
}