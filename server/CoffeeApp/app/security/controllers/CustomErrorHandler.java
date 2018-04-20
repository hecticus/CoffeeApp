package security.controllers;

import play.Configuration;
import play.Environment;
import play.api.OptionalSourceMapper;
import play.api.routing.Router;
import play.http.DefaultHttpErrorHandler;

import javax.inject.Inject;
import javax.inject.Provider;

public class CustomErrorHandler extends DefaultHttpErrorHandler {

    @Inject
    public CustomErrorHandler(Configuration configuration, Environment environment,
                        OptionalSourceMapper sourceMapper, Provider<Router> routes) {
        super(configuration, environment, sourceMapper, routes);
    }
/*
    protected CompletionStage<Result> onDevServerError(RequestHeader var1, UsefulException var2) {
        return CompletableFuture.completedFuture(Results.status(500, views.html.error500.render()));
    }

    protected CompletionStage<Result> onProdServerError(RequestHeader var1, UsefulException var2) {
        return CompletableFuture.completedFuture(Results.status(500, views.html.error500.render()));
    }*/
}
