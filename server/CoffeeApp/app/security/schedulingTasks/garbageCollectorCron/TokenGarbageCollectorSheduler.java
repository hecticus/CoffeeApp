package security.schedulingTasks.garbageCollectorCron;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import scala.concurrent.duration.Duration;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @author  nisa
 * @since   2018
 *
 * reference: https://stackoverflow.com/questions/42139866/play-2-5-run-a-java-method-at-certain-times-of-day-cron
 */
public class TokenGarbageCollectorSheduler {

    @Inject
    public TokenGarbageCollectorSheduler(final ActorSystem system, @Named("token-garbageCollector-actor") ActorRef TokenGarbageCollectorActor) {
        system.scheduler().schedule(
                Duration.create(0, TimeUnit.MILLISECONDS), //Initial delay when system start
                Duration.create(1, TimeUnit.DAYS), //Frequency delay for next run
                //Duration.create(30, TimeUnit.SECONDS), //Frequency delay for next run
                TokenGarbageCollectorActor,
                "message for onreceive method of TokenGarbageCollectorActor",
                system.dispatcher(),
                null);
    }
}
