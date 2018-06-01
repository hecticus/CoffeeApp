package controllers.utils;

import io.ebean.text.PathProperties;

import java.util.HashMap;
import java.util.Map;

public class PropertiesCollections {

    Map<String, PathProperties> pathProperties = new HashMap<>();

    public void putPropertiesCollection(String key, String collection){
        pathProperties.put(key, PathProperties.parse(collection));
    }

    public PathProperties getPathProperties(String collection){
        if(collection == null || collection.isEmpty())
            return null;

        if(!pathProperties.isEmpty()) {
            for (Map.Entry<String, PathProperties> entry : pathProperties.entrySet()) {
                if (collection.equalsIgnoreCase(entry.getKey())) {
                    return entry.getValue();
                }
            }
        }

        return PathProperties.parse(collection);
    }
}
