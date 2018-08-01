package security.authentication.oauth2;

import play.libs.Json;
import play.mvc.Result;

import static play.mvc.Results.*;
import static play.mvc.Results.internalServerError;

/**
 * Created by nisa on 18/10/17.
 *
 * reference: https://tools.ietf.org/html/rfc6749#section-5.2
 * reference: https://tools.ietf.org/html/rfc6750#section-3.1
 */
public class Response {

    public static class AccessTokenErrorResponse {
        public String error;
        public String error_description;
        public String error_uri;
    }

    /*
    * ok 200
    */
    public static Result granted(PasswordGrant.AccessTokenResponse accessTokenResponse){
        return ok(Json.toJson(accessTokenResponse));
    }

    /*
    * noContent 204
    */
    public static Result noContentLF(){
        return noContent();
    }

    /*
    * badRequest 400
    */
    public static Result invalidRequest(){
        AccessTokenErrorResponse accessTokenErrorResponse = new AccessTokenErrorResponse();
        accessTokenErrorResponse.error = "invalid_request";
        return badRequest(Json.toJson(accessTokenErrorResponse));
    }

    public static Result invalidRequest(String parameter){
        AccessTokenErrorResponse accessTokenErrorResponse = new AccessTokenErrorResponse();
        accessTokenErrorResponse.error = "invalid_request";
        accessTokenErrorResponse.error_description = "'" + parameter +"'";
        return badRequest(Json.toJson(accessTokenErrorResponse));
    }

    public static Result invalidClient(){
        AccessTokenErrorResponse accessTokenErrorResponse = new AccessTokenErrorResponse();
        accessTokenErrorResponse.error = "invalid_client";
        return unauthorized(Json.toJson(accessTokenErrorResponse));
    }

    public static Result invalidGrant(){
        AccessTokenErrorResponse accessTokenErrorResponse = new AccessTokenErrorResponse();
        accessTokenErrorResponse.error = "invalid_grant";
        return badRequest(Json.toJson(accessTokenErrorResponse));
    }

    public static Result invalidGrant(String error_description){
        AccessTokenErrorResponse accessTokenErrorResponse = new AccessTokenErrorResponse();
        accessTokenErrorResponse.error = "invalid_grant";
        accessTokenErrorResponse.error = error_description;
        return badRequest(Json.toJson(accessTokenErrorResponse));
    }

    public static Result unauthorizedClient(){
        AccessTokenErrorResponse accessTokenErrorResponse = new AccessTokenErrorResponse();
        accessTokenErrorResponse.error = "unauthorized_client";
        return badRequest(Json.toJson(accessTokenErrorResponse));
    }

    public static Result unsupportedGrantType(){
        AccessTokenErrorResponse accessTokenErrorResponse = new AccessTokenErrorResponse();
        accessTokenErrorResponse.error = "unsupported_grant_type";
        return badRequest(Json.toJson(accessTokenErrorResponse));
    }

    public static Result invalidScope(){
        AccessTokenErrorResponse accessTokenErrorResponse = new AccessTokenErrorResponse();
        accessTokenErrorResponse.error = "invalid_scope";
        return badRequest(Json.toJson(accessTokenErrorResponse));
    }

    /*
    * unauthorized 401
    */
    /*public static Result invalidClient(){
        AccessTokenErrorResponse accessTokenErrorResponse = new AccessTokenErrorResponse();
        accessTokenErrorResponse.error = "invalid_client";
        return unauthorized(Json.toJson(accessTokenErrorResponse));
    }*/
    public static Result invalidToken(){
        AccessTokenErrorResponse accessTokenErrorResponse = new AccessTokenErrorResponse();
        accessTokenErrorResponse.error = "invalid_token";
        return unauthorized(Json.toJson(accessTokenErrorResponse));
    }

    /*
    * forbidden 403
    */
    public static Result insufficientScope(){
        AccessTokenErrorResponse accessTokenErrorResponse = new AccessTokenErrorResponse();
        accessTokenErrorResponse.error = "insufficient_scope";
        return forbidden(Json.toJson(accessTokenErrorResponse));
    }

    /*
    * internalServerError 500
    */
    public static Result internalServerErrorLF(){
        return internalServerError(("Oops!"));
    }
    public static Result internalServerErrorLF(String message){
        return internalServerError("Oops!: " + message);
    }
}
