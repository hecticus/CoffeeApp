package security.authorization;

import io.jsonwebtoken.ExpiredJwtException;
import play.Configuration;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import security.authentication.BaseGrant;
import security.authentication.ResponseAuth;
import security.models.AuthPermission;
import security.models.SecurityToken;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by nisa on 26/10/17.
 *
 */
public class HecticusSecurity extends Action<HSecurity> {

    @Inject
    public BaseGrant baseGrant;
    private static Boolean enabled;

    @Inject
    HecticusSecurity(Configuration config){
        enabled = config.getBoolean("authorization.enabled");
    }

    public CompletionStage<Result> call(Http.Context ctx)  {
        /*
        * reference: https://stackoverflow.com/questions/25646335/play-framework-2-2-how-to-get-current-controller-and-action-in-java
        * ROUTE_VERB          -> The HTTP verb used (ex: GET)
        * ROUTE_ACTION_METHOD -> The method called (ex: getUserFavorites)
        * ROUTE_CONTROLLER    -> Controller class (ex: controllers.api.UsersController)
        * ROUTE_COMMENTS      -> ???
        * ROUTE_PATTERN       -> THE URL used (ex: users/favorites)
        */
        try {
            String accessToken = getTokenFromHeader(ctx);

            if (enabled) { // if enable = true then validate permission
                if (accessToken == null)
                    return CompletableFuture.completedFuture(ResponseAuth.invalidRequest("Missing 'access_token' parameter value."));

                SecurityToken securityToken;
                try {
                    securityToken = baseGrant.verifyRevokeAccessToken(accessToken);
                }catch (ExpiredJwtException e){
                    //e.printStackTrace();
                    System.out.println("access_token expired " + accessToken);
                    return CompletableFuture.completedFuture(ResponseAuth.expiredToken());
                }catch (Exception e){
                    e.printStackTrace();
                    return CompletableFuture.completedFuture(ResponseAuth.invalidToken());
                }
                if (securityToken == null) // if null then token was revoked
                    return CompletableFuture.completedFuture(ResponseAuth.invalidToken());

                String[] pathSplited = (ctx.args.get("ROUTE_CONTROLLER").toString()).split("\\.");
                String permission = pathSplited[pathSplited.length - 1] + "." + ctx.args.get("ROUTE_ACTION_METHOD");  // only keep the class name of the path + method name
                if (!AuthPermission.hasPermmission(permission, securityToken.getAuthUser().getId()))
                    return CompletableFuture.completedFuture(ResponseAuth.insufficientScope());
            }

            if (accessToken != null) { // obtener usuario para los endpoints que requieran trabajar con el usuario de la consulta
                try {
                    ctx.args.put("authUserId", baseGrant.getClaim(accessToken, "id"));
                } catch (ExpiredJwtException e) {
                    e.printStackTrace();
                    return CompletableFuture.completedFuture(ResponseAuth.expiredToken());
                } catch (Exception e) {
                    e.printStackTrace();
                    return CompletableFuture.completedFuture(ResponseAuth.invalidToken());
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            return CompletableFuture.completedFuture(ResponseAuth.internalServerErrorLF(e.getMessage()));
        }
        return delegate.call(ctx);
    }

    public static String getTokenFromHeader(Http.Context ctx) {
        if(ctx.request().headers().containsKey("Authorization")){
            String authorization = ctx.request().getHeader("Authorization");

            if(authorization != null && authorization.startsWith("Bearer ")){
                return authorization.substring(authorization.indexOf(' ') + 1);
            }
        }
        return null;
    }
}