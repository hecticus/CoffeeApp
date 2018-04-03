package models.domain;

import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by drocha on 10/24/16.
 */
@Entity
public class Config extends Model {

    @Id
    private Long idConfig;

    @Constraints.Required
    @Constraints.MaxLength(50)
    @Column(length = 50, nullable = false)
    private String configKey;

    @Constraints.Required
    @Column(nullable = false)
    private String value;

    private String description;

    public static Finder<Long, Config> finder = new Finder<Long, Config>(Config.class);

    public Long getIdConfig() {
        return idConfig;
    }

    public void setIdConfig(Long idConfig) {
        this.idConfig = idConfig;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String getString(String key){
        Config config = finder.query().where().eq("configKey", key).findUnique();
        return config.getValue();
    }

    public static void setString(String key, String value){
        Config config = finder.query().where().eq("configKey", key).findUnique();
        if(config != null){
            config.setValue(value);
            config.update();
        }else{
            config = new Config();
            config.setConfigKey(key);
            config.setValue(value);
            config.save();
        }
    }

    public static void setString(String key, String value, String description){
        Config config = finder.query().where().eq("configKey", key).findUnique();
        if(config != null){
            config.setValue(value);
            config.setDescription(description);
            config.update();
        }else{
            config = new Config();
            config.setConfigKey(key);
            config.setValue(value);
            config.setDescription(description);
            config.save();
        }
    }
}
