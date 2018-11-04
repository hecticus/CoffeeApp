//package controllers.userControllers;
//
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import play.data.Form;
//import play.data.FormFactory;
//import security.authorization.HSecurity;
//import controllers.parsers.queryStringBindable.Pager;
//import controllers.utils.NsExceptionsUtils;
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
// * Created by yenny on 8/31/16.
// */
//public class UserClients extends Controller {
//
//    @Inject
//    private FormFactory formFactory;
//
//    @HSecurity
//    //@BodyParser.Of(UserClientBodyParser.class)
//	public Result create() {
//        try {
//            JsonNode request = request().body().asJson();
//            if (request == null)
//                return Response.requiredJson();
//
//            Form<UserClient> form = formFactory.form(UserClient.class).bind(request);
//            if (form.hasErrors())
//                return Response.invalidParameter(form.errorsAsJson());
//
//            //UserClient userClient = form.get();
//            UserClient userClient = Json.fromJson(request, UserClient.class);
//
//            AuthUser authUser = userClient.getAuthUser();
//            if(authUser != null){
//                AuthGroup authGroup = AuthGroup.findByName("client");
//                if(authGroup != null) {
//                    authUser.setAuthGroup(authGroup);
//                }
//                userClient.setAuthUser(authUser);
//            }
//
//            userClient.insert();
//            userClient.getAuthUser().setPassword(null);
//
//            return Response.createdEntity(Json.toJson(userClient));
//        }catch(Exception e){
//            return NsExceptionsUtils.create(e);
//        }
//    }
//
//    @HSecurity
//    public Result update(Long id) {
//        try {
//            JsonNode request = request().body().asJson();
//            if (request == null)
//                return Response.requiredJson();
//
//            Form<UserClient> form = formFactory.form(UserClient.class).bind(request);
//            if (form.hasErrors())
//                return Response.invalidParameter(form.errorsAsJson());
//
//            UserClient userClient = Json.fromJson(request, UserClient.class);
//            userClient.setId(id);
//            userClient.update();
//
//            return Response.updatedEntity(Json.toJson(userClient));
//        }catch(Exception e){
//            return NsExceptionsUtils.update(e);
//        }
//    }
//
//    @HSecurity
//	public Result delete(Long id) {
//        Ebean.beginTransaction();
//        try {
//            UserClient entity = UserClient.findById(id);
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
//        try {
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
//            Ebean.deleteAll(UserClient.class, ids);
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
//            UserClient userClient = UserClient.findById(id);
//
//            return Response.foundEntity(Json.toJson(userClient));
//        }catch(Exception e){
//            return NsExceptionsUtils.find(e);
//        }
//    }
//
//    @HSecurity
//	public Result findAll(String email, String firstName, String lastName, Long companyId, Pager pager, String sort, String collection) {
//        try {
//            if(collection != null && !collection.isEmpty()) {
//                PathProperties pathProperties = PathProperties.parse(collection);
//                PagedList pagedList = UserClient.findAll(email, firstName, lastName, companyId, pager.index, pager.size, sort, pathProperties);
//                return Response.foundEntity(pagedList, pathProperties);
//            }
//            PagedList pagedList = UserClient.findAll(email, firstName, lastName, companyId, pager.index, pager.size, sort, null);
//            return Response.foundEntity(pagedList);
//        }catch(Exception e){
//            return NsExceptionsUtils.find(e);
//        }
//    }
//}