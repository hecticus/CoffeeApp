package security.schedulingTasks.garbageCollectorCron;

import akka.actor.UntypedActor;
import security.authentication.BaseGrant;
import security.models.SecurityToken;

import javax.inject.Inject;
import java.util.List;

/**
 *
 *
 * @author  nisa
 * @since   2018
 *
 * reference: https://stackoverflow.com/questions/42139866/play-2-5-run-a-java-method-at-certain-times-of-day-cron
 */
public class TokenGarbageCollectorActor extends UntypedActor {

    @Inject
    public BaseGrant baseGrant;

    @Override
    public void onReceive(final Object message) throws Throwable {
        tokenRevokeCron();
        //Logger.info("Write your crone task or simply call your method here that perform your task " + message);
    }

    public void tokenRevokeCron() {
        List<SecurityToken> securityRefreshTokens = SecurityToken.findAllByType(BaseGrant.TOKEN_TYPE_REFRESH);
        if(securityRefreshTokens != null) {
            securityRefreshTokens.forEach(securityToken -> {
                try {
                    baseGrant.verifyToken(securityToken.getToken());
                } catch (Exception e) {
                    securityToken.delete();
                }
            });
        }

        List<SecurityToken> securityAccessTokens = SecurityToken.findAllByType(BaseGrant.TOKEN_TYPE_ACCESS);
        if(securityAccessTokens != null) {
            securityAccessTokens.forEach(securityToken -> {
                try {
                    baseGrant.verifyToken(securityToken.getToken());
                } catch (Exception e) {
                    securityToken.delete();
                }
            });
        }
    }
}
