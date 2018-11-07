package security.authentication;

import io.ebean.Ebean;
import io.jsonwebtoken.ExpiredJwtException;
import play.mvc.Controller;
import play.mvc.Result;
import security.SecurityUtils;
import security.models.AuthUser;
import security.models.ClientCredential;
import security.models.SecurityToken;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 * Created by nisa on 16/10/17.
 *
 * reference: https://tools.ietf.org/html/rfc6749#section-4.3
 */
public class PasswordGrant extends Controller {

    @Inject
    public BaseGrant baseGrant;

    public class AuthorizationRequest {
        public String grant_type;
        public String username;
        public String password;
        public String client_id;
        public String client_secret; // optional

        AuthorizationRequest(String grant_type, String client_id, String username, String password){
            this.grant_type = grant_type;
            this.client_id = client_id;
            this.username = username;
            this.password = password;
        }
    }

    public class AccessTokenRequest {
        public String grant_type;
        public String refresh_token;
        public String client_id;
        public String client_secret; // optional

        AccessTokenRequest(String grant_type, String refresh_token, String client_id){
            this.grant_type = grant_type;
            this.refresh_token = refresh_token;
            this.client_id = client_id;
        }
    }

    public class AccessTokenResponse {
        public String access_token;
        public String token_type;
        public Long expires_in;
        public String refresh_token; // optional
        public String scope; //optional
        public String state; //optional

        AccessTokenResponse(String access_token, String token_type, Long expires_in, String refresh_token){
            this.access_token = access_token;
            this.token_type = token_type;
            this.expires_in = expires_in;
            this.refresh_token = refresh_token;
        }
    }

    public Result token(){
        try {
            Map<String, String[]> request = request().body().asFormUrlEncoded();

            if (!request.containsKey("grant_type")) {
                return ResponseAuth.invalidRequest("Missing 'grant_type' parameter value.");
            }

            switch (request.get("grant_type")[0]) {
                case "password": {
                    String error = SecurityUtils.verifyInvalidRequest(request, "grant_type", "username", "password", "client_id");
                    if (error != null) {
                        return ResponseAuth.invalidRequest("Missing '" + error + "' parameter value.");
                    }
                    AuthorizationRequest authorizationRequest = new AuthorizationRequest(
                            request.get("grant_type")[0],
                            request.get("client_id")[0],
                            request.get("username")[0],
                            request.get("password")[0]
                    );
                    return autenticate(authorizationRequest);
                }
                case BaseGrant.TOKEN_TYPE_REFRESH: {
                    String error = SecurityUtils.verifyInvalidRequest(request, "grant_type", "refresh_token", "client_id");
                    if (error != null) {
                        return ResponseAuth.invalidRequest("Missing '" + error + "' parameter value.");
                    }
                    AccessTokenRequest accessTokenRequest = new AccessTokenRequest(
                            request.get("grant_type")[0],
                            request.get("refresh_token")[0],
                            request.get("client_id")[0]
                    );
                    return refreshAccessToken(accessTokenRequest);
                }
            }
            return ResponseAuth.unsupportedGrantType();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseAuth.internalServerErrorLF(e.getMessage());
        }
    }

    private Result autenticate(AuthorizationRequest authorizationRequest){
        Ebean.beginTransaction();
        try {
            ClientCredential clientCredential = ClientCredential.findByClientId(authorizationRequest.client_id);
            if(clientCredential == null)
                return ResponseAuth.invalidClient("Invalid Client id.");

            AuthUser authUser = AuthUser.findByEmailPassword(authorizationRequest.username, authorizationRequest.password);
            if(authUser == null)
                return ResponseAuth.invalidGrant("Invalid username and/or password.");

            authUser.setLastLogin(ZonedDateTime.now());
            authUser.update();

            SecurityToken securityRefreshToken = baseGrant.generateSecurityRefreshToken(authUser, clientCredential);
            SecurityToken securityAccessToken = baseGrant.generateSecurityAccessToken(securityRefreshToken, authUser, clientCredential);

            Ebean.commitTransaction();

            AccessTokenResponse accessTokenResponse = new AccessTokenResponse(
                    securityAccessToken.getToken(),
                    BaseGrant.TOKEN_TYPE_BEARER,
                    Long.valueOf(baseGrant.getClaim(securityAccessToken.getToken(), "exp").toString()),
                    securityRefreshToken.getToken()
            );

            return ResponseAuth.granted(accessTokenResponse);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseAuth.internalServerErrorLF(e.getMessage());
        }finally {
            Ebean.endTransaction();
        }
    }

    private Result refreshAccessToken(AccessTokenRequest accessTokenRequest){
        Ebean.beginTransaction();
        try {
            ClientCredential clientCredential = ClientCredential.findByClientId(accessTokenRequest.client_id);
            if(clientCredential == null)
                return ResponseAuth.invalidClient("Invalid Client id.");

            SecurityToken securityRefreshToken;
            try {
                securityRefreshToken = baseGrant.verifyRevokeRefreshToken(accessTokenRequest.refresh_token);
            }catch (ExpiredJwtException e) {
                e.printStackTrace();
                return ResponseAuth.expiredToken();
            }catch (Exception e) {
                e.printStackTrace();
                return ResponseAuth.invalidToken();
            }
            if(securityRefreshToken == null) // if null then token was revoked
                return ResponseAuth.invalidToken();

            AuthUser authUser = securityRefreshToken.getAuthUser();

            securityRefreshToken.setToken(baseGrant.generateRefreshToken(securityRefreshToken.getId().toString(), authUser, clientCredential));
            securityRefreshToken.update();

            SecurityToken securityAccessToken = securityRefreshToken.getAccessToken();
            if(securityAccessToken != null) {
                securityAccessToken.setToken(baseGrant.generateAccessToken(securityAccessToken.getId().toString(), authUser, clientCredential));
                securityAccessToken.update();
            } else {
                securityAccessToken = baseGrant.generateSecurityAccessToken(securityRefreshToken, authUser, clientCredential);
            }
            Ebean.commitTransaction();

            AccessTokenResponse accessTokenResponse = new AccessTokenResponse(
                    securityAccessToken.getToken(),
                    BaseGrant.TOKEN_TYPE_BEARER,
                    Long.valueOf(baseGrant.getClaim(securityAccessToken.getToken(), "exp").toString()),
                    securityRefreshToken.getToken()
            );

            return ResponseAuth.granted(accessTokenResponse);

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseAuth.internalServerErrorLF(e.getMessage());
        }finally {
            Ebean.endTransaction();
        }
    }
}