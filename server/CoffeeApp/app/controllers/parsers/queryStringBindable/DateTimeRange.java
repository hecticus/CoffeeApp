package controllers.parsers.queryStringBindable;

import play.mvc.QueryStringBindable;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

/**
 * Created by nisa on 25/07/17.
 */
public class DateTimeRange implements QueryStringBindable<DateTimeRange> {
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
    public ZonedDateTime from;
    public ZonedDateTime to;

    @Override
    public Optional<DateTimeRange> bind(String key, Map<String, String[]> data) {
        try {
            if(data.containsKey(key + ".from"))
                from = ZonedDateTime.parse(data.get(key + ".from")[0], DATETIME_FORMATTER);
            if(data.containsKey(key + ".to"))
                to = ZonedDateTime.parse(data.get(key + ".to")[0], DATETIME_FORMATTER);

            // <key>.from must greater or equal than <key>.to
            if(from != null && to != null && from.compareTo(to) > 0)
                return Optional.empty();

            return Optional.ofNullable(this);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public String unbind(String key) {
        return new StringBuilder()
                .append("from=")
                .append(from)
                .append("&to=")
                .append(to)
                .toString();
    }

    @Override
    public String javascriptUnbind() {
        return null;
    }
}
