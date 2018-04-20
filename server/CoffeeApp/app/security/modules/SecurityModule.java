
package security.modules;

import be.objectify.deadbolt.java.cache.HandlerCache;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.client.direct.AnonymousClient;
import org.pac4j.core.config.Config;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.matching.PathMatcher;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.http.client.direct.DirectBasicAuthClient;
import org.pac4j.http.client.direct.DirectFormClient;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.http.client.indirect.IndirectBasicAuthClient;
import org.pac4j.http.credentials.authenticator.test.SimpleTestUsernamePasswordAuthenticator;
import org.pac4j.play.CallbackController;
import org.pac4j.play.LogoutController;
import org.pac4j.play.deadbolt2.Pac4jHandlerCache;
import org.pac4j.play.deadbolt2.Pac4jRoleHandler;
import org.pac4j.play.store.PlayCacheSessionStore;
import org.pac4j.play.store.PlaySessionStore;
import play.Configuration;
import play.Environment;
import play.cache.SyncCacheApi;
import security.controllers.CustomAuthorizer;
import security.controllers.DemoHttpActionAdapter;
import security.util.Utils;

public class SecurityModule extends AbstractModule {

    private final Configuration configuration;

    private static class MyPac4jRoleHandler implements Pac4jRoleHandler { }

    private final String baseUrl;

    public SecurityModule(final Environment environment, final Configuration configuration) {
        this.configuration = configuration;
//        this.baseUrl = configuration.getString("baseUrl");
        this.baseUrl = configuration.getString("play.http.secret.key");
    }

    @Override
    protected void configure() {
        bind(HandlerCache.class).to(Pac4jHandlerCache.class);

        bind(Pac4jRoleHandler.class).to(MyPac4jRoleHandler.class);
        final PlayCacheSessionStore playCacheSessionStore = new PlayCacheSessionStore(getProvider(SyncCacheApi.class));
        //bind(PlaySessionStore.class).toInstance(playCacheSessionStore);
        bind(PlaySessionStore.class).to(PlayCacheSessionStore.class);

        // callback
        final CallbackController callbackController = new CallbackController();
        callbackController.setDefaultUrl("/");
        callbackController.setMultiProfile(true);
        bind(CallbackController.class).toInstance(callbackController);

        // logout
        final LogoutController logoutController = new LogoutController();
        logoutController.setDefaultUrl("/?defaulturlafterlogout");
        //logoutController.setDestroySession(true);
        bind(LogoutController.class).toInstance(logoutController);
    }


    @Provides
    protected FormClient provideFormClient() {
        return new FormClient(baseUrl + "/loginForm", new SimpleTestUsernamePasswordAuthenticator());
    }



    @Provides
    protected DirectFormClient provideDirectFormClient() {
        final Authenticator<UsernamePasswordCredentials> blockingAuthenticator = (credentials, ctx) -> {

            final int wait = Utils.block();

            if (Utils.random(10) <= 7) {
                CommonProfile profile = new CommonProfile();
                profile.setId("fake" + wait);
                credentials.setUserProfile(profile);
            }
        };
        return new DirectFormClient(blockingAuthenticator);
    }

    @Provides
    protected DirectBasicAuthClient provideDirectBasicAuthClient() {
        return new DirectBasicAuthClient(new SimpleTestUsernamePasswordAuthenticator());
    }

    @Provides
    protected Config provideConfig( FormClient formClient,
                                   IndirectBasicAuthClient indirectBasicAuthClient,
                                   ParameterClient parameterClient, DirectBasicAuthClient directBasicAuthClient,
                                   DirectFormClient directFormClient) {

        //casClient.getConfiguration().setProxyReceptor(casProxyReceptor);

        final Clients clients = new Clients(baseUrl + "/callback", formClient,
                indirectBasicAuthClient,  parameterClient, directBasicAuthClient,
                new AnonymousClient(), directFormClient);

        final Config config = new Config(clients);
        config.addAuthorizer("admin", new RequireAnyRoleAuthorizer<>("ROLE_ADMIN"));
        config.addAuthorizer("custom", new CustomAuthorizer());
        config.addMatcher("excludedPath", new PathMatcher().excludeRegex("^/facebook/notprotected\\.html$"));
        config.setHttpActionAdapter(new DemoHttpActionAdapter());
        return config;
    }
}

