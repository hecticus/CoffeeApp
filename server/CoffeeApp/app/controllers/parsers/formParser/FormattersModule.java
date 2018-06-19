package controllers.parsers.formParser;

import com.google.inject.AbstractModule;

import play.data.format.Formatters;

/*
 * reference: https://www.playframework.com/documentation/2.5.x/JavaForms
 */
public class FormattersModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(Formatters.class).toProvider(FormattersProvider.class);

    }
}
