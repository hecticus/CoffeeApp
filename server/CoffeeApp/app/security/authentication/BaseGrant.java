package security.authentication;

import com.hecticus.auth.AuthJWT;
import com.typesafe.config.Config;
import security.models.AuthUser;
import security.models.ClientCredential;
import security.models.SecurityToken;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by nisa on 11/10/17.
 */
public class BaseGrant {

    public static final String TOKEN_TYPE_ACCESS = "access_token";
    public static final String TOKEN_TYPE_REFRESH = "refresh_token";
    public static final String TOKEN_TYPE_BEARER = "bearer";
    private String secret;
    private String issuer;
    private long expiresInAccessToken;
    private long expiresInRefreshToken;
    private AuthJWT authJWT;

    @Inject
    public BaseGrant(Config config){
        //ClassLoader classLoader = Thread.currentThread().getContextClassLoader(); TODO intentar esto en vez de injectar
        //ConfigFactory.load(classLoader,"conf/application.conf");

        secret = config.getString("play.http.secret.key");
        System.out.println("######################3"+ secret);
        authJWT = new AuthJWT(secret);

        Config configToken = config.getConfig("token");
        issuer = configToken.getString("issuer");
        expiresInAccessToken = configToken.getLong("access.ttlMillis");
        expiresInRefreshToken = configToken.getLong("refresh.ttlMillis");
    }

    public Object getClaim(String jwt, String claim) throws Exception {
        return authJWT.getClaim(jwt, claim);
    }

    public void verifyToken(String jwt) throws Exception {
        authJWT.verifyJWT(jwt);
    }

    public SecurityToken verifyRevokeAccessToken(String jwt) throws Exception {
        authJWT.verifyJWT(jwt);
        if(authJWT.getClaim(jwt, "token_type").toString().equals(TOKEN_TYPE_ACCESS)) {
            return SecurityToken.findByIdType(Long.valueOf(authJWT.getClaim(jwt, "jti").toString()), TOKEN_TYPE_ACCESS);
        }
        return null;
    }

    public SecurityToken verifyRevokeRefreshToken(String jwt) throws Exception {
        authJWT.verifyJWT(jwt);
        if(authJWT.getClaim(jwt, "token_type").toString().equals(TOKEN_TYPE_REFRESH)){
            return SecurityToken.findByIdType(Long.valueOf(authJWT.getClaim(jwt, "jti").toString()), TOKEN_TYPE_REFRESH);
        }
        return null;
    }

    public SecurityToken generateSecurityRefreshToken(AuthUser authUser, ClientCredential clientCredential){
        SecurityToken securityToken = new SecurityToken();
        securityToken.setAuthUser(authUser);
        securityToken.setToken("");
        securityToken.setType(TOKEN_TYPE_REFRESH);
        securityToken.insert();
        securityToken.setToken(generateRefreshToken(securityToken.getId().toString(), authUser, clientCredential));
        securityToken.update();
        return securityToken;
    }

    public SecurityToken generateSecurityAccessToken(SecurityToken securityRefreshToken, AuthUser authUser, ClientCredential clientCredential){
        SecurityToken securityToken = new SecurityToken();
        securityToken.setAuthUser(authUser);
        securityToken.setToken("");
        securityToken.setType(TOKEN_TYPE_ACCESS);
        securityToken.setRefreshToken(securityRefreshToken);
        securityToken.insert();
        securityToken.setToken(generateAccessToken(securityToken.getId().toString(), authUser, clientCredential));
        securityToken.update();
        return securityToken;
    }

    public String generateAccessToken(String jti, AuthUser authUser, ClientCredential clientCredential){
        Map mclaims = new HashMap<>();
        mclaims.put("id", authUser.getId());
        mclaims.put("email", authUser.getEmail());
        mclaims.put("client_id", clientCredential.getClientId());
        mclaims.put("token_type", TOKEN_TYPE_ACCESS);
        if(authUser.getAuthGroup() != null) {
            mclaims.put("authGroup", authUser.getAuthGroup().getName());
        }
        List<String> authRoles = new ArrayList<>();
        authUser.getAuthRoles().stream().forEach(authRole -> {
            authRoles.add(authRole.getName());
        });
        mclaims.put("authRoles", authRoles);

        return authJWT.createJWT(
                jti,                    //jti (JWT ID) Claim
                issuer,                 //iss (Issuer) Claim
                authUser.getEmail(),    //sub (Subject) Claim
                expiresInAccessToken,   //exp (Expiration Time) Claim
                mclaims);               //extra claims
    }

    public String generateRefreshToken(String jti, AuthUser authUser, ClientCredential clientCredential){
        Map mclaims = new HashMap<>();
        mclaims.put("email", authUser.getEmail());
        mclaims.put("client_id", clientCredential.getClientId());
        mclaims.put("token_type", TOKEN_TYPE_REFRESH);

        return authJWT.createJWT(jti, issuer, authUser.getEmail(), expiresInRefreshToken, mclaims);
    }
}
