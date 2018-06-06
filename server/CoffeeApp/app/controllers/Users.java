package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.PropertiesCollection;
import controllers.utils.Response;
import models.User;

import controllers.responseUtils.ExceptionsUtils;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;
import security.models.AuthUser;

import javax.inject.Inject;

/**
 * Created by sm21 01/09
 */
public class Users extends Controller {


    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public Users(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }


    public Result create() {
        try {
            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();

            JsonNode authUserNode = request.findValue("authUser");
            Form<AuthUser> formAuth = formFactory.form(AuthUser.class).bind(authUserNode);
            if (formAuth.hasErrors())
                return Response.invalidParameter(formAuth.errorsAsJson());

            AuthUser authUsers = Json.fromJson(authUserNode, AuthUser.class);
            authUsers.insert();

            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(request.toString());

            node.set("authUser",  Json.toJson(authUsers));

            Form<User> form = formFactory.form(User.class).bind(node);
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());
            User user = Json.fromJson(node, User.class);
            user.insert();
            return Response.createdEntity(Json.toJson(user));
        } catch (Exception e) {
            return NsExceptionsUtils.create(e);
        }
    }

    public Result update(Long idUser) {
        try {
            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();

            Form<User> form = formFactory.form(User.class).bind(request);
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());
            User user = Json.fromJson(request, User.class);
            user.setId(idUser);
            user.update();


            return Response.createdEntity(Json.toJson(user));
        } catch (Exception e) {
            return NsExceptionsUtils.create(e);
        }
    }

    public Result delete(Long id) {
        try {
            User user = User.findById(id);
//            user.setStatusDelete(1);
            user.getAuthUser().setDeleted(true);
            user.getAuthUser().update();
            user.update();
            return Response.foundEntity(Json.toJson(user));
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }

    public Result findById(Long id) {
        try {
            User user = User.findById(id);
            return Response.foundEntity(Json.toJson(user));
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }


    @CoffeAppsecurity
    public Result uploadPhoto(JsonNode request) {
        try {
            JsonNode jracksPhoto = User.uploadPhoto(request);
            return controllers.responseUtils.Response.foundEntity(Json.toJson(jracksPhoto));
        } catch (Exception e) {
            return ExceptionsUtils.update(e);
        }
    }





}
