package multimedia.models;

import io.ebean.Ebean;
import io.ebean.Finder;
import io.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import controllers.parsers.jsonParser.CustomDeserializer.CustomDateTimeDeserializer;
import controllers.parsers.jsonParser.customSerializer.CustomDateTimeSerializer;
import models.Provider;
import multimedia.MultimediaUtils;
import multimedia.RackspaceCloudFiles;
import org.apache.tika.mime.MimeTypeException;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author  Yenny Fung
 * @since   2016
 */
@Entity
public class Media extends Model {

    @Column(length = 50, nullable = false, updatable = false)
    private String dtype;

    @Transient
    private String dtypeResolution;

    @Id
    protected Long id;

    @Constraints.Required
    @Transient
    private String media;

    @Column(columnDefinition = "text", nullable = false)
    private String url;

    @Column(length = 200, nullable = false)
    private String nameCdn;

    @Column(length = 200, nullable = false)
    private String mimeType;

    @Constraints.MaxLength(50)
    @Column(length = 50)
    private String size;

    @Constraints.MaxLength(100)
    @Column(length = 100)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Transient
    private String mediaOptional; //for captures

    @Column(columnDefinition = "text")
    private String urlOptional; //for captures

    @Column(length = 200)
    private String nameCdnOptional; //for captures

    private String mimeTypeOptional; //for captures

    @Transient
    private List<String> urlResolutions = new ArrayList<>();
    public List<String> getUrlResolutions(){
        this.urlResolutions = new ArrayList<>();
        for(Resolution resolution: this.resolutions) {
            String prefix = this.url.substring(0, this.url.lastIndexOf("."));
            String sufix = this.nameCdn.substring(this.nameCdn.lastIndexOf("."));
            String nameCDNResize = prefix.concat("_" + resolution.getName()).concat(sufix);

            this.urlResolutions.add(nameCDNResize);
        }
        return this.urlResolutions;
    }

    /*
     * get name with the resolutions
     */
    @Transient
    private List<String> nameCdnResolutions = new ArrayList<>();
    public List<String> getNameCdnResolutions(){
        this.nameCdnResolutions = new ArrayList<>();
        for(Resolution resolution: this.resolutions) {
            String prefix = this.nameCdn.substring(0, this.nameCdn.lastIndexOf(".")); // image.extesion
            String sufix = this.nameCdn.substring(this.nameCdn.lastIndexOf(".")); // .extesion
            String nameCDNResize = prefix.concat("_" + resolution.getName()).concat(sufix);

            this.nameCdnResolutions.add(nameCDNResize);
        }
        return this.nameCdnResolutions;
    }

    @ManyToMany(cascade = CascadeType.REMOVE)
    private List<Resolution> resolutions = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Provider> providersModels = new ArrayList<>();

//    @ManyToMany(cascade = CascadeType.REMOVE)
//    @JsonIgnore
//    private List<PartModel> partModels = new ArrayList<>();
//
//    @ManyToMany(cascade = CascadeType.REMOVE)
//    @JsonIgnore
//    private List<OrderTask> orderTasks = new ArrayList<>();

    @Formats.DateTime(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssX")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @CreatedTimestamp //Automatically gets set to the current date and time when the record is first created
    //@Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    protected ZonedDateTime createdAt;

    @Formats.DateTime(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssX")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @UpdatedTimestamp //Automatically gets set to the current date and time whenever the record is updated
    //@Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    protected ZonedDateTime updatedAt;

    public static Finder<Long, Media> finder = new Finder<>(Media.class);

    @PrePersist
    @PreUpdate
    public void uploadToRackspace() throws IOException, MySQLIntegrityConstraintViolationException, MimeTypeException {
        try {
            if(this.media != null) {
                byte[] bytes = MultimediaUtils.decodeBase64ToBytes(this.media);
                String mimeType = MultimediaUtils.guessMIMEtypeFromBytes(bytes);
                String prefix = MultimediaUtils.transformToPath(this.dtype, MultimediaUtils.transformToUniqueValidName(this.name));
                String sufix = MultimediaUtils.guessExtensionFromMimeType(mimeType);

                this.mimeType = mimeType;
                this.nameCdn = prefix.concat(".").concat(sufix);
                this.url = RackspaceCloudFiles.uploadObjectsToContainer(bytes, this.nameCdn);
                this.media = null; // para que no retorne el base64 en el response

                if (mimeType.matches("^image/.*")) {
                    this.resolutions = (this.dtypeResolution != null) ? Resolution.findAllByDtype(this.dtypeResolution) : new ArrayList<>();
                    if (!this.resolutions.isEmpty()) {
                        BufferedImage image = MultimediaUtils.bytesToImage(bytes);
                        for (int i = 0; i < resolutions.size(); ++i) {
                            BufferedImage resizedImage = MultimediaUtils.resizeImage(image, resolutions.get(i).getWidth(), resolutions.get(i).getHeight());
                            String resizedName = prefix.concat("_").concat(resolutions.get(i).getName()).concat(".").concat(sufix);
                            RackspaceCloudFiles.uploadObjectsToContainer(resizedImage, sufix, resizedName);
                        }
                    }
                }
            }
        } catch (IOException e) {
            removeFromRackspace();
            throw new IOException("error media:" + e.getMessage());
        }

        try {
            if(this.mediaOptional != null) {
                byte[] bytes = MultimediaUtils.decodeBase64ToBytes(this.mediaOptional);
                String mimeType = MultimediaUtils.guessMIMEtypeFromBytes(bytes);
                String sufix = MultimediaUtils.guessExtensionFromMimeType(mimeType);
                String prefix = (this.nameCdn != null && !this.nameCdn.isEmpty()) ?
                        this.nameCdn.substring(0, this.nameCdn.lastIndexOf(".")) :
                        MultimediaUtils.transformToPath(this.dtype, MultimediaUtils.transformToUniqueValidName(this.name));

                this.mimeTypeOptional = mimeType;
                this.nameCdnOptional = prefix.concat("_capture.").concat(sufix);
                this.urlOptional = RackspaceCloudFiles.uploadObjectsToContainer(bytes, this.nameCdnOptional);
                this.mediaOptional = null;
            }
        }catch (IOException e){
            removeFromRackspace();
            throw new IOException("error mediaOptional:" + e.getMessage());
        }
    }

    @PostRemove
    public void removeFromRackspace() {
        if(this.nameCdn != null) {
            RackspaceCloudFiles.deleteObjectsToContainer(this.nameCdn);
        }
        if(!this.resolutions.isEmpty()){
            RackspaceCloudFiles.deleteObjectsToContainer(getNameCdnResolutions());
        }
        if (this.nameCdnOptional != null) {
            RackspaceCloudFiles.deleteObjectsToContainer(this.nameCdnOptional);
        }
    }

    public void removeNameCdnInRackspace() {
        if(this.nameCdn != null) {
            RackspaceCloudFiles.deleteObjectsToContainer(this.nameCdn);
        }
        if(!this.resolutions.isEmpty()){
            RackspaceCloudFiles.deleteObjectsToContainer(getNameCdnResolutions());
        }
    }

    public void removeNameCdnOptionalInRackspace() {
        if (this.nameCdnOptional != null) {
            RackspaceCloudFiles.deleteObjectsToContainer(this.nameCdnOptional);
        }
    }

    public static Media findById(Long id){
        return finder.byId(id);
    }

    public static List<Media> findByIds(List<Long> ids){
        return finder.query().where().idIn(ids).findList();
    }

    public static List<Media> findAll(){
        return finder.all();
    }

    public static void delete(Long id) {
        Media entity = finder.byId(id);
        if(entity != null)
            Ebean.delete(entity);
    }

    public static void delete(List<Long> ids) {
        List<Media> entities = finder.query().where().idIn(ids).findList();
        if(!entities.isEmpty())
            Ebean.deleteAll(entities);
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getDtypeResolution() {
        return dtypeResolution;
    }

    public void setDtypeResolution(String dtypeResolution) {
        this.dtypeResolution = dtypeResolution;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
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

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public String getMediaOptional() {
        return mediaOptional;
    }

    public void setMediaOptional(String mediaOptional) {
        this.mediaOptional = mediaOptional;
    }

    public String getUrlOptional() {
        return urlOptional;
    }

    public void setUrlOptional(String urlOptional) {
        this.urlOptional = urlOptional;
    }

    public String getNameCdnOptional() {
        return nameCdnOptional;
    }

    public void setNameCdnOptional(String nameCdnOptional) {
        this.nameCdnOptional = nameCdnOptional;
    }

    public String getMimeTypeOptional() {
        return mimeTypeOptional;
    }

    public void setMimeTypeOptional(String mimeTypeOptional) {
        this.mimeTypeOptional = mimeTypeOptional;
    }

    public void setUrlResolutions(List<String> urlResolutions) {
        this.urlResolutions = urlResolutions;
    }

    public void setNameCdnResolutions(List<String> nameCdnResolutions) {
        this.nameCdnResolutions = nameCdnResolutions;
    }

    public List<Resolution> getResolutions() {
        return resolutions;
    }

    public void setResolutions(List<Resolution> resolutions) {
        this.resolutions = resolutions;
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