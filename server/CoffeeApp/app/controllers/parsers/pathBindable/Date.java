package controllers.parsers.pathBindable;

import play.mvc.PathBindable;

import java.time.format.DateTimeFormatter;

/**
 * Created by nisa on 25/07/17.
 */
public class Date implements PathBindable<Date> {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public String value;

    @Override
    public Date bind(String key, String value) {
        Date date = new Date();
        if(!value.equals("-1")) {
            try {
                date.value = DATE_FORMATTER.parse(value).toString();
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }else{
            date.value = value;
        }
        return date;
    }

    @Override
    public String unbind(String key) {
        return null;
    }

    @Override
    public String javascriptUnbind() {
        return null;
    }
}
