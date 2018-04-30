package security.authentication.updatePassword;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.Result;

import static play.mvc.Results.*;

/**
 * Created by nisa on 27/11/17.
 */
public class Response {

    public static ObjectNode buildExtendResponse(String message, JsonNode result){
        ObjectNode response = Json.newObject();
        response.put("message", message);
        response.set("result", result);
        return response;
    }

    public static ObjectNode buildExtendResponse(String message){
        ObjectNode response = Json.newObject();
        response.put("message", message);
        return response;
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
    public static Result notFoundEntity(String entityType){
        return notFound(buildExtendResponse("Not found entity [" + entityType + "]"));
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
