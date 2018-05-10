package models.responseUtils;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import play.mvc.Result;

import javax.persistence.EntityNotFoundException;

/**
 * Created by nisa on 30/04/17.
 */
public class ExceptionsUtils {

    public static Result create(Exception e){
        e.printStackTrace();
        Throwable eRoot = getCause(e);
        if(eRoot != null) {
            if (eRoot instanceof EntityNotFoundException)
                return Response.notFoundEntity((EntityNotFoundException) eRoot);

            if (eRoot instanceof InvalidFormatException)
                return Response.invalidParameter((InvalidFormatException) eRoot);

            if (eRoot instanceof JsonMappingException)
                return Response.invalidParameter((JsonMappingException) eRoot);

            if (eRoot instanceof MysqlDataTruncation)
                return Response.invalidParameter((MysqlDataTruncation) eRoot);

            if (eRoot instanceof MySQLIntegrityConstraintViolationException)
                return Response.constraintViolation((MySQLIntegrityConstraintViolationException) eRoot);
        }
        return Response.internalServerErrorLF();
    }

    public static Result update(Exception e){
        e.printStackTrace();
        Throwable eRoot = getCause(e);
        if(eRoot != null) {
            if (eRoot instanceof EntityNotFoundException)
                return Response.notFoundEntity((EntityNotFoundException) eRoot);

            if (eRoot instanceof InvalidFormatException)
                return Response.invalidParameter((InvalidFormatException) eRoot);

            if (eRoot instanceof JsonMappingException)
                return Response.invalidParameter((JsonMappingException) eRoot);

            if (eRoot instanceof MysqlDataTruncation)
                return Response.invalidParameter((MysqlDataTruncation) eRoot);

            if (eRoot instanceof MySQLIntegrityConstraintViolationException)
                return Response.constraintViolation((MySQLIntegrityConstraintViolationException) eRoot);
        }
        return Response.internalServerErrorLF();
    }

    public static Result delete(Exception e){
        Throwable eRoot = getCause(e);
        if(eRoot != null) {
            if(e!=null && e instanceof NullPointerException)
                return Response.notFoundEntity("");
        }
        e.printStackTrace();
        return Response.internalServerErrorLF();
    }

    public static Result find(Exception e){
        e.printStackTrace();
        return Response.internalServerErrorLF();
    }

    /*
    * get the root exception
    */
    public static Throwable getCause(Throwable e) {
        Throwable cause = null;
        Throwable result = e;
        while(null != (cause = result.getCause())  && (result != cause))
            result = cause;
        return result;
    }
}
