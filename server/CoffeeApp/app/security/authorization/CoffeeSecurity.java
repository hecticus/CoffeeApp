package security.authorization;


import com.typesafe.config.Config;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import security.authentication.oauth2.BaseGrant;
import security.authentication.oauth2.Response;
import security.models.AuthUser;
import security.models.Permission;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


public class CoffeeSecurity  extends Action<CoffeeSecurity>{

    private static Boolean enabled;

    @Inject
    public BaseGrant baseGrant;



    //Aqui debo crear el nuevo rbac
    @Inject
        CoffeeSecurity(Config config){
        //enabled = config.getBoolean("play.rbac.enabled");
        enabled = true;
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
            if (enabled) {
                String permission =  ctx.args.get("ROUTE_CONTROLLER") + "." + ctx.args.get("ROUTE_ACTION_METHOD");
                System.out.println(permission);


                String accessToken = getTokenFromHeader(ctx);
                System.out.println(accessToken);
                if (accessToken == null)
                    return CompletableFuture.completedFuture(Response.invalidRequest());

                AuthUser authUser = baseGrant.verifyAccessToken(accessToken);
                if (authUser == null)
                    return CompletableFuture.completedFuture(Response.invalidToken());

                if (!Permission.hasPermmission(permission, authUser.getId())) {
                    return CompletableFuture.completedFuture(Response.insufficientScope());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return CompletableFuture.completedFuture(Response.internalServerErrorLF(e.getMessage()));
        }
        return delegate.call(ctx);
    }


    private static String getTokenFromHeader(Http.Context ctx) {
        try {
            String authorization = ctx.request().getHeaders().get("Authorization").get();
            if(!authorization.isEmpty()){
                String token[] = authorization.split(" ");
                if (token.length > 1 && token[0].equals("Bearer")) {
                    return token[1];
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTokenFromHeader(Http.Request request) {
        try {
            String authorization = request.getHeaders().get("Authorization").get();
            if(authorization != null){
                String token[] = authorization.split(" ");
                if (token.length > 1 && token[0].equals("Bearer")) {
                    return token[1];
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




}


