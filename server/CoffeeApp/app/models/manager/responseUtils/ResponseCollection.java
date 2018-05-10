package models.manager.responseUtils;

import io.ebean.text.PathProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import controllers.utils.ListPagerCollection;
import play.libs.Json;
import play.mvc.Result;

import javax.persistence.EntityNotFoundException;
import java.time.format.DateTimeParseException;
import static play.mvc.Http.Status.CONFLICT;
import static play.mvc.Results.*;


/**
 * Created by darwin on 25/08/17.
 */
public class ResponseCollection {
    public static ObjectNode buildExtendResponse(String message, JsonNode result){
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

    public static ObjectNode buildExtendResponse(String message){
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

    public static Result foundEntity(ListPagerCollection listPager){
        return ok(buildExtendResponse(
                "Successful search",
                Json.toJson(listPager.entities),
                Json.toJson(listPager.pager)
        ));
    }

    public static Result foundEntity(ListPagerCollection listPager, PathProperties pathProperties){
        return ok(buildExtendResponse(
                "Successful search",
                pathProperties != null && !pathProperties.getPathProps().isEmpty() ? JsonUtils.toJson(listPager.entities, pathProperties) :  Json.toJson(listPager.entities),
                Json.toJson(listPager.pager)
        ));
    }

    public static Result foundEntity(Object object, PathProperties pathProperties){
        return ok(buildExtendResponse(
                "Successful search",
                pathProperties != null ? JsonUtils.toJson(object, pathProperties) :  Json.toJson(object)
        ));
    }

    public static Result updatedEntity(JsonNode result){
        return ok(buildExtendResponse("Successful updated", result));
    }

    public static Result deletedEntity(){
        return ok(buildExtendResponse("Successful deleted"));
        //return noContent();
    }

    //TODO accessGranted maybe return other http code
    public static Result accessDenied() {
        return ok(buildExtendResponse("ACCESS DENIED"));
    }

    public static Result accessGranted(){
        return ok(buildExtendResponse("ACCESS GRANTED"));
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

    public static Result requiredParameter(String parameter){
        return badRequest(buildExtendResponse("Missing parameter [" + parameter + "]"));
    }

    public static Result invalidParameter(String parameter){
        return badRequest(buildExtendResponse("Invalid parameter [" + parameter + "]"));
    }

    public static Result invalidParameter(JsonParseException e){
        return badRequest(buildExtendResponse("Invalid format: " + e.getMessage()));
    }

    public static Result invalidParameter(InvalidFormatException e){
        return badRequest(buildExtendResponse("Invalid format: " + e.getMessage()));
    }

    public static Result invalidParameter(DateTimeParseException e){
        return badRequest(buildExtendResponse("Invalid parse datetime: " + e.getMessage()));
    }

    public static Result invalidParameter(JsonMappingException e){
        return badRequest(buildExtendResponse("Error json mapping:" + e.getMessage()));
    }

    public static Result invalidParameter(MysqlDataTruncation e){
        return badRequest(buildExtendResponse(e.getMessage()));
    }

    /*
    * notFound 404
    */
    public static Result notFoundEntity(String entityType){
        return notFound(buildExtendResponse("Entity not found [" + entityType + "]"));
    }

    public static Result notFoundEntity(EntityNotFoundException e){
        return notFound(buildExtendResponse(e.getMessage()));
    }

    /*
    * conflict 409
    */
    public static Result uniqueViolation(String parameter){
        return status(CONFLICT, buildExtendResponse("No unique parameter [" + parameter + "]"));
    }

    public static Result constraintViolation(MySQLIntegrityConstraintViolationException e){
        return status(CONFLICT, buildExtendResponse("Violation constrain: " + e.getMessage()));
    }

    /*
    * internalServerError 500
    */
    public static Result internalServerErrorLF(){
        return internalServerError(buildExtendResponse("Oops!"));
    }
}
