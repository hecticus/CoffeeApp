package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import controllers.parsers.jsonParser.CustomDeserializer.CustomDateTimeDeserializer;
import controllers.parsers.jsonParser.customSerializer.CustomDateTimeSerializer;
import io.ebean.Model;
import io.ebean.annotation.CreatedTimestamp;
import io.ebean.annotation.SoftDelete;
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
 * @author  Yenny Fung 2016
 * modify sm21 2018
 */
@MappedSuperclass
public abstract class AbstractEntity extends Model {

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)//, insertable = false)
    protected ZonedDateTime createdAt;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdatedTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")//, insertable = false)
    protected ZonedDateTime updatedAt;

    @Column(nullable = false)
    private Integer statusDelete;

    protected AbstractEntity() {
        statusDelete = 0;
    }


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