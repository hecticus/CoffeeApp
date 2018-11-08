package controllers.userControllers.recoverPassword;

import io.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.Response;
import models.userModels.RecoverPassword;
import models.userModels.User;
import play.libs.mailer.MailerClient;
import security.SecurityUtils;
import security.models.AuthUser;
import org.apache.commons.mail.EmailException;
import com.typesafe.config.Config;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

import java.time.ZonedDateTime;

/**
 * Created by nisa on 13/10/17.
 */
public class RecoverPasswords extends Controller {

    private static class RecoverRequest {
        public String email;
        public String newPassword;
        public String pin;
    }

    private static class Pin{
        public static Integer length;
        public static Integer expiresIn;
    }

    private static class Mailer{
        public static String email;
        public static String issuer;
    }

    @Inject
    MailerClient mailerClient;

    @Inject
    RecoverPasswords(Config config){
        Config configRecoverPass = config.getConfig("recoverpass.pin");
        Pin.length = configRecoverPass.getInt("length");
        Pin.expiresIn = configRecoverPass.getInt("expiresIn");

        Config configMailer = config.getConfig("play.mailer");
        Mailer.issuer = configMailer.getString("issuer");
        Mailer.email = configMailer.getString("user");
    }

    public Result recover() {
        JsonNode request = request().body().asJson();
        if (request == null)
            return Response.requiredJson();

        RecoverRequest recoverRequest = Json.fromJson(request, RecoverRequest.class);

        if(recoverRequest.newPassword != null && recoverRequest.pin != null){
            return recievePin(recoverRequest);
        }else if (recoverRequest.email != null){
            return sendPin(recoverRequest.email);
        }
        return Response.requiredParameter("newPassword/pin");
    }

    private Result sendPin(String email) {
        try {
            User user = User.findByEmail(email);
            if(user == null)
                return Response.notFoundEntity("user");

            RecoverPassword recoverPassword = new RecoverPassword(
                    SecurityUtils.generatePin(Pin.length),
                    ZonedDateTime.now().plusMinutes(Pin.expiresIn),
                    Pin.expiresIn,
                    user
            );

            Mail mail = new Mail(
                    mailerClient,
                    Mailer.email,
                    Mailer.issuer,
                    user.getAuthUser().getEmail(),
                    user.getAuthUser().getEmail(),
                    recoverPassword.getPin(),
                    recoverPassword.getExpiresIn());
            mail.send();

            if(user.getRecoverPassword() == null){
                recoverPassword.insert();
            }else{
                recoverPassword.update();
            }

            recoverPassword.setPin(null); // important for response security information*/
            return Response.createdEntity(Json.toJson(recoverPassword));

        } catch (EmailException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.internalServerErrorLF();
    }

    private Result recievePin(RecoverRequest recoverRequest) {
        Ebean.beginTransaction();
        try {
            RecoverPassword recoverPassword = RecoverPassword.findByPin(recoverRequest.pin);
            if(recoverPassword == null)
                return Response.notFoundEntity("pin");

            if(ZonedDateTime.now().isAfter(recoverPassword.getExpiration()))
                return Response.badCredentials("pin expired", Json.toJson(recoverPassword));

            AuthUser authUser = recoverPassword.getUser().getAuthUser();
            authUser.setPassword(recoverRequest.newPassword); //TODO encriptar
            authUser.update();
            recoverPassword.delete();

            Ebean.commitTransaction();

            authUser.setPassword(null);
            return Response.updatedEntity(Json.toJson(authUser));

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            Ebean.endTransaction();
        }
        return Response.internalServerErrorLF();
    }
}
