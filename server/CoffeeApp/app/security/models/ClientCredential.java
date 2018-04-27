package security.models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import models.domain.AbstractEntity;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by nisa on 06/10/17.
 *
 * The Client Credentials grant is used when applications request an access token to access their own resources, not on behalf of a user
 * reference: https://www.oauth.com/oauth2-servers/access-tokens/client-credentials/
 */
@Entity
@Table(name = "auth_client_credential")
public class /**/ClientCredential extends AbstractEntity {

    @Id
    private Long id;

    @Constraints.Required
    @Constraints.MaxLength(50)
    @Column(length = 50, unique = true, nullable = false)
    private String clientId;

    @Constraints.Required
    @Constraints.MaxLength(100)
    @Column(length = 100)
    @JsonIgnore
    private String clientSecret;

    @Constraints.Required
    @Constraints.MaxLength(100)
    @Column(length = 100, nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String icon;

    @Column(columnDefinition = "text")
    private String homePageUrl;

    @Column(columnDefinition = "text")
    private String privacyPolicyUrl;

    @Column(columnDefinition = "text")
    private String authCallbackUri;

    private String description;

    private static Model.Finder<Long, ClientCredential> finder = new Model.Finder<>(ClientCredential.class);

    public static ClientCredential findByClientIdUri(String clientId, String redirectUri){
        ClientCredential clientCredential = finder.where().eq("clientId", clientId).findUnique();

        if(clientCredential != null){
            String authCallbackUri = clientCredential.getAuthCallbackUri();
            if(authCallbackUri != null){
                String[] authCallbackUris = authCallbackUri.split(",");
                for(int i = 0; i < authCallbackUris.length; ++i){
                    if(authCallbackUris[i].equals(redirectUri))
                        return clientCredential;
                }
            }
        }
        return null;
    }

    public static ClientCredential findByClientIdSecret(String clientId, String clientSecret){
        return finder.where()
                .eq("clientId", clientId)
                .eq("clientSecret", clientSecret)
                .findUnique();
    }

    public static ClientCredential findByClientId(String clientId){
        return finder.where()
                .eq("clientId", clientId)
                .findUnique();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHomePageUrl() {
        return homePageUrl;
    }

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    public String getPrivacyPolicyUrl() {
        return privacyPolicyUrl;
    }

    public void setPrivacyPolicyUrl(String privacyPolicyUrl) {
        this.privacyPolicyUrl = privacyPolicyUrl;
    }

    public String getAuthCallbackUri() {
        return authCallbackUri;
    }

    public void setAuthCallbackUri(String authCallbackUri) {
        this.authCallbackUri = authCallbackUri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
