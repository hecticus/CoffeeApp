package security.authentication;

import play.libs.Json;
import play.mvc.Result;

import static play.mvc.Results.*;
import static play.mvc.Results.internalServerError;

/**
 * Created by nisa on 18/10/17.
 *
 * reference:
 * https://tools.ietf.org/html/rfc6749#section-5.2
 * https://tools.ietf.org/html/rfc6750#section-3.1
 */
public class ResponseAuth {

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
    public static Result invalidRequest(String description){
        AccessTokenErrorResponse accessTokenErrorResponse = new AccessTokenErrorResponse();
        accessTokenErrorResponse.error = "invalid_request";
        accessTokenErrorResponse.error_description = description;
        return badRequest(Json.toJson(accessTokenErrorResponse));
    }

    public static Result invalidClient(String description){
        AccessTokenErrorResponse accessTokenErrorResponse = new AccessTokenErrorResponse();
        accessTokenErrorResponse.error = "invalid_client";
        accessTokenErrorResponse.error_description = description;
        return unauthorized(Json.toJson(accessTokenErrorResponse));
    }

    public static Result invalidGrant(String description){
        AccessTokenErrorResponse accessTokenErrorResponse = new AccessTokenErrorResponse();
        accessTokenErrorResponse.error = "invalid_grant";
        accessTokenErrorResponse.error_description = description;
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
        accessTokenErrorResponse.error_description = "The authorization grant_type is not supported.";
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
    // reference: https://tools.ietf.org/html/rfc6750#page-9
    public static Result invalidToken(){
        AccessTokenErrorResponse accessTokenErrorResponse = new AccessTokenErrorResponse();
        accessTokenErrorResponse.error = "invalid_token";
        accessTokenErrorResponse.error_description = "Token invalid.";
        return unauthorized(Json.toJson(accessTokenErrorResponse));
    }
    public static Result expiredToken(){
        AccessTokenErrorResponse accessTokenErrorResponse = new AccessTokenErrorResponse();
        accessTokenErrorResponse.error = "expired_token";
        accessTokenErrorResponse.error_description = "The token expired.";
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
    public static Result internalServerErrorLF(String message){
        return internalServerError("Oops!: " + message);
    }
}
