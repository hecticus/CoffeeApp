package security.authentication.oauth2;

import com.hecticus.auth.AuthJWT;
import com.typesafe.config.Config;
import security.models.AuthUser;
import security.models.ClientCredential;
import security.models.SecurityToken;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by nisa on 11/10/17.
 */
public class BaseGrant {

    public static final String TOKEN_TYPE_ACCESS = "access_token";
    public static final String TOKEN_TYPE_REFRESH = "refresh_token";
    public static final String TOKEN_TYPE_BEARER = "bearer";
    private String secret;
    private String issuer;
    private Long expiresInAccessToken;
    private Long expiresInRefreshToken;
    private AuthJWT authJWT;

    @Inject
//    public BaseGrant(Configuration config){
    public BaseGrant(Config config){
        //ClassLoader classLoader = Thread.currentThread().getContextClassLoader(); TODO intentar esto en vez de injectar
        //ConfigFactory.load(classLoader,"conf/application.conf");

//        secret = config.getString("play.crypto.secret");
        secret = config.getString("play.http.secret.key");
        authJWT = new AuthJWT(secret);

        Config configToken = config.getConfig("play.token");
        issuer = configToken.getString("issuer");
        expiresInAccessToken = configToken.getLong("refresh.ttlMillis");
        expiresInRefreshToken = configToken.getLong("access.ttlMillis");
    }

    public Long getExpiresInAccessToken(){
        return expiresInAccessToken;
    }

    public ClientCredential verifyClient(String clientId){
        return ClientCredential.findByClientId(clientId);
    }

    public ClientCredential verifyClient(String clientId, String clientSecret){
        return ClientCredential.findByClientIdSecret(clientId, clientSecret);
    }

    public ClientCredential verifyClientUri(String clientId, String redirectUri){
        return ClientCredential.findByClientIdUri(clientId, redirectUri);
    }

    public AuthUser verifyAuthUser(String email, String password){
        return  AuthUser.findByEmailPassword(email, password);
    }

    public AuthUser verifyAccessToken(String jwt){
        try {
            authJWT.verifyJWT(jwt);
            if(authJWT.getClaim(jwt, "token_type").toString().equals(TOKEN_TYPE_ACCESS))
                return AuthUser.findById(Long.valueOf(authJWT.getClaim(jwt, "id").toString()));
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public SecurityToken verifyRefreshToken(String jwt){
        try {
            authJWT.verifyJWT(jwt);
            if(authJWT.getClaim(jwt, "token_type").toString().equals(TOKEN_TYPE_REFRESH)){
                SecurityToken securityToken = SecurityToken.findByAuthUserIdToken(Long.valueOf(authJWT.getClaim(jwt, "id").toString()), jwt);
                if(securityToken != null && securityToken.getAuthUser() != null)
                    return securityToken;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String generateAccessToken(String jti, String sub, Map mclaim){
        mclaim.put("token_type", TOKEN_TYPE_ACCESS);
        return authJWT.createJWT(
                jti,                    //jti
                issuer,                 //iss
                sub,                    //sub
                expiresInAccessToken,   //exp
                mclaim);                //iat
    }

    public String generateRefreshToken(String jti, String sub, Map mclaim){
        mclaim.put("token_type", TOKEN_TYPE_REFRESH);
        return authJWT.createJWT(jti, issuer, sub, expiresInRefreshToken, mclaim);
    }

    /*
    *
    */
    public SecurityToken storeToken(String jwt) throws Exception {
        AuthUser authUser = new AuthUser();
        authUser.setId(Long.valueOf(authJWT.getClaim(jwt, "id").toString()));

        SecurityToken securityToken = new SecurityToken();
        securityToken.setAuthUser(authUser);
        securityToken.setToken(jwt);
        securityToken.insert();
        return securityToken;
    }

    public SecurityToken updateToken(String jwtCurrent, String jwtNew) throws Exception {
        SecurityToken securityToken = SecurityToken.findByAuthUserIdToken(Long.valueOf(authJWT.getClaim(jwtCurrent, "id").toString()), jwtCurrent);
        securityToken.setToken(jwtNew);
        securityToken.update();
        return securityToken;
    }

    public void revokeToken(String jwt) throws Exception {
        SecurityToken securityToken = SecurityToken.findByAuthUserIdToken(Long.valueOf(authJWT.getClaim(jwt, "id").toString()), jwt);
        if(securityToken != null)
            securityToken.delete();
    }
}
