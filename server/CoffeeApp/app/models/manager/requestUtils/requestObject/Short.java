package models.manager.requestUtils.requestObject;


import play.mvc.PathBindable;

/**
 * Created by yenny on 11/29/16.
 */
public class Short implements PathBindable<Short> {
    public java.lang.Short value;

    @Override
    public Short bind(String key, String value) {
        Short s = new Short();
        s.value = java.lang.Short.valueOf(value);
        return s;
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
