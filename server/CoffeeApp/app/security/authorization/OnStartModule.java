package security.authorization;

import com.google.inject.AbstractModule;

/**
 * Created by nisa on 26/10/17.
 *
 * reference: https://www.playframework.com/documentation/2.5.x/ScalaDependencyInjection
 *
 */
public class OnStartModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(OnLoadRBAC.class).asEagerSingleton();
    }
}