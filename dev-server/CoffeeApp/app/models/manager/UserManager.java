package models.manager;

import com.fasterxml.jackson.databind.JsonNode;
import play.Configuration;
import play.libs.mailer.MailerClient;
import play.mvc.Result;

/**
 * Created by yenny on 10/3/16.
 */
public interface UserManager {

    Result findByEmail(String email);

    Result uploadPhoto(JsonNode request);

    Result login(Configuration config);

    Result authorize(Configuration config, String path);

    Result verify(Configuration config);

    Result startResetPassword(String email, MailerClient mailerClient, Configuration config);

    Result handleStartResetPassword();

}
