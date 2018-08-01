package controllers.parsers.formParser;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import play.data.format.Formatters;
import play.data.format.Formatters.SimpleFormatter;
import play.i18n.MessagesApi;

/*
 * reference: https://www.playframework.com/documentation/2.5.x/JavaForms
 */
@Singleton
public class FormattersProvider implements Provider<Formatters> {

    private final MessagesApi messagesApi;

    @Inject
    public FormattersProvider(MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }

    @Override
    public Formatters get() {
        Formatters formatters = new Formatters(messagesApi);

        formatters.register(LocalDate.class, new SimpleFormatter<LocalDate>() {
            /*private Pattern localDatePattern = Pattern.compile(
                    "^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$"
            );*/
            private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public LocalDate parse(String input, Locale l) throws ParseException {
                /*Matcher m = localDatePattern.matcher(input);
                if (!m.find()) throw new ParseException("No valid Input", 0);
                int y = Integer.valueOf(m.group(1));
                int M = Integer.valueOf(m.group(2));
                int d = Integer.valueOf(m.group(3));
                return LocalDate.of(y, M, d);*/
                try {
                    return LocalDate.parse(input, DATE_FORMATTER);
                } catch (DateTimeParseException e) {
                    throw new ParseException("No valid Input", 0);
                }
            }

            @Override
            public String print(LocalDate localDate, Locale l) {
                return localDate.toString();
                //return localDate.format(DATE_FORMATTER);
            }
        });

        formatters.register(ZonedDateTime.class, new SimpleFormatter<ZonedDateTime>() {
            private final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");

            @Override
            public ZonedDateTime parse(String input, Locale l) throws ParseException {
                try {
                    return ZonedDateTime.parse(input, DATETIME_FORMATTER);
                } catch (DateTimeParseException e) {
                    throw new ParseException("No valid Input", 0);
                }
            }

            @Override
            public String print(ZonedDateTime localDateTime, Locale l) {
                return localDateTime.toString();
                //return localDateTime.format(DATE_FORMATTER);
            }
        });

        return formatters;
    }
}
