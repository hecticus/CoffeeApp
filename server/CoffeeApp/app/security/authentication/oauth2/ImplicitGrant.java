package security.authentication.oauth2;

import com.typesafe.config.Config;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Map;

import static security.authentication.oauth2.Request.verifyInvalidRequest;

/**
 * Created by nisa on 16/10/17.
 *
 * reference: https://tools.ietf.org/html/rfc6749#section-4.1
 *
 */
public class ImplicitGrant extends Controller{

    public static final String RESPONSE_TYPE_IMPLICIT = "token";
    private static BaseGrant baseGrant;

    public class AuthorizationRequest {
        public String response_type;
        public String client_id;
        public String redirect_uri;
        public String scope;
        public String state;
    }

    public class AccessTokenResponse {
        public String access_token;
        public String token_type;
        public Long expires_in;
        public String refresh_token;
        public String scope; //optional
        public String state; //optional
    }

    @Inject
    ImplicitGrant(Config config){
        baseGrant = new BaseGrant(config);
    }

    public Result auth(){
        try {
            Map<String, String[]> request = request().body().asFormUrlEncoded();
            if(!request.containsKey("response_type"))
                return Response.invalidRequest("response_type");

            switch (request.get("response_type")[0]) {
                case RESPONSE_TYPE_IMPLICIT:
                    return authenticate(request);
                default:
                    break;
            }
            return Response.unsupportedGrantType();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.internalServerErrorLF();
        }
    }

    private Result authenticate(Map<String, String[]> request){
        AuthorizationRequest authorizationRequest = new AuthorizationRequest();
        try {
            verifyInvalidRequest(request, new String[]{"response_type", "client_id", "redirect_uri"});
            authorizationRequest.client_id = request.get("client_id")[0];
            authorizationRequest.redirect_uri = request.get("username")[0];
        }catch (NullPointerException e) {
            return Response.invalidRequest(e.getMessage());
        }catch (Exception e) {
            return Response.invalidRequest();
        }
        return Response.internalServerErrorLF();
    }
}
