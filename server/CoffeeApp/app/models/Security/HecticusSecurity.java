package models.Security;

import models.dao.RoleDao;
import models.dao.SecurityRouteDao;
import models.dao.SecurityTagDao;
import models.dao.UserDao;
import models.dao.impl.RoleDaoImpl;
import models.dao.impl.SecurityRouteDaoImpl;
import models.dao.impl.SecurityTagDaoImpl;
import models.dao.impl.UserDaoImpl;
import models.domain.Config;
import models.manager.responseUtils.Response;
import play.Configuration;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class HecticusSecurity extends Action<HSecurity> {

    public static UserDao userDao = new UserDaoImpl();
    public static RoleDao roleDao = new RoleDaoImpl();
    private static SecurityRouteDao securityRouteDao = new SecurityRouteDaoImpl();
    private static SecurityTagDao securityTagDao = new SecurityTagDaoImpl();

    @Inject
    private Configuration appConfiguration;

    public CompletionStage<Result> call(Http.Context ctx) {
        if (Config.getString("SecurityEnabled").equalsIgnoreCase("disabled"))
            return delegate.call(ctx);
        //Zona para fillear

        String route = configuration.value().split("@")[0];
        String token = getTokenFromHeader(ctx);
        try {
            /*if (token != null) {
                User user = userDao.findByToken(token);
                if (user != null) {
                    SecurityRoute sc = securityRouteDao.CheckAndInsert(route, 0);
                    if (configuration.value().split("@").length > 1) {
                        String[] separator = configuration.value().split("@")[1].split(",");
                        List<SecurityTag> sclist = new ArrayList<SecurityTag>();
                        for (int x = 0; x < separator.length; x++) {
                            SecurityTag tmp = securityTagDao.CheckAndInsert(separator[x]);
                            sclist.add(tmp);
                        }
                        sc.setSecurityTag(sclist);
                        sc.update();
                    }
                    boolean canAccess = roleDao.AccessResource(user, route);
                    if (canAccess) {
                        ctx.args.putIfAbsent("CurrentUser", user);
                        return delegate.call(ctx);
                    }
                }
            }*/
        } catch (java.lang.NullPointerException exception) {
            return CompletableFuture.completedFuture(Response.accessDenied());
        }
        //return CompletableFuture.completedFuture(Response.accessDenied());
        return delegate.call(ctx);
    }


  /* private String getTokenFromHeader(Http.Context ctx) {

       System.out.printf("pase ****************        \\n   ");
       //System.out.printf(" imprimo http--- "+ ctx.toString());
       //System.out.printf("");
       //String authorization =  ctx.request().getQueryString("Authorization");
       //System.out.printf("pase ---------------- \n authorization "  +authorization +"  IMPRIMO CTX:  "+ ctx.toString() );
       //System.out.printf("    -- au ---   ");
       String authTokenHeaderValues = ctx.request().getHeaders().get("Authorization").get();
       System.out.printf(" --> authToken     " + authTokenHeaderValues);

       *//*if (authTokenHeaderValues != null) {
           System.out.printf(" --> authToken     " + authTokenHeaderValues.toString() );
       }else {
           System.out.printf(" authToken   SOY NULL NOSE   " );

       }

       if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (authTokenHeaderValues[0] != null)) {
           String token = authTokenHeaderValues[0];

           AuthJWT aut = new AuthJWT(appConfiguration.getString("play.crypto.secret"));

           try {
               aut.verifyJWT(token);
               return token;

           }catch (SignatureException e){
               return null;
           }

       }*//*
       return null;
   }*/

    private static String getTokenFromHeader(Http.Context ctx) {
        try {
            String authorization = ctx.request().getHeaders().get("Authorization").get();
            System.out.println(" auto //////// "+authorization);
            if (!authorization.isEmpty()) {
                String token[] = authorization.split(" ");
                System.out.println(" t0 "+ token[0].toString() + " t1 "+ token[1].toString()+ " length "+ token.length);

                if (token.length > 1 && token[0].equals("Bearer")) {
                    System.out.println("aasss{}");
                    return token[1];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }
}