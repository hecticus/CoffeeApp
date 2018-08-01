package multimedia.models;

import io.ebean.Finder;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "multimedia_rescale_cdn")
public class RescaleCDN extends BaseModel{

    @Constraints.Required
    @Column(nullable = false)
    private Integer width;

    @Constraints.Required
    @Column(nullable = false)
    private Integer height;

    @Constraints.MaxLength(10)
    @Column(length = 10)
    private String sufix;

    @Column(columnDefinition = "text", nullable = false)
    private String url;

    @Column(length = 200, nullable = false)
    private String nameCdn;

    @Constraints.Required
    @ManyToOne(optional = false)
    private MultimediaCDN multimedia;

    private static Finder<Long, RescaleCDN> finder = new Finder<>(RescaleCDN.class);

    public static RescaleCDN findById(Long id){
        return finder.byId(id);
    }

    public static List<RescaleCDN> findAll(){
        return finder.query().findList();
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

    public String getSufix() {
        return sufix;
    }

    public void setSufix(String sufix) {
        this.sufix = sufix;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNameCdn() {
        return nameCdn;
    }

    public void setNameCdn(String nameCdn) {
        this.nameCdn = nameCdn;
    }

    public MultimediaCDN getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(MultimediaCDN multimedia) {
        this.multimedia = multimedia;
    }
}
