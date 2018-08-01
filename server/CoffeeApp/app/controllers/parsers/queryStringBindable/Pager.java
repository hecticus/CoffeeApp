package controllers.parsers.queryStringBindable;

import play.mvc.QueryStringBindable;

import java.util.Map;
import java.util.Optional;

/**
 * Created by nisa on 25/07/17.
 *
 * reference: https://www.playframework.com/documentation/2.5.3/api/java/play/mvc/QueryStringBindable.html
 */
public class Pager implements QueryStringBindable<Pager> {
    public Integer index;
    public Integer size;

    public Optional<Pager> bind(String key, Map<String, String[]> data) {
        try {
            // must exist both o neither
            if(data.containsKey(key + ".index") && data.containsKey(key + ".size")) {
                index = Integer.parseInt(data.get(key + ".index")[0]);
                size = Integer.parseInt(data.get(key + ".size")[0]);
            }else if(data.containsKey(key + ".index") ^ data.containsKey(key + ".size")){
                return Optional.empty();
            }
            return Optional.ofNullable(this);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public String unbind(String key) {
        return key + ".index=" + index + "&" + key + ".size=" + size;
    }

    public String javascriptUnbind() {
        return "function(k,v) {\n" +
                "    return encodeURIComponent(k+'.index')+'='+v.index+'&'+encodeURIComponent(k+'.size')+'='+v.size;\n" +
                "}";
    }
}