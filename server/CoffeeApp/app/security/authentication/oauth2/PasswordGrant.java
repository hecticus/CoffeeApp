package security.authentication.oauth2;

import play.mvc.Controller;
import play.mvc.Result;
import security.models.AuthUser;
import security.models.ClientCredential;
import security.models.SecurityToken;

import javax.inject.Inject;
import java.util.Map;

import static security.authentication.oauth2.Request.verifyInvalidRequest;

/**
 * Created by nisa on 16/10/17.
 *
 * reference: https://tools.ietf.org/html/rfc6749#section-4.3
 */
public class PasswordGrant extends Controller {

    public static final String GRANT_TYPE_PASSWORD = "password";
    public static final String GRANT_TYPE_REFRESH_ACCESS_TOKEN = "refresh_token";

    @Inject
    public BaseGrant baseGrant;

    public class AuthorizationRequest {
        public String grant_type;
        public String username;
        public String password;
        public String client_id;
        public String client_secret;
    }

    public class AccessTokenRequest {
        public String grant_type;
        public String refresh_token;
        public String client_id;
        public String client_secret;
    }

    public class AccessTokenResponse {
        public String access_token;
        public String token_type;
        public Long expires_in;
        public String refresh_token;
        public String scope; //optional
        public String state; //optional
        public Long user_id;
    }

    public Result token(){
        try {
            Map<String, String[]> request = request().body().asFormUrlEncoded();
            if(!request.containsKey("grant_type"))
                return Response.invalidRequest("grant_type");

            switch (request.get("grant_type")[0]) {
                case GRANT_TYPE_PASSWORD:
                    return autenticate(request);
                case GRANT_TYPE_REFRESH_ACCESS_TOKEN:
                    return refreshAccessToken(request);
                default:
                    break;
            }
            return Response.unsupportedGrantType();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.internalServerErrorLF(e.getMessage());
        }
    }

    public Result revokeToken(){
        Map<String, String[]> request = request().body().asFormUrlEncoded();
        if(!request.containsKey("refresh_token"))
            return Response.invalidRequest("refresh_token");

        try {
            baseGrant.revokeToken(request.get("refresh_token")[0]);
            return Response.noContentLF();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.invalidGrant("verifing refresh_token");
        }
    }

    private Result autenticate(Map<String, String[]> request){
        AuthorizationRequest authorizationRequest = new AuthorizationRequest();
        try {
            verifyInvalidRequest(request, new String[]{"grant_type", "username", "password", "client_id"});
            authorizationRequest.grant_type = request.get("grant_type")[0];
            authorizationRequest.client_id = request.get("client_id")[0];
            authorizationRequest.username = request.get("username")[0];
            authorizationRequest.password = request.get("password")[0];
        }catch (NullPointerException e) {
            e.printStackTrace();
            return Response.invalidRequest(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return Response.invalidRequest();
        }

        ClientCredential clientCredential = baseGrant.verifyClient(authorizationRequest.client_id);
        if(clientCredential == null)
            return Response.invalidClient();

        AuthUser authUser = baseGrant.verifyAuthUser(authorizationRequest.username, authorizationRequest.password);
        if(authUser == null)
            return Response.invalidGrant();

        Map mclaim = new Claim(authUser.getId(), authUser.getEmail(), clientCredential.getClientId()).toMap();
        AccessTokenResponse accessTokenResponse = new AccessTokenResponse();
        accessTokenResponse.token_type = BaseGrant.TOKEN_TYPE_BEARER;
        accessTokenResponse.access_token = baseGrant.generateAccessToken(null, authUser.getEmail(), mclaim);
        accessTokenResponse.expires_in = baseGrant.getExpiresInAccessToken();
        accessTokenResponse.refresh_token = baseGrant.generateRefreshToken(null, authUser.getEmail(), mclaim);
        accessTokenResponse.user_id = authUser.getId();

        try {
            baseGrant.storeToken(accessTokenResponse.refresh_token);
        }catch (Exception e) {
            e.printStackTrace();
            return Response.invalidGrant("storing refresh_token");
        }
        return Response.granted(accessTokenResponse);
    }

    private Result refreshAccessToken(Map<String, String[]> request){
        AccessTokenRequest accessTokenRequest = new AccessTokenRequest();
        try {
            verifyInvalidRequest(request, new String[]{"grant_type", "refresh_token", "client_id"});
            accessTokenRequest.grant_type = request.get("grant_type")[0];
            accessTokenRequest.refresh_token = request.get("refresh_token")[0];
            accessTokenRequest.client_id = request.get("client_id")[0];
        }catch (NullPointerException e) {
            e.printStackTrace();
            return Response.invalidRequest(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return Response.invalidRequest();
        }

        ClientCredential clientCredential = baseGrant.verifyClient(accessTokenRequest.client_id);
        if(clientCredential == null)
            return Response.invalidGrant();

        SecurityToken securityToken = baseGrant.verifyRefreshToken(accessTokenRequest.refresh_token);
        if(securityToken == null)
            return Response.invalidToken();

        AuthUser authUser = securityToken.getAuthUser();
        Map mclaim = new Claim(authUser.getId(), authUser.getEmail(), clientCredential.getClientId()).toMap();
        AccessTokenResponse accessTokenResponse = new AccessTokenResponse();
        accessTokenResponse.token_type = BaseGrant.TOKEN_TYPE_BEARER;
        accessTokenResponse.access_token = baseGrant.generateAccessToken(null, authUser.getEmail(), mclaim);
        accessTokenResponse.expires_in = baseGrant.getExpiresInAccessToken();
        accessTokenResponse.refresh_token = baseGrant.generateRefreshToken(null, authUser.getEmail(), mclaim);
        accessTokenResponse.user_id = authUser.getId();

        try {
            baseGrant.updateToken(accessTokenRequest.refresh_token, accessTokenResponse.refresh_token);
        }catch (Exception e) {
            e.printStackTrace();
            return Response.invalidGrant("updating refresh_token");
        }
        return Response.granted(accessTokenResponse);
    }
}