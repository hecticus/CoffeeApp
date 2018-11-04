package controllers.userControllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.PagedList;
import com.avaje.ebean.text.PathProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.data.Form;
import play.data.FormFactory;
import security.authorization.HSecurity;
import models.userModels.UserGeneric;
import controllers.parsers.queryStringBindable.Pager;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.Response;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.models.AuthUser;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nisa on 10/3/16.
 */
public class UserGenerics extends Controller {

    @Inject
    private FormFactory formFactory;

    @HSecurity
    public Result create() {
        try {
            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();

            Form<UserGeneric> form = formFactory.form(UserGeneric.class).bind(request);
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());

            //UserGeneric userGeneric = form.get();
            UserGeneric userGeneric = Json.fromJson(request, UserGeneric.class);
            userGeneric.insert();
            userGeneric.getAuthUser().setPassword(null);

            return Response.createdEntity(Json.toJson(userGeneric));
        }catch(Exception e){
            return NsExceptionsUtils.create(e);
        }
    }

    @HSecurity
    public Result update(Long id) {
        try {
            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();

            Form<UserGeneric> form = formFactory.form(UserGeneric.class).bind(request);
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());

            UserGeneric userGeneric = Json.fromJson(request, UserGeneric.class);
            userGeneric.setId(id);
            userGeneric.update();
            userGeneric.getAuthUser().setPassword(null);

            return Response.updatedEntity(Json.toJson(userGeneric));
        }catch(Exception e){
            return NsExceptionsUtils.update(e);
        }
    }


    @HSecurity
    public Result delete(Long id) {
        Ebean.beginTransaction();
        try {
            UserGeneric entity = UserGeneric.findById(id);
            if(entity != null) {
                entity.delete();
                Ebean.delete(AuthUser.class, entity.getId());
                Ebean.commitTransaction();
            }

            return Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        } finally {
            Ebean.endTransaction();
        }
    }

    @HSecurity
    public Result deletes() {
        try {
            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();
            if(!request.isArray())
                return Response.invalidParameter("must be array");

            List<Long> ids = new ArrayList<>();
            JsonNode arrNode = new ObjectMapper().readTree(request.toString());
            for (int i = 0; i < arrNode.size(); ++i) {
                ids.add(arrNode.get(i).get("id").asLong());
            }
            Ebean.deleteAll(UserGeneric.class, ids);

            return Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

    @HSecurity
    public Result findById(Long id) {
        try {
            UserGeneric userGeneric = UserGeneric.findById(id);

            return Response.foundEntity(Json.toJson(userGeneric));
        }catch(Exception e){
            return NsExceptionsUtils.find(e);
        }
    }

    @HSecurity
    public Result findAll(String email, String firstName, String lastName, Long roleId, Pager pager, String sort, String collection) {
        try {
            if(collection != null && !collection.isEmpty()) {
                PathProperties pathProperties = PathProperties.parse(collection);
                PagedList pagedList = UserGeneric.findAll(email, firstName, lastName, roleId, pager.index, pager.size, sort, pathProperties);
                return Response.foundEntity(pagedList, pathProperties);
            }
            PagedList pagedList = UserGeneric.findAll(email, firstName, lastName, roleId, pager.index, pager.size, sort, null);
            return Response.foundEntity(pagedList);
        }catch(Exception e){
            return NsExceptionsUtils.find(e);
        }
    }
}
