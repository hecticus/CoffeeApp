package security.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.responseUtils.ExceptionsUtils;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.PropertiesCollection;
import controllers.utils.Response;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;
import security.models.Group;

import javax.inject.Inject;

public class Groups  extends Controller {
    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public Groups(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }


    public Result create() {
        try {
            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();

            Form<Group> form = formFactory.form(Group.class).bind(request);
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());
            Group group = Json.fromJson(request, Group.class);
            group.insert();
            return Response.createdEntity(Json.toJson(group));
        } catch (Exception e) {
            return NsExceptionsUtils.create(e);
        }
    }

    public Result update(Long id) {
        try {
            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();

            Form<Group> form = formFactory.form(Group.class).bind(request);
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());
            Group group = Json.fromJson(request, Group.class);
            group.setId(id);
            group.update();
            return Response.createdEntity(Json.toJson(group));
        } catch (Exception e) {
            return NsExceptionsUtils.create(e);
        }
    }

//    public Result delete(Long id) {
//        try {
//            Group group = Group.findById(id);
//            group.delete();
//            return Response.foundEntity(Json.toJson(user));
//        } catch (Exception e) {
//            return NsExceptionsUtils.find(e);
//        }
//    }

    public Result findById(Long id) {
        try {
            return Response.foundEntity(Json.toJson(Group.findById(id)));
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }




}
