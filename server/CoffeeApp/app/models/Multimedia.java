package models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.ebean.Finder;
import multimedia.models.MultimediaCDN;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

@Entity
public class Multimedia extends AbstractEntity {

    private String dtype;

    @Constraints.MaxLength(100)
    @Column(length = 100)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private MultimediaCDN multimediaCDN;

    private static Finder<Long, Multimedia> finder = new Finder<>(Multimedia.class);

    public static Multimedia findById(Long id){
        return finder.byId(id);
    }

    public static Multimedia findId(Long id){

        return finder.query().where().eq("id", id).setIncludeSoftDeletes().findUnique();
    }

    public static List<Multimedia> findByIds(List<Long> ids){
        return finder.query().where().idIn(ids).findList();
    }

    public static List<Multimedia> findAll(){
        return finder.all();
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultimediaCDN getMultimediaCDN() {
        return multimediaCDN;
    }

    public void setMultimediaCDN(MultimediaCDN multimediaCDN) {
        this.multimediaCDN = multimediaCDN;
    }
}
