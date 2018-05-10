package models.requestUtils.requestObject;

import models.requestUtils.Request;
import play.mvc.PathBindable;

/**
 * Created by yenny on 11/25/16.
 */
public class Date implements PathBindable<Date> {
    public String value;

    @Override
    public Date bind(String key, String value) {
        Date date = new Date();
        if(!value.equals("-1")) {
            try {
                date.value = Request.dateFormatter.parseLocalDate(value).toString();
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
