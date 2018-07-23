package controllers;

import controllers.utils.NsExceptionsUtils;
import controllers.utils.Response;
import multimedia.models.Resolution;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import java.util.List;

public class Resolutions extends Controller {

    @CoffeAppsecurity
    public Result findAll() {
        try {
            List<Resolution> resolutions = Resolution.findAll();

            return Response.foundEntity(Json.toJson(resolutions));
        }catch(Exception e){
            return NsExceptionsUtils.find(e);
        }
    }
}