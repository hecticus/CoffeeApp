package models.manager.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.hecticus.auth.AuthJWT;
import com.hecticus.utils.basic.Notify;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import models.dao.UserDao;
import models.dao.impl.UserDaoImpl;
import models.domain.User;
import models.manager.RoleManager;
import models.manager.UserManager;
import models.manager.responseUtils.ExceptionsUtils;
import models.manager.responseUtils.JsonUtils;
import models.manager.responseUtils.Response;
import play.Configuration;
import play.libs.Json;
import play.libs.mailer.MailerClient;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.HashMap;

import static play.mvc.Controller.request;
import static play.mvc.Results.*;

/**
 * Created by yenny on 10/3/16.
 */
public class UserManagerImpl implements UserManager {

    private static UserDao userDao = new UserDaoImpl();
    private static RoleManager roleDao = new RoleManagerImpl();

    private String secret_key = "";
    private String app_server = "http://localhost:4200/#/"; /*ojo con esto drocha colocal el de coffe*/

    // private String app_server = "https://dev.coffee.hecticus.com/#/"; /* para pruebas en dev*/

    private MailerClient mailerClient;

    public UserManagerImpl() {

    }

    @Inject
    public UserManagerImpl(Configuration configuration) {
        this.secret_key = configuration.getString("play.crypto.secret");
    }

    @Override
    public Result findByEmail(String email) {

        try {
            User user = userDao.findByEmail(email);
            return Response.foundEntity(Json.toJson(user));
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

    @Override
    public Result uploadPhoto(JsonNode request) {
        try {
            JsonNode jracksPhoto = userDao.uploadPhoto(request);
            return Response.foundEntity(Json.toJson(jracksPhoto));
        } catch (Exception e) {
            return ExceptionsUtils.update(e);
        }
    }


    @Override
    public Result login(Configuration config) {
        try {

            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();

            JsonNode user = request.get("email");
            if (user == null)
                return Response.requiredParameter("email");

            JsonNode pass = request.get("password");
            if (pass == null)
                return Response.requiredParameter("password");

            User USER = userDao.findByEmail(user.textValue());

            if (USER != null) {
                if (user.textValue().equals(USER.getEmail()) && pass.textValue().equals(USER.getPassword())) {

                    this.secret_key = config.getString("play.crypto.secret");

                    HashMap<String, String> hmap = new HashMap<String, String>();
                    hmap.put("email", user.textValue());

                    AuthJWT aut = new AuthJWT(this.secret_key);
                    String jwt = aut.createJWT(USER.getIdUser().toString(), app_server, "login/", -1, hmap);


                    USER.setToken(jwt);
                    userDao.update(USER);

                    return ok(Response.buildExtendResponse("", JsonUtils.toJson(USER, User.class)))
                            .withHeader("Authorization",jwt)
                            .withHeader("Access-Control-Allow-Methods", "PUT, GET, POST, DELETE, OPTIONS")
                            .withHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, Authorization, X-Custom-header")
                            .withHeader("Access-Control-Expose-Headers","Authorization")
                            .withHeader("Access-Control-Allow-Origin", "*");

                } else {
                    return badRequest(Response.buildExtendResponse("Check your username/password", null));
                }
            } else return badRequest(Response.buildExtendResponse("Check your username/password",null));


        } catch (Exception e) {

            return Response.internalServerErrorLF();
        }
    }


    @Override
    public Result authorize(Configuration config, String path) {
        try {
            this.secret_key = config.getString("play.crypto.secret");

            String auth_path = path;

            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();

            JsonNode user = request.get("email");
            if (user == null)
                return Response.requiredParameter("email");

            JsonNode pass = request.get("password");
            if (pass == null)
                return Response.requiredParameter("passsword");

            User USER = userDao.findByEmail(user.textValue());

            if (USER != null)
                if (user.textValue().equals(USER.getEmail()) && pass.textValue().equals(USER.getPassword())) {

                    HashMap<String, String> hmap = new HashMap<String, String>();
                    hmap.put("email", user.textValue());

                    AuthJWT aut = new AuthJWT(this.secret_key);
                    String jwt = aut.createJWT(USER.getIdUser().toString(), app_server, auth_path, 80000, hmap);

                    return ok()
                            .withHeader("Authorization", jwt)
                            .withHeader("Access-Control-Allow-Origin", "*")
                            .withHeader("Access-Control-Allow-Methods", "PUT, GET, POST, DELETE, OPTIONS")
                            .withHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, Authorization, X-Custom-header")
                            .withHeader("Access-Control-Expose-Headers","Authorization");

                } else
                    return unauthorized();
            else {
                return badRequest(Response.buildExtendResponse("User",null));
            }


        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }


    }


    @Override
    public Result verify(Configuration config) {
        try {

            this.secret_key = config.getString("play.crypto.secret");

            String token = request().getHeader("Authorization");

            if(token != null){

                System.out.println("Verify Authorization "+token);
                AuthJWT aut = new AuthJWT(this.secret_key);
                aut.verifyJWT(token);
                    ArrayList<String> msap = new ArrayList<>();
                    msap.add("email");

                    //Si quiero obtener los claims:
                    /*
                    Map<String,String> mmap = aut.getClaims(token,msap);

                    String suid = mmap.get("Id");
                    String email = mmap.get("Email");

                    System.out.println("Id "+suid);
                    System.out.println("Email "+email);
                    */

                    return ok(Response.buildExtendResponse("",null))
                            .withHeader("Authorization", token)
                            .withHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, Authorization, X-Custom-header")
                            .withHeader("Access-Control-Expose-Headers","Authorization")
                            .withHeader("Access-Control-Allow-Origin", "*")
                            .withHeader("Access-Control-Allow-Methods", "PUT, GET, POST, DELETE, OPTIONS");

            }else{
                return badRequest(Response.buildExtendResponse("Token is null",null));

            }



        } catch (ExpiredJwtException ex){
            return badRequest(Response.buildExtendResponse("",null));

        }catch (SignatureException ex){
            //Aca cae si falla el verify
            //Logica que quiero si falla

            return badRequest(Response.buildExtendResponse("",null));

        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }


    }


    @Override
    public Result startResetPassword(String to_email, MailerClient mailerClient, Configuration config) {
        try {

            this.secret_key = config.getString("play.crypto.secret");

            String auth_path = "reset";
            String href = "http://"+app_server+"/"+auth_path;
            //String href = "http://localhost:4200/"+auth_path;

            User USER = userDao.findByEmail(to_email);

            if (USER != null) {

               //Seteamos el correo como CLAIM
               HashMap<String, String> hmap = new HashMap<String, String>();
               hmap.put("id", USER.getIdUser().toString());
               hmap.put("email", to_email);
               hmap.put("scope", "resetPassword");

               AuthJWT aut = new AuthJWT(this.secret_key);
               String jwt = aut.createJWT(USER.getIdUser().toString(), app_server, auth_path, -1, hmap);

               Notify notify = new Notify(mailerClient);

               notify.sendEmail("<a href='"+href+"?token="+jwt+"'>Password Recovery Link</a>", to_email, "german.mantilla@hecticus.com", "Password Recovery");

               return ok(Response.buildExtendResponse("Sent",null))
                            .withHeader("Authorization", jwt)
                            .withHeader("Access-Control-Allow-Headers","Origin, Content-Type, Authorization, X-Custom-header")
                            .withHeader("Access-Control-Expose-Headers","Authorization")
                            .withHeader("Access-Control-Allow-Origin", "*")
                            .withHeader("Access-Control-Allow-Methods", "PUT, GET, POST, DELETE, OPTIONS");


            } else {
                return badRequest(Response.buildExtendResponse("Not sent",null));
            }

        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

    @Override
    public Result handleStartResetPassword() {
        try {

            AuthJWT aut = new AuthJWT(this.secret_key);
            String token = request().getHeader("Authorization");
            System.out.println("SK ->"+this.secret_key);
            System.out.println("Authorization ->"+token);
            aut.verifyJWT(token);

            //Verifico el claim: Email
            aut.getClaim(request().getHeader("Authorization"),"email");

                    JsonNode request = request().body().asJson();
                    if (request == null)
                        return Response.requiredJson();

                    JsonNode user = request.get("email");
                    if (user == null)
                        return Response.requiredParameter("email");

                    JsonNode pass = request.get("password");
                    if (pass == null)
                        return Response.requiredParameter("passsword");

                    User USER = userDao.findByEmail(user.textValue());

                    if (USER != null) {
                        //Cambio el password y actualizo su status
                        try
                        {
                            USER.setPassword(pass.textValue());
                            userDao.update(USER);

                            return Response.updatedEntity(user);

                        }catch(Exception e){
                            return ExceptionsUtils.update(e);
                        }

                    } else {
                        return badRequest(Response.buildExtendResponse("Error on update"));
                    }



        } catch (SignatureException e) {
            e.printStackTrace();
            return badRequest(Response.buildExtendResponse("Token invalid",null));
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }


    }



}