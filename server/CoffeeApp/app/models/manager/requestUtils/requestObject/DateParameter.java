package models.manager.requestUtils.requestObject;

import models.manager.requestUtils.Request;
import play.mvc.QueryStringBindable;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Optional;

/**
 * Created by gmantilla on 4/28/17.
 */
public class DateParameter implements QueryStringBindable<DateParameter> {

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    public String value;

    @Override
    public Optional<DateParameter> bind(String key, Map<String, String[]> data) {
        if (data.containsKey(key)) {
            try {
                //value = formatter.parse(data.get(key)[0]);
                value = Request.dateFormatter.parseLocalDate(data.get(key)[0]).toString();
                return Optional.<DateParameter>ofNullable(this);
            } catch (NumberFormatException e) {
                return Optional.<DateParameter>empty();
            }
        } else {
            return Optional.<DateParameter>empty();
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