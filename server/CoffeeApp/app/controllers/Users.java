package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.Response;
import models.domain.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.models.AuthUser;

/**
 * Created by yenny on 10/3/16.
 */
public class Users extends Controller {



    public Result findById(Long id) {
        try {
            User user = User.findById(id);

            return Response.foundEntity(Json.toJson(user));
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }

/*  //Se puede ampliar agregando un email de recuperacion
    public Result findByEmail(String email) {
        try {
            User user = User.findByEmail(email);

            return Response.foundEntity(Json.toJson(user));
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }*/

    public Result updatePassword(Long id) {
        try {
            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();

            AuthUser authUser = AuthUser.findById(id);
            if(authUser == null)
                return Response.notFoundEntity("id[" + id + "]");

            JsonNode password = request.get("password");
            authUser.setPassword(password == null ? null : password.textValue()); //TODO encriptar
            authUser.update();

            return Response.updatedEntity(Json.toJson(authUser));
        }catch(Exception e){
            return NsExceptionsUtils.update(e);
        }
    }
}
