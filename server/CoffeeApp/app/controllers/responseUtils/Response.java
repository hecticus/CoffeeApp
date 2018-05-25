package controllers.responseUtils;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import controllers.utils.ListPager;
import play.libs.Json;
import play.mvc.Result;

import javax.persistence.EntityNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static play.mvc.Http.Status.CONFLICT;
import static play.mvc.Http.Status.PRECONDITION_FAILED;
import static play.mvc.Results.*;

import org.modelmapper.ModelMapper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yenny on 9/7/16.
 */
public class Response {

    public static ObjectNode buildExtendResponse(String message, JsonNode result){
        ObjectNode response = Json.newObject();
        response.put("message", message);
        response.set("result", result);
        return response;
    }

    public static ObjectNode buildExtendResponse(String message, JsonNode result, JsonNode pager){
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

    public static String fieldNameToAttributeName(String message){
        StringBuilder name = new StringBuilder(message);
        int index = name.indexOf("_");
        while (index != -1){
            name.deleteCharAt(index);
            name.replace(index, index+1, String.valueOf(Character.toUpperCase(name.charAt(index))));
            index = name.indexOf("_");
        }
        return String.valueOf(name);
    }

    /*
    * ok 200
    */
    public static Result updatedEntity(JsonNode result){
        return ok(buildExtendResponse("Successful updated", result));
    }

    public static Result deletedEntity(){
        return ok(buildExtendResponse("Successful deleted"));
    }

    public static Result foundEntity(JsonNode result){
        return ok(buildExtendResponse("Successful search", result));
    }

    /*public static Result foundEntity(JsonNode result, ListPager.Pager pager){
        return ok(buildExtendResponse("Successful search", result, Json.toJson(pager)));
    }*/

    public static Result foundEntity(ListPager listPager){
        return ok(buildExtendResponse(
                "Successful search",
                Json.toJson(listPager.entities),
                Json.toJson(listPager.pager)));
    }

    public static Result foundEntity(ListPager listPager, Class typeDest){
        return ok(buildExtendResponse(
                "Successful search",
                JsonUtils.toJson(listPager.entities, typeDest),
                Json.toJson(listPager.pager)));
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

    public static Result accessDenied() {
        return badRequest(buildExtendResponse("ACCESS DENIED"));
    }

    public static Result accessGranted(){
        return ok(buildExtendResponse("ACCESS GRANTED"));
    }

    public static Result requiredParameter(String parameter){

        ObjectNode response = Json.newObject();
        response.put("message", "Missing parameter [" + parameter + "]");
        response.put("error", 412);
        return status(PRECONDITION_FAILED, response);
    }

    public static Result requiredParameter(String parameter,String description){

        ObjectNode response = Json.newObject();
        response.put("message", "Missing parameter [" + parameter + "]");
        response.put("error", 412);
        response.put("errorDescription","el parametro: "+description+", es obligatorio");
        return status(PRECONDITION_FAILED, response);
    }

    public static Result invalidParameter(String parameter){
        return badRequest(buildExtendResponse("Invalid parameter [" + parameter + "]"));
    }

    public static Result invalidParameter(InvalidFormatException e){
        final Pattern pattern = Pattern.compile("\\[\"(.+?)\"\\]");
        Matcher matcher = pattern.matcher(e.getPathReference());
        String attribute = "<unknow>";
        if (matcher.find())
            attribute = matcher.group(1);
        while(matcher.find())
            attribute += "." + matcher.group(1);
        return invalidParameter(attribute);
    }

    public static Result invalidParameter(JsonMappingException e){
        final Pattern pattern = Pattern.compile("\\[\"(.+?)\"\\]");
        Matcher matcher = pattern.matcher(e.getPathReference());
        String attribute = "<unknow>";
        if (matcher.find())
            attribute = matcher.group(1);
        while(matcher.find())
            attribute += "." + matcher.group(1);
        return invalidParameter(attribute);
    }

    public static Result invalidParameter(MysqlDataTruncation e){
        final Pattern pattern = Pattern.compile("column \'(.+?)\'");
        Matcher matcher = pattern.matcher(e.getMessage());
        if(matcher.find())
            return invalidParameter(fieldNameToAttributeName(matcher.group(1)));
        else
            return invalidParameter("<unknow>");
    }

    public static Result uniqueViolation(String parameter){
        return status(CONFLICT,buildExtendResponse("No unique parameter [" + parameter + "]"));
    }

    public static Result constraintViolation(MySQLIntegrityConstraintViolationException e){
        return status(CONFLICT, buildExtendResponse(e.getMessage()));
    }

    /*
    * notFound 404
    */
    public static Result notFoundEntity(String entityType){
        return notFound(buildExtendResponse("Entity not found [" + entityType + "]"));
    }

    public static Result notFoundEntity(EntityNotFoundException e){
        final Pattern pattern = Pattern.compile("type\\[class (.+?)\\]");
        Matcher matcher = pattern.matcher(e.getMessage());
        if(matcher.find()) {
            String entity = matcher.group(1);
            return notFoundEntity(entity.substring(entity.lastIndexOf(".") + 1));
        }else {
            return notFoundEntity("<unknow>");
        }
    }

    /*
    * internalServerError 500
    */
    public static Result internalServerErrorLF(){
        return internalServerError(buildExtendResponse("Oops!"));
    }

    public static Result messageExist(String msg){
        ObjectNode response = Json.newObject();
        response.put("message", "registered ["+msg+"]");
        response.put("error", 409);
        return status(CONFLICT, response);

    }

    public static Result messageExistDeleted(String msg){

        ObjectNode response = Json.newObject();
        response.put("message", "registered and no active ["+msg+"]");
        response.put("error", 409);
        return status(CONFLICT, response);
    }

    public static Result messageNotDeleted(String msg){

        ObjectNode response = Json.newObject();
        response.put("message", msg);
        response.put("error", 409);
        return status(CONFLICT, response);
    }

    public static Result messagebadRequest(String msg, int error){

        ObjectNode response = Json.newObject();
        response.put("message", msg);
        response.put("error", error);
        return badRequest (response);
    }


    public static Result message(String msg){
        return ok(buildExtendResponse(msg));
    }

    /*
    * get the root exception
    */
    public static Throwable getCause(Throwable e) {
        Throwable cause = null;
        Throwable result = e;
        while(null != (cause = result.getCause())  && (result != cause) )
            result = cause;
        return result;
    }

    public static JsonNode toJson(Object object, Class typeDest){
        if(object != null)
            object = new ModelMapper().map(object, typeDest);
        return Json.toJson(object);
    }

    public static JsonNode toJson(List<?> objectSources, Class typeDest){
        List<Object> objectDests = new ArrayList<>();
        for(Object objectSource: objectSources)
            objectDests.add(new ModelMapper().map(objectSource, typeDest));
        return Json.toJson(objectDests);
    }

    public static Result responseExceptionDeleted(Exception e){
        Throwable eRoot = Response.getCause(e);
        if(eRoot != null) {
            if(e!=null && e instanceof NullPointerException)
                return notFound(buildExtendResponse("Entity not found"));
        }
        return Response.internalServerErrorLF();
    }

    public static Result responseExceptionUpdated(Exception e){
        Throwable eRoot = Response.getCause(e);
        if(eRoot != null) {
            if (eRoot instanceof EntityNotFoundException)
                return notFoundEntity((EntityNotFoundException) eRoot);

            if (eRoot instanceof InvalidFormatException)
                return invalidParameter((InvalidFormatException) eRoot);

            if (eRoot instanceof JsonMappingException)
                return invalidParameter((JsonMappingException) eRoot);

            if (eRoot instanceof MysqlDataTruncation)
                return invalidParameter((MysqlDataTruncation) eRoot);

            if (eRoot instanceof MySQLIntegrityConstraintViolationException)
                return constraintViolation((MySQLIntegrityConstraintViolationException) eRoot);
        }
        return Response.internalServerErrorLF();
    }

    public static Result responseExceptionCreated(Exception e){
        Throwable eRoot = Response.getCause(e);
        if(eRoot != null) {
            if (eRoot instanceof EntityNotFoundException)
                return notFoundEntity((EntityNotFoundException) eRoot);

            if (eRoot instanceof InvalidFormatException)
                return invalidParameter((InvalidFormatException) eRoot);

            if (eRoot instanceof JsonMappingException)
                return Response.invalidParameter((JsonMappingException) eRoot);

            if (eRoot instanceof MysqlDataTruncation)
                return invalidParameter((MysqlDataTruncation) eRoot);

            if (eRoot instanceof MySQLIntegrityConstraintViolationException)
                return constraintViolation((MySQLIntegrityConstraintViolationException) eRoot);
        }
        return Response.internalServerErrorLF();
    }
}