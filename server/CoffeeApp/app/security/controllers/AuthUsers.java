package security.controllers;

import io.ebean.text.PathProperties;
import io.ebean.PagedList;
import controllers.responseUtils.ExceptionsUtils;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.PropertiesCollections;
import controllers.utils.Response;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.models.AuthUser;
import security.models.Group;
import security.models.Role;

import javax.inject.Inject;
import java.util.List;

public class AuthUsers extends Controller {

    @Inject
    private FormFactory formFactory;
    private static PropertiesCollections propertiesCollection = new PropertiesCollections();

    public AuthUsers(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }


    public Result create() {
        try {
            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();

            Form<AuthUser> form = formFactory.form(AuthUser.class).bind(request);
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());

            AuthUser authUsers = form.get();
            authUsers.insert();
            AuthUser userr = AuthUser.findById(Long.valueOf(7));
            return Response.createdEntity(Json.toJson(userr));
//            return Response.createdEntity(Json.toJson(authUsers));
        } catch (Exception e) {
            return NsExceptionsUtils.create(e);
        }
    }

    public Result update(Long id ) {
        try {
            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();

            Form<AuthUser> form = formFactory.form(AuthUser.class).bind(request);
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());

            AuthUser authUsers = form.get();
            authUsers.setId(id);
            authUsers.update();

            return Response.createdEntity(Json.toJson(authUsers));
        } catch (Exception e) {
            return NsExceptionsUtils.create(e);
        }
    }

    public Result findById(Long id) {
        try {
            AuthUser authUser = AuthUser.findById(id);

            return Response.foundEntity(Json.toJson(authUser));
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }

//    public Result findAll(String name, Integer pageindex, Integer pagesize,String sort, String username, String email,
//                          String password, Boolean archived, String collection, List<Role> roles, List<Group> groups ) {
//        try {
//            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
//            PagedList pagedList = AuthUser.findAll(name, pageindex, pagesize, sort, pathProperties, all, idFarm.intValue());//, statusLot);
//
//            return Response.foundEntity(pagedList, pathProperties);
//        }catch(Exception e){
//            return ExceptionsUtils.find(e);
//        }
}


