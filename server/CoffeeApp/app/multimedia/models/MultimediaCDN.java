package multimedia.models;

import io.ebean.Finder;
import multimedia.MultimediaUtils;
import multimedia.RackspaceCloudFiles;
import org.apache.tika.mime.MimeTypeException;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author  Yenny Fung
 * @since   2016
 */
@Entity
@Table(name = "multimedia_cdn")
public class MultimediaCDN extends BaseModel {

    private static final String FOLDER_PROFILE_PICTURE_PROVIDER = "FotoPerfil";

    @Transient
    private String path;

    @Constraints.Required
    @Transient
    private String mediaBase64;

    @Column(columnDefinition = "text", nullable = false)
    private String url;

    @Column(length = 200, nullable = false)
    private String mimeType;

    @Column(length = 200, nullable = false)
    private String nameCdn;

    @OneToMany(mappedBy = "multimedia", cascade = CascadeType.ALL)
    private List<RescaleCDN> rescaleCDNS = new ArrayList<>();

    private static Finder<Long, MultimediaCDN> finder = new Finder<>(MultimediaCDN.class);

    @PrePersist
    @PreUpdate
    public void uploadToRackspace() throws IOException, MimeTypeException {
        try {
            if(this.mediaBase64 != null) {
                byte[] bytes = MultimediaUtils.decodeBase64ToBytes(this.mediaBase64);
                this.mimeType = MultimediaUtils.guessMIMEtypeFromBytes(bytes);

                String validPath = MultimediaUtils.transformToValidPath(this.path);
                String uniqueValidPath = MultimediaUtils.transformToUniquePath(validPath);
                String extension = MultimediaUtils.guessExtensionFromMimeType(this.mimeType);
                this.nameCdn = MultimediaUtils.transformToPath(FOLDER_PROFILE_PICTURE_PROVIDER,
                        uniqueValidPath.concat(".").concat(extension));

                this.url = RackspaceCloudFiles.uploadObjectsToContainer(bytes, this.nameCdn);

                if (!this.rescaleCDNS.isEmpty() && this.mimeType.matches("^image/.*")) {
                    BufferedImage image = MultimediaUtils.bytesToImage(bytes);

                    for (int i = 0; i < this.rescaleCDNS.size(); ++i) {
                        RescaleCDN rescaleCDN = this.rescaleCDNS.get(i);
                        BufferedImage resizedImage = MultimediaUtils.resizeImage(image, rescaleCDN.getWidth(), rescaleCDN.getHeight());

                        StringBuilder resizedName = new StringBuilder(uniqueValidPath);
                        if(rescaleCDN.getSufix() != null) {
                           resizedName.append(rescaleCDN.getSufix());
                        }
                        resizedName.append('.').append(extension);
                        rescaleCDN.setNameCdn(resizedName.toString());

                        rescaleCDN.setUrl(RackspaceCloudFiles.uploadObjectsToContainer(resizedImage, extension, rescaleCDN.getNameCdn()));
                    }
                }
            }
        } catch (IOException e) {
            removeFromRackspace();
            throw new IOException("error media:" + e.getMessage());
        }
    }

    @PostRemove
    public void removeFromRackspace() {
        if(this.nameCdn != null) {
            RackspaceCloudFiles.deleteObjectsToContainer(this.nameCdn);
        }
        for (int i = 0; i < this.rescaleCDNS.size(); ++i) {
            RackspaceCloudFiles.deleteObjectsToContainer(this.rescaleCDNS.get(i).getNameCdn());
        }
    }

    public static MultimediaCDN findById(Long id){
        return finder.byId(id);
    }

    public static MultimediaCDN findId(Long id){

        return finder.query().where().eq("id", id).setIncludeSoftDeletes().findUnique();
    }


    public static List<MultimediaCDN> findByIds(List<Long> ids){
        return finder.query().where().idIn(ids).findList();
    }

    public static List<MultimediaCDN> findAll(){
        return finder.all();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMediaBase64() {
        return mediaBase64;
    }

    public void setMediaBase64(String mediaBase64) {
        this.mediaBase64 = mediaBase64;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getNameCdn() {
        return nameCdn;
    }

    public void setNameCdn(String nameCdn) {
        this.nameCdn = nameCdn;
    }

    public List<RescaleCDN> getRescaleCDNS() {
        return rescaleCDNS;
    }

    public void setRescaleCDNS(List<RescaleCDN> rescaleCDNS) {
        this.rescaleCDNS = rescaleCDNS;
    }
}