package controllers.parsers.queryStringBindable;

import play.mvc.QueryStringBindable;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

/**
 * Created by nisa on 25/07/17.
 *
 * reference: https://www.playframework.com/documentation/2.5.x/RequestBinders
 */
public class DateTime implements QueryStringBindable<DateTime> {
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
    public ZonedDateTime value;

    @Override
    public Optional<DateTime> bind(String key, Map<String, String[]> data) {
        try {
            if (data.containsKey(key))
                value = ZonedDateTime.parse(data.get(key)[0], DATETIME_FORMATTER);
            return Optional.ofNullable(this);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    public String unbind(String key) {
        return key + "=" + value;
    }

    @Override
    public String javascriptUnbind() {
        return "function(k,v) {\n" +
                "    return encodeURIComponent(k)+'='+v;\n" +
                "}";
    }
}