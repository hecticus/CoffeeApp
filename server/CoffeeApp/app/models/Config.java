package models;

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
    @Column(length = 50, unique = true, nullable = false)
    private Long id;

    @Constraints.Required
    @Constraints.MaxLength(50)
    @Column(length = 50, nullable = false)
    private String configKey;

    @Constraints.Required
    @Column(nullable = false)
    private String configValue;

    @Column(columnDefinition = "text")
    private String description;

    public static Finder<Long, Config> finder = new Finder<Long, Config>(Config.class);

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfigvalue() {
        return configValue;
    }

    public void setConfigvalue(String configvalue) {
        this.configValue = configvalue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String getString(String key){
        Config config = finder.query().where().eq("configKey", key).findUnique();
        return config.getConfigvalue();
    }

    public static void setString(String key, String value){
        Config config = finder.query().where().eq("configKey", key).findUnique();
        if(config != null){
            config.setConfigvalue(value);
            config.update();
        }else{
            config = new Config();
            config.setConfigKey(key);
            config.setConfigvalue(value);
            config.save();
        }
    }

    public static void setString(String key, String value, String description){
        Config config = finder.query().where().eq("configKey", key).findUnique();
        if(config != null){
            config.setConfigvalue(value);
            config.setDescription(description);
            config.update();
        }else{
            config = new Config();
            config.setConfigKey(key);
            config.setConfigvalue(value);
            config.setDescription(description);
            config.save();
        }
    }
}
