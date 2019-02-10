package security.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;



import controllers.utils.NsExceptionsUtils;
import controllers.utils.PropertiesCollection;
import controllers.utils.Response;
import io.ebean.Ebean;
import io.ebean.PagedList;
import io.ebean.text.PathProperties;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;
import security.models.Role;

import javax.inject.Inject;

public class Roles extends Controller {
    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public Roles(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }


    public Result create() {
        try {
            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();

            Form<Role> form = formFactory.form(Role.class).bind(request);
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());
            Role user = Json.fromJson(request, Role.class);
            user.insert();
            return Response.createdEntity(Json.toJson(user));
        } catch (Exception e) {
            return NsExceptionsUtils.create(e);
        }
    }

    public Result update(Long id) {
        try {
            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();

            Form<Role> form = formFactory.form(Role.class).bind(request);
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());
            Role user = Json.fromJson(request, Role.class);
            user.setId(id);
            user.update();
            return Response.createdEntity(Json.toJson(user));
        } catch (Exception e) {
            return NsExceptionsUtils.create(e);
        }
    }

    public Result delete(Long id) {
        try {
            Ebean.delete(Role.findById(id));
            return Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }

    public Result findById(Long id) {
        try {
            return Response.foundEntity(Json.toJson(Role.findById(id)));
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }

        @CoffeAppsecurity
    public Result findAll(Integer index, Integer size, String collection,
                          String sort, String name, boolean delete){
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            PagedList pagedList = Role.findAll(index, size, pathProperties, sort, name, delete);
            return Response.foundEntity(pagedList, pathProperties);
        }catch(Exception e){
            return NsExceptionsUtils.find(e);
        }
    }

}
