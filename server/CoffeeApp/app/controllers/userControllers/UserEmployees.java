//
//package controllers.userControllers;
//
//import com.avaje.ebean.Ebean;
//import com.avaje.ebean.PagedList;
//import com.avaje.ebean.text.PathProperties;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import play.data.Form;
//import play.data.FormFactory;
//import security.authorization.HSecurity;
//import models.userModels.UserEmployee;
//import controllers.parsers.queryStringBindable.Pager;
//import controllers.utils.NsExceptionsUtils;
//import controllers.utils.JsonUtils;
//import controllers.utils.Response;
//import play.libs.Json;
//import play.mvc.Controller;
//import play.mvc.Result;
//import security.models.AuthGroup;
//import security.models.AuthUser;
//
//import javax.inject.Inject;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by yenny on 9/15/16.
// */
//public class UserEmployees extends Controller {
//
//    @Inject
//    private FormFactory formFactory;
//    @HSecurity
//	public Result create() {
//        try {
//            JsonNode request = request().body().asJson();
//            if (request == null)
//                return Response.requiredJson();
//
//            Form<UserEmployee> form = formFactory.form(UserEmployee.class).bind(request);
//            if (form.hasErrors())
//                return Response.invalidParameter(form.errorsAsJson());
//
//            //UserEmployee userEmployee = form.get();
//            UserEmployee userEmployee = Json.fromJson(request, UserEmployee.class);
//
//            AuthUser authUser = userEmployee.getAuthUser();
//            if(authUser != null){
//                AuthGroup authGroup = AuthGroup.findByName("employee");
//                if(authGroup != null) {
//                    authUser.setAuthGroup(authGroup);
//                }
//                userEmployee.setAuthUser(authUser);
//            }
//
//            userEmployee.insert();
//            userEmployee.getAuthUser().setPassword(null);
//
//            return Response.createdEntity(Json.toJson(userEmployee));
//        }catch(Exception e){
//            return NsExceptionsUtils.create(e);
//        }
//    }
//
//    @HSecurity
//	public Result update(Long id) {
//        try {
//            JsonNode request = request().body().asJson();
//            if (request == null)
//                return Response.requiredJson();
//
//            Form<UserEmployee> form = formFactory.form(UserEmployee.class).bind(request);
//            if (form.hasErrors())
//                return Response.invalidParameter(form.errorsAsJson());
//
//            UserEmployee userEmployee = Json.fromJson(request, UserEmployee.class);
//            userEmployee.setId(id);
//            userEmployee.update();
//            userEmployee.getAuthUser().setPassword(null);
//
//            return Response.updatedEntity(Json.toJson(userEmployee));
//        }catch(Exception e){
//            return NsExceptionsUtils.update(e);
//        }
//    }
//
//    @HSecurity
//	public Result delete(Long id) {
//        Ebean.beginTransaction();
//        try {
//            UserEmployee entity = UserEmployee.findById(id);
//            if(entity != null) {
//                entity.delete();
//                Ebean.delete(AuthUser.class, entity.getId());
//                Ebean.commitTransaction();
//            }
//
//            return Response.deletedEntity();
//        } catch (Exception e) {
//            return NsExceptionsUtils.delete(e);
//        } finally {
//            Ebean.endTransaction();
//        }
//    }
//
//    @HSecurity
//	public Result deletes() {
//        try{
//            JsonNode request = request().body().asJson();
//            if (request == null)
//                return Response.requiredJson();
//            if(!request.isArray())
//                return Response.invalidParameter("must be array");
//
//            List<Long> ids = new ArrayList<>();
//            JsonNode arrNode = new ObjectMapper().readTree(request.toString());
//            for (int i = 0; i < arrNode.size(); ++i) {
//                ids.add(arrNode.get(i).get("id").asLong());
//            }
//            Ebean.deleteAll(UserEmployee.class, ids);
//
//            return Response.deletedEntity();
//        } catch (Exception e) {
//            return NsExceptionsUtils.delete(e);
//        }
//    }
//
//    @HSecurity
//	public Result findById(Long id) {
//        try {
//            UserEmployee userEmployee = UserEmployee.findById(id);
//
//            return Response.foundEntity(Json.toJson(userEmployee));
//        }catch(Exception e){
//            return NsExceptionsUtils.find(e);
//        }
//    }
//
//    @HSecurity
//	public Result findAll(String email, String firstName, String lastName, Pager pager, String sort, String collection) {
//        try {
//            if(collection != null && !collection.isEmpty()) {
//                PathProperties pathProperties = PathProperties.parse(collection);
//                PagedList pagedList = UserEmployee.findAll(email, firstName, lastName, pager.index, pager.size, sort, pathProperties);
//                return Response.foundEntity(pagedList, pathProperties);
//            }
//            PagedList pagedList = UserEmployee.findAll(email, firstName, lastName, pager.index, pager.size, sort, null);
//            return Response.foundEntity(pagedList);
//        }catch(Exception e){
//            return NsExceptionsUtils.find(e);
//        }
//    }
//}
