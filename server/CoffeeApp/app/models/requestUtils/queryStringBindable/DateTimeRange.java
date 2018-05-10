package models.requestUtils.queryStringBindable;

import play.mvc.QueryStringBindable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

/**
 * Created by nisa on 25/07/17.
 */
public class DateTimeRange implements QueryStringBindable<DateTimeRange> {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public LocalDateTime from;
    public LocalDateTime to;
    @Override
    public Optional<DateTimeRange> bind(String key, Map<String, String[]> data) {
        try {
            if(data.containsKey(key + ".from"))
                from = LocalDateTime.parse(data.get(key + ".from")[0], dateTimeFormatter);
            if(data.containsKey(key + ".to"))
                to = LocalDateTime.parse(data.get(key + ".to")[0], dateTimeFormatter);

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
