package security.authentication;

import io.ebean.*;
import play.mvc.Controller;
import play.mvc.Result;
import security.models.SecurityToken;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class RevokeToken extends Controller {
    /*
        reference:
        https://tools.ietf.org/html/rfc7009
        https://www.oauth.com/oauth2-servers/listing-authorizations/revoking-access/
        https://developer.okta.com/authentication-guide/tokens/revoking-tokens

        The token revocation endpoint can revoke either access or refresh tokens. Revoking an access token does not revoke the associated refresh token, though revoking a refresh token does revoke the associated access token.
        Revoking only the access token will effectively force the use of the refresh token to retrieve a new access token. This could be useful if, for example, you have changed a user’s data and you want this information to be reflected in a new access token.
        If you revoke only the refresh token then the access token will also be revoked. This allows you to, for example, force a user to reauthenticate.
    */

    @Inject
    public BaseGrant baseGrant;

    public class RevokeRequest {
        public String token;
        public String token_type_hint; // optional - access_token or refresh_token

        RevokeRequest(String token){
            this.token = token;
        }
    }

    public Result revoke(){
        try {
            Map<String, String[]> request = request().body().asFormUrlEncoded();

            if (!request.containsKey("token")) {
                return ResponseAuth.invalidRequest("token");
            }

            RevokeRequest revokeRequest = new RevokeRequest(request.get("token")[0]);

            if(request.containsKey("token")) {
                revokeRequest.token_type_hint = request.get("token_type_hint")[0];
            }else{
                revokeRequest.token_type_hint = baseGrant.getClaim(revokeRequest.token, "token_type").toString();
            }

            switch (revokeRequest.token_type_hint) {
                case BaseGrant.TOKEN_TYPE_ACCESS:
                    return revokeAccessToken(revokeRequest.token);
                case BaseGrant.TOKEN_TYPE_REFRESH:
                    return revokeRefreshToken(revokeRequest.token);
            }

            return ResponseAuth.unsupportedGrantType(); // TODO
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseAuth.internalServerErrorLF(e.getMessage());
        }
    }

    private Result revokeAccessToken(String token){
        try {
            Long authUserId = Long.valueOf(baseGrant.getClaim(token, "id").toString());
            List<SecurityToken> securityTokens = SecurityToken.findAllByAuthUserIdType(authUserId, baseGrant.TOKEN_TYPE_ACCESS);

            if (securityTokens != null && !securityTokens.isEmpty())
                Ebean.deleteAll(securityTokens);

            return ResponseAuth.noContentLF(); // TODO 200
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseAuth.internalServerErrorLF(e.getMessage());
        }
    }

    private Result revokeRefreshToken(String token){
        try {
            Long id = Long.valueOf(baseGrant.getClaim(token, "jti").toString());
            SecurityToken securityRefreshToken = SecurityToken.findByIdType(id, baseGrant.TOKEN_TYPE_REFRESH);

            if (securityRefreshToken != null)
                securityRefreshToken.delete();

            return ResponseAuth.noContentLF(); // TODO 200
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseAuth.internalServerErrorLF(e.getMessage());
        }
    }

    //@HSecurity
    public Result deleteUseless() {
        List<SecurityToken> securityRefreshTokens = SecurityToken.findAllByType(baseGrant.TOKEN_TYPE_REFRESH);
        if(securityRefreshTokens != null) {
            securityRefreshTokens.stream().forEach(securityToken -> {
                try {
                    baseGrant.verifyToken(securityToken.getToken());
                } catch (Exception e) {
                    securityToken.delete();
                }
            });
        }

        List<SecurityToken> securityAccessTokens = SecurityToken.findAllByType(baseGrant.TOKEN_TYPE_ACCESS);
        if(securityAccessTokens != null) {
            securityAccessTokens.stream().forEach(securityToken -> {
                try {
                    baseGrant.verifyToken(securityToken.getToken());
                } catch (Exception e) {
                    securityToken.delete();
                }
            });
        }
        return ResponseAuth.noContentLF();
    }

    //@HSecurity
    public Result deleteAll() {
        List<SecurityToken> securityTokens = SecurityToken.findAll();

        if(securityTokens != null && !securityTokens.isEmpty())
            Ebean.deleteAll(securityTokens);

        return ResponseAuth.noContentLF();
    }
}
