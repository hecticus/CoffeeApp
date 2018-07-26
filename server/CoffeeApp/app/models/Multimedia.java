package models;


import io.ebean.Finder;
import multimedia.models.MultimediaCDN;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

@Entity
public class Multimedia extends AbstractEntity {

//    @Constraints.Required
//    @Transient
//    private String media; //base64
//
//    @Transient
//    private String mediaOptional; //base64 for captures

    private String dtype;

    @Constraints.MaxLength(100)
    @Column(length = 100)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, optional = false)
    private MultimediaCDN multimediaCDN;

//    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//    private MultimediaCDN multimediaCDNOptional; // for captures in videos

//    @OneToOne
//    private User user;

    @OneToOne
    private Provider provider;

    private static Finder<Long, Multimedia> finder = new Finder<>(Multimedia.class);

//    @PrePersist
//    public void createMultimedia() throws Exception {
//        try {
//            if(this.media != null) {
//                this.multimediaCDN = new MultimediaCDN();

//                this.multimediaCDN.setPath(this.dtype.concat("/").concat(this.name));
//                this.multimediaCDN.save();
//            }
//
//            if(this.mediaOptional != null) {
//                this.multimediaCDNOptional = new MultimediaCDN();
//                this.multimediaCDNOptional.setMediaBase64(this.media);
//                this.multimediaCDNOptional.setPath(multimediaCDN.getPath());
//                this.multimediaCDNOptional.save();
//            }
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//    }

    public static Multimedia findById(Long id){
        return finder.byId(id);
    }

    public static List<Multimedia> findByIds(List<Long> ids){
        return finder.query().where().idIn(ids).findList();
    }

    public static List<Multimedia> findAll(){
        return finder.all();
    }

//    public String getMedia() {
//        return media;
//    }
//
//    public void setMedia(String media) {
//        this.media = media;
//    }

//    public String getMediaOptional() {
//        return mediaOptional;
//    }
//
//    public void setMediaOptional(String mediaOptional) {
//        this.mediaOptional = mediaOptional;
//    }

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

//    public MultimediaCDN getMultimediaCDNOptional() {
//        return multimediaCDNOptional;
//    }
//
//    public void setMultimediaCDNOptional(MultimediaCDN multimediaCDNOptional) {
//        this.multimediaCDNOptional = multimediaCDNOptional;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

}
