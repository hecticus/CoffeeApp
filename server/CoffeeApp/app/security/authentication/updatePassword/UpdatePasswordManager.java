package security.authentication.updatePassword;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.mailer.MailerClient;
import security.authentication.oauth2.BaseGrant;
import security.SecurityUtils;
import security.authorization.CoffeeSecurity;
import security.models.AuthUser;
import security.models.SecurityPin;
import org.apache.commons.mail.EmailException;
import play.Configuration;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

import java.time.ZonedDateTime;

/**
 * Created by nisa on 13/10/17.
 */
public class UpdatePasswordManager extends Controller { //TODO USE OWN RESPONSE
    @Inject
    MailerClient mailerClient;

    private static class ResetRequest {
        public String currentPassword;
        public String newPassword;
    }

    private static class RecoverRequest {
        public String email;
        public String newPassword;
        public String pin;
    }

    private static class Pin{
        public static Integer length;
        public static Integer expiresIn;
        public static Integer tries;
    }

    private static class Mailer{
        public static String email;
        public static String issuer;
    }

    private static BaseGrant baseGrant;

    @Inject
    UpdatePasswordManager(Configuration config){
        Configuration configRecoverPass = config.getConfig("play.recoverpass.pin");
        Pin.length = configRecoverPass.getInt("length");
        Pin.expiresIn = configRecoverPass.getInt("expiresIn");
        Pin.tries = configRecoverPass.getInt("tries");

        Configuration configMailer = config.getConfig("play.mailer");
        Mailer.issuer = configMailer.getString("issuer");
        Mailer.email = configMailer.getString("user");

        baseGrant = new BaseGrant(config);
    }

    public Result resetPassword() {
        try {
            String token = CoffeeSecurity.getTokenFromHeader(request());

            AuthUser authUser = baseGrant.verifyAccessToken(token);
            if(authUser == null)
                return Response.badCredentials();

            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();

            ResetRequest resetRequest = Json.fromJson(request, ResetRequest.class);
            if(resetRequest.currentPassword == null || resetRequest.newPassword == null)
                return Response.requiredParameter("currentPassword/newPassword");

            if(!authUser.getPassword().equals(resetRequest.currentPassword))
                return Response.badCredentials("check your current password");

            authUser.setPassword(resetRequest.newPassword); //TODO encriptar
            authUser.update();

            return ok(Response.buildExtendResponse("Successful reset password"));
        }catch(Exception e){
            e.printStackTrace();
            return Response.internalServerErrorLF();
        }
    }

    public Result recoverPassword() {
        JsonNode request = request().body().asJson();
        if (request == null)
            return Response.requiredJson();

        RecoverRequest recoverRequest = Json.fromJson(request, RecoverRequest.class);
        if(recoverRequest.email != null && recoverRequest.newPassword != null && recoverRequest.pin != null){
            return recoverPasswordStep2(recoverRequest);
        }else if (recoverRequest.email != null){
            return recoverPasswordStep1(recoverRequest.email);
        }
        return Response.requiredParameter("email/newPassword/pin");
    }

    private Result recoverPasswordStep1(String email) {
        try {
            AuthUser authUser = AuthUser.findByEmail(email);
            if(authUser == null)
                return Response.notFoundEntity("user");

            SecurityPin securityPin = new SecurityPin(
                    SecurityUtils.generatePin(Pin.length),
                    ZonedDateTime.now().plusMinutes(Pin.expiresIn),
                    Pin.expiresIn,
                    Pin.tries,
                    authUser);
            if(SecurityPin.findByUserId(authUser.getId()) == null){
                securityPin.insert();
            }else{
                securityPin.update();
            }

            Mail mail = new Mail(
                    mailerClient,
                    Mailer.email,
                    Mailer.issuer,
                    authUser.getEmail(),
                    authUser.getEmail(),
                    securityPin.getPin(),
                    securityPin.getExpiresIn());
            mail.send();

            securityPin.setPin(null); // important for response security information
            return ok(Response.buildExtendResponse("Your PIN have been send it to email", Json.toJson(securityPin)));

        } catch (EmailException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.internalServerErrorLF();
    }

    private Result recoverPasswordStep2(RecoverRequest recoverRequest) {
        try {
            AuthUser authUser = AuthUser.findByEmail(recoverRequest.email);
            if(authUser == null)
                return Response.notFoundEntity("user");

            SecurityPin securityPin = SecurityPin.findByUserId(authUser.getId());
            if(securityPin == null)
                return Response.invalidParameter("pin");

            if(securityPin.getTries() > 0){
                if(ZonedDateTime.now().isBefore(securityPin.getExpiration()) && securityPin.getPin().equals(recoverRequest.pin)) {
                    authUser.setPassword(recoverRequest.newPassword); //TODO encriptar
                    authUser.update();
                    SecurityPin.delete(securityPin.getId());
                    return ok(Response.buildExtendResponse("Successful reset password"));
                }
                securityPin.setTries((securityPin.getTries() - 1));
                securityPin.update();
            }

            securityPin = new SecurityPin(securityPin.getExpiration(), securityPin.getTries()); // important for response security information
            return Response.badCredentials("pin", Json.toJson(securityPin));

        } catch (Exception e) {
            e.printStackTrace();
            return Response.internalServerErrorLF();
        }
    }
}
