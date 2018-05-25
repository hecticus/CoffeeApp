package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.Response;
import models.User;
import controllers.responseUtils.ExceptionsUtils;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

/**
 * Created by yenny on 10/3/16.
 */
public class Users extends Controller {

    private static User userDao = new User();

    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            User user = User.findById(id);

            return Response.foundEntity(Json.toJson(user));
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }


    @CoffeAppsecurity
    public Result findByEmail(String email) {

        try {
            User user = userDao.findByEmail(email);
            return controllers.responseUtils.Response.foundEntity(Json.toJson(user));
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

    @CoffeAppsecurity
    public Result uploadPhoto(JsonNode request) {
        try {
            JsonNode jracksPhoto = userDao.uploadPhoto(request);
            return controllers.responseUtils.Response.foundEntity(Json.toJson(jracksPhoto));
        } catch (Exception e) {
            return ExceptionsUtils.update(e);
        }
    }



}
