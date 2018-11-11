package security.schedulingTasks.garbageCollectorCron;

import com.google.inject.AbstractModule;
import play.libs.akka.AkkaGuiceSupport;

/**
 *
 *
 * @author  nisa
 * @since   2018
 *
 * reference: https://stackoverflow.com/questions/42139866/play-2-5-run-a-java-method-at-certain-times-of-day-cron
 */
public class TokenGarbageCollectorModule extends AbstractModule implements AkkaGuiceSupport {
    @Override
    protected void configure() {
        bindActor(TokenGarbageCollectorActor.class, "token-garbageCollector-actor");
        bind(TokenGarbageCollectorSheduler.class).asEagerSingleton();
    }
}
