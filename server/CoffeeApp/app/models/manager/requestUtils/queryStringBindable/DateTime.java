package models.manager.requestUtils.queryStringBindable;

import play.mvc.QueryStringBindable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

/**
 * Created by nisa on 25/07/17.
 */
// https://www.playframework.com/documentation/2.5.x/RequestBinders
public class DateTime implements QueryStringBindable<DateTime> {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public LocalDateTime value;

    @Override
    public Optional<DateTime> bind(String key, Map<String, String[]> data) {
        try {
            if (data.containsKey(key))
                value = LocalDateTime.parse(data.get(key)[0], dateTimeFormatter);
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