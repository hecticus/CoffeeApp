package models.manager;

import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.Config;
import play.libs.mailer.MailerClient;
import play.mvc.Result;
/**
 * Created by yenny on 10/3/16.
 */
public interface UserManager {

    Result findByEmail(String email);

    Result uploadPhoto(JsonNode request);

    Result login(Config config);

    Result logout();

    Result authorize(Config config, String path);

    Result verify(Config config);

    Result startResetPassword(String email, MailerClient mailerClient, Config config);

    Result handleStartResetPassword();

}
