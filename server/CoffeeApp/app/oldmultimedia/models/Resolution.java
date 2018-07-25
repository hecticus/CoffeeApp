package oldmultimedia.models;

import com.fasterxml.jackson.annotation.*;
import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  Yenny Fung
 * @since   2016
 */
@Entity
public class Resolution extends Model {

    @Constraints.Required
    @Constraints.MaxLength(50)
    @Column(length = 50, nullable = false, updatable = false)
    private String dtype; // discriminator

    @Id
    protected Long id;

    @Constraints.Required
    @Constraints.MaxLength(100)
    @Column(length = 100, nullable = false)
    private String name;

    @Constraints.Required
    @Column(nullable = false)
    private Integer width;

    @Constraints.Required
    @Column(nullable = false)
    private Integer height;

    @ManyToMany(mappedBy = "resolutions")
    @JsonIgnore
    private List<Media> media = new ArrayList<>();

    private static Finder<Long, Resolution> finder = new Finder<>(Resolution.class);

    public static Resolution findById(Long id){
        return finder.byId(id);
    }

    public static List<Resolution> findAllByDtype(String dtype){
        return finder.query().where()
                .eq("dtype", dtype)
                .findList();
    }

    public static List<Resolution> findAll(){
        return finder.all();
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }
}
