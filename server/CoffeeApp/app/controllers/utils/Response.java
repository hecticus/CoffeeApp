package controllers.utils;

//import com.avaje.ebean.Ebean;
//import com.avaje.ebean.text.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.ebean.Ebean;
import io.ebean.PagedList;
import io.ebean.text.PathProperties;
import play.libs.Json;
import play.mvc.Result;

import java.io.IOException;

import static play.mvc.Results.*;

/**
 * Created by yenny on 9/7/16.
 */
public class Response{

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

    public static Result foundEntity(PagedList pagedList, PathProperties pathProperties){
        ObjectNode pager = Json.newObject();
        //modificando
        pager.put("totalEntitiesPerPage", pagedList.getPageSize());
        pager.put("totalEntities", pagedList.getTotalCount());
        pager.put("pageIndex", pagedList.getPageIndex());
        pager.put("pageSize", pagedList.getPageSize());
        pager.put("pages", pagedList.getTotalPageCount());
        pager.put("startIndex", 0);
        pager.put("endIndex", pagedList.getTotalPageCount() - 1);
        JsonNode jsonEntities = null;

        if (pathProperties != null) {
            try {
                jsonEntities = new ObjectMapper().readTree(Ebean.json().toJson(pagedList.getList(), pathProperties));
                jsonEntities = Json.toJson(jsonEntities);
            } catch (IOException e) {
                e.printStackTrace();
                return internalServerErrorLF();
            }
        }else{
            jsonEntities = Json.toJson(pagedList.getList());
        }
        return ok(buildExtendResponse("Successful search", jsonEntities, pager));
    }

    public static Result foundEntity(Object object, PathProperties pathProperties){
        JsonNode jsonEntitie = null;

        if (pathProperties != null) {
            try {
                jsonEntitie = new ObjectMapper().readTree(Ebean.json().toJson(object, pathProperties));
            } catch (IOException e) {
                e.printStackTrace();
                return internalServerErrorLF();
            }
        }else{
            jsonEntitie = Json.toJson(object);
        }
        return ok(buildExtendResponse("Successful search", jsonEntitie));
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