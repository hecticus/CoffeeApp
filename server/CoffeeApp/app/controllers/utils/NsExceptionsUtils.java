package controllers.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mysql.jdbc.MysqlDataTruncation;

import controllers.utils.Response;
import org.apache.commons.lang3.exception.ExceptionUtils;
import play.mvc.Result;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeParseException;

/**
 * Created by nisa on 30/04/17.
 */
public class NsExceptionsUtils {

    public static Result create(Exception e){
        e.printStackTrace();
        //Throwable eRoot = getCause(e);
        Throwable eRoot = ExceptionUtils.getRootCause(e);
        if (eRoot == null) { // si no es aninado devuelve null, por lo tanto si es null se le asigna la excepcion original
            eRoot = e;
        }

        if (eRoot instanceof JsonParseException ||
            eRoot instanceof InvalidFormatException ||
            eRoot instanceof JsonMappingException ||
            eRoot instanceof MysqlDataTruncation ||
            eRoot instanceof DateTimeParseException ||
            eRoot instanceof IOException) {
            return Response.invalidParameter(eRoot.getMessage());

        } else if(eRoot instanceof IllegalStateException) {
            return Response.invalidParameter(eRoot.getMessage());

        } else if (eRoot instanceof com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException ||
            eRoot instanceof com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException) {
            return Response.constraintViolation(eRoot.getMessage());

        } else {
            return Response.internalServerErrorLF(eRoot != null ? eRoot.getMessage() : "");
        }
    }

    public static Result update(Exception e){
        e.printStackTrace();
        //Throwable eRoot = getCause(e);
        Throwable eRoot = ExceptionUtils.getRootCause(e);
        if (eRoot == null) {
            eRoot = e;
        }

        if (eRoot instanceof EntityNotFoundException) {
            return Response.notFoundEntity(eRoot.getMessage());

        } else if (eRoot instanceof JsonParseException ||
            eRoot instanceof InvalidFormatException ||
            eRoot instanceof JsonMappingException ||
            eRoot instanceof MysqlDataTruncation ||
            eRoot instanceof DateTimeParseException ||
            eRoot instanceof IOException) {
            return Response.invalidParameter(eRoot.getMessage());

        } else if (eRoot instanceof com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException ||
            eRoot instanceof com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException) {
            return Response.constraintViolation(eRoot.getMessage());

        } else {
            return Response.internalServerErrorLF(eRoot != null ? eRoot.getMessage() : "");
        }
    }

    public static Result delete(Exception e){
        e.printStackTrace();
        //Throwable eRoot = getCause(e);
        Throwable eRoot = ExceptionUtils.getRootCause(e);
        if (eRoot == null) {
            eRoot = e;
        }

        if (eRoot instanceof com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException) {
            return Response.constraintViolation(eRoot.getMessage());
        } else {
            return Response.internalServerErrorLF(eRoot.getMessage());
        }
    }

    public static Result find(Exception e){
        e.printStackTrace();
        //Throwable eRoot = getCause(e);
        Throwable eRoot = ExceptionUtils.getRootCause(e);
        if (eRoot == null) {
            eRoot = e;
        }
        return Response.internalServerErrorLF(eRoot.getMessage());
    }

    /*
    * get the root exception
    */
    public static Throwable getCause(Throwable e) {
        Throwable cause;
        Throwable result = e;
        while(null != (cause = result.getCause())  && (result != cause)) {
            result = cause;
        }
        return result;
    }
}
