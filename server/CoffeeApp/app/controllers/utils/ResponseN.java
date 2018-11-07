package controllers.utils;

import io.ebean.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.ebean.text.PathProperties;
import play.libs.Json;
import play.mvc.Result;

import java.io.IOException;

import static play.mvc.Results.*;

/**
 * Created by yenny on 9/7/16.
 */
public class ResponseN{

    private static ObjectNode buildExtendResponse(String message, JsonNode result){
        ObjectNode response = Json.newObject();
        response.put("message", message);
        response.set("result", result);
        return response;
    }

    private static ObjectNode buildExtendResponse(String message, JsonNode result, JsonNode pager){
        ObjectNode response = Json.newObject();
        response.put("message", message);
        response.set("result", result);
        response.set("pager", pager);
        return response;
    }

    private static ObjectNode buildExtendResponse(String message){
        ObjectNode response = Json.newObject();
        response.put("message", message);
        return response;
    }

    /*
    * ok 200
    */
    public static Result foundEntity(JsonNode result){
        return ok(buildExtendResponse("Successful search", result));
    }

    public static Result foundEntity(Object object){
        return ok(buildExtendResponse("Successful search", Json.toJson(object)));
    }

    public static Result foundEntity(PagedList pagedList){
        ObjectNode pager = Json.newObject();
        pager.put("totalEntitiesPerPage", pagedList.getList().size());
        pager.put("totalEntities", pagedList.getTotalPageCount());
        pager.put("pageIndex", pagedList.getPageIndex());
        pager.put("pageSize", pagedList.getPageSize());
        pager.put("pages", pagedList.getTotalPageCount());
        pager.put("startIndex", 0);
        pager.put("endIndex", pagedList.getTotalPageCount() - 1);

        JsonNode jsonEntities = Json.toJson(pagedList.getList());

        return ok(buildExtendResponse("Successful search", jsonEntities, pager));
    }

    public static Result foundEntity(PagedList pagedList, PathProperties pathProperties){
        ObjectNode pager = Json.newObject();
        pager.put("totalEntitiesPerPage", pagedList.getList().size());
        pager.put("totalEntities", pagedList.getTotalPageCount());
        pager.put("pageIndex", pagedList.getPageIndex());
        pager.put("pageSize", pagedList.getPageSize());
        pager.put("pages", pagedList.getTotalPageCount());
        pager.put("startIndex", 0);
        pager.put("endIndex", pagedList.getTotalPageCount() - 1);

        try {
            JsonNode jsonEntities = new ObjectMapper().readTree(Ebean.json().toJson(pagedList.getList(), pathProperties));
            jsonEntities = Json.toJson(jsonEntities);
            return ok(buildExtendResponse("Successful search", jsonEntities, pager));
        } catch (IOException e) {
            e.printStackTrace();
            return internalServerErrorLF();
        }
    }

    public static Result foundEntity(Object object, PathProperties pathProperties){
        JsonNode jsonEntity;

        if (pathProperties != null) {
            try {
                jsonEntity = new ObjectMapper().readTree(Ebean.json().toJson(object, pathProperties));
            } catch (IOException e) {
                e.printStackTrace();
                return internalServerErrorLF();
            }
        }else{
            jsonEntity = Json.toJson(object);
        }
        return ok(buildExtendResponse("Successful search", jsonEntity));
    }

    public static Result updatedEntity(JsonNode result){
        return ok(buildExtendResponse("Successful updated", result));
    }

    public static Result deletedEntity(){
        return ok(buildExtendResponse("Successful deleted"));
        //return noContent();
    }

    /*
    * created 201
    */
    public static Result createdEntity(JsonNode result){
        return created(buildExtendResponse("Successful created", result));
    }
    public static Result createdEntity(){
        return created(buildExtendResponse("Successful created"));
    }

    /*
    * badRequest 400
    */
    public static Result requiredJson(){
        return badRequest(buildExtendResponse("Expecting Json data"));
    }

    public static Result requiredParameter(String message){
        return badRequest(buildExtendResponse("Missing parameter: " + message));
    }

    public static Result invalidParameter(String message){
        return badRequest(buildExtendResponse("Invalid parameter: " + message));
    }

    public static Result invalidParameter(JsonNode errors){
        ObjectNode response = Json.newObject();
        response.put("message", "Invalid parameter");
        response.set("errors", (ObjectNode) errors);
        return badRequest(response);
    }

    /*
     * unauthorized 401
     */
    public static Result badCredentials(){
        return unauthorized(buildExtendResponse("Authorization has been refused for those credentials"));
    }
    public static Result badCredentials(String message){
        return unauthorized(buildExtendResponse("Authorization has been refused for those credentials: " + message));
    }
    public static Result badCredentials(String message, JsonNode result){
        return unauthorized(buildExtendResponse("Authorization has been refused for those credentials: " + message, result));
    }

    /*
    * notFound 404
    */
    public static Result notFoundEntity(String message){
        return notFound(buildExtendResponse("Not found entity: " + message));
    }

    /*
    * conflict 409
    */
    public static Result uniqueViolation(String message){
        return status(409, buildExtendResponse("No unique parameter " + message));
    }

    public static Result constraintViolation(String message){
        return status(409, buildExtendResponse("Constrain violation: " + message));
    }

    /*
    * internalServerError 500
    */
    public static Result internalServerErrorLF(){
        return internalServerError(buildExtendResponse("Oops!"));
    }

    public static Result internalServerErrorLF(String message){
        return internalServerError(buildExtendResponse("Oops!: " + message));
    }
}