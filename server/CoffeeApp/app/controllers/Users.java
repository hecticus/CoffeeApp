/*
package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.responseUtils.ResponseCollection;
import controllers.utils.ListPagerCollection;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.PropertiesCollection;
import controllers.utils.Response;
import io.ebean.Ebean;
import io.ebean.annotation.Transactional;
import io.ebean.text.PathProperties;
import models.User;

import controllers.responseUtils.ExceptionsUtils;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authentication.updatePassword.UpdatePasswordManager;
import security.authorization.HSecurity;
import security.models.AuthUser;

import javax.inject.Inject;

*/
/**
 * Created by sm21 01/09
 *//*

public class Users extends Controller {


    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public Users(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

    @Transactional
    @HSecurity
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
            authUsers.save();

            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(request.toString());
            node.set("authUser",  Json.toJson(authUsers));

            Form<User> form = formFactory.form(User.class).bind(node);
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());
            User user = Json.fromJson(node, User.class);
            user.save();
            return Response.createdEntity(Json.toJson(user));
        } catch (Exception e) {
            return NsExceptionsUtils.create(e);
        }
    }

    @HSecurity
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
    public Result findByAuthUser(Long authUserId) {
        try {
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

    @HSecurity
    public Result delete(Long id) {
        try {
            Ebean.delete(User.findById(id).getAuthUser());
            Ebean.delete(User.findById(id));
            return Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }

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
    public Result findAll(Integer index, Integer size, String collection,
                          String sort, String name, String firstName, String lastName, boolean deleted){
        try {
            ListPagerCollection listPager = User.findAll(index, size, propertiesCollection.getPathProperties(collection), sort, name, firstName, lastName, deleted);
            return ResponseCollection.foundEntity(listPager, propertiesCollection.getPathProperties(collection));
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }



    public Result changePassword() {
        try {
            Boolean change = true;
            JsonNode request = request().body().asJson();
            if (request == null)
                return security.authentication.updatePassword.Response.requiredJson();

            JsonNode email = request.get("email");
            if (email ==  null)
                return controllers.responseUtils.Response.requiredParameter("email");

            JsonNode password = request.get("password");
            if (email ==  null)
                return controllers.responseUtils.Response.requiredParameter("email");

            AuthUser authUser = AuthUser.findByEmail(email.asText());
            authUser.setPassword(password.asText());
            authUser.update();

            return controllers.responseUtils.Response.foundEntity(Json.toJson(change));
        } catch (Exception e) {
            return ExceptionsUtils.update(e);
        }
    }



}
*/
