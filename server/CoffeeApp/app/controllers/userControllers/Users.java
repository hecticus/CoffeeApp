package controllers.userControllers;


import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.Response;
import io.ebean.text.PathProperties;
import models.userModels.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authentication.ResponseAuth;
import security.authorization.HSecurity;
import security.models.AuthUser;

/**
 * Created by nisa on 10/3/16.
 */
public class Users extends Controller {

    @HSecurity
    public Result findById(Long id) {
        try {
            User user = User.findById(id);

            return Response.foundEntity(Json.toJson(user));
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }

    @HSecurity
    public Result findByEmail(String email) {
        try {
            User user = User.findByEmail(email);

            return Response.foundEntity(Json.toJson(user));
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }

    @HSecurity
    public Result findByAuthUser(Long authUserId, String collection) {
        try {
            if(collection != null && !collection.isEmpty()) {
                PathProperties pathProperties = PathProperties.parse(collection);
                User user = User.findByAuthUserId(authUserId, pathProperties);
                return Response.foundEntity(user, pathProperties);
            }
            User user = User.findByAuthUserId(authUserId);
            return Response.foundEntity(Json.toJson(user));
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }

    @HSecurity
    public Result updatePassword(Long id) {
        try {
            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();

            if(!request.has("password"))
                Response.requiredParameter("password");

            AuthUser authUser = AuthUser.findById(id);
            if(authUser == null)
                return Response.notFoundEntity("authUser");

            JsonNode password = request.get("password");
            authUser.setPassword(password == null ? null : password.textValue()); //TODO encriptar
            authUser.update();

            return Response.updatedEntity(Json.toJson(authUser));
        }catch(Exception e){
            return NsExceptionsUtils.update(e);
        }
    }

    @HSecurity
    public Result resetPassword(Long id) {
        try {
            Long authUserId = Long.valueOf(ctx().args.get("authUserId").toString());
            if(authUserId == null)
                ResponseAuth.insufficientScope();

            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();

            if(!(request.has("currentPassword") && request.has("newPassword")))
                Response.requiredParameter("currentPassword/newPassword");

            User user = User.findByIdAuthUserId(id, authUserId);
            AuthUser authUser = user.getAuthUser();

            if(!authUser.getPassword().equals(request.get("currentPassword").asText()))
                return Response.invalidParameter("currentPassword no match");

            authUser.setPassword(request.get("newPassword").asText()); //TODO encriptar
            authUser.update();
            authUser.setPassword(null);

            return Response.updatedEntity(Json.toJson(user));
        }catch(Exception e){
            return NsExceptionsUtils.update(e);
        }
    }
}
