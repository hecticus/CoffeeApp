/*
package security;

import com.hecticus.auth.AuthJWT;
import io.jsonwebtoken.SignatureException;
import models.domain.User;
import play.mvc.Http;
import play.mvc.Security;

public class security extends  Security.Authenticator  {

    @Override
    public String getUsername(Http.Context ctx) {
        String token = getTokenFromHeader(ctx);
        if (token != null) {
            User user = User.find.where().eq("authToken", token).findUnique();
            if (user != null) {
                return user.username;
            }
        }
        return null;
    }


    private String getTokenFromHeader(Http.Context ctx) {
        String[] authTokenHeaderValues = ctx.request().headers().get("Authorization");
        if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (authTokenHeaderValues[0] != null)) {
            String token = authTokenHeaderValues[0];

            AuthJWT aut = new AuthJWT(appConfiguration.getString("play.crypto.secret"));

            try {
                aut.verifyJWT(token);
                return token;

            }catch (SignatureException e){
                return null;
            }

        }
        return null;
    }
}

}
*/
