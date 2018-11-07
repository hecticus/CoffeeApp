package controllers.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nisa on 28/04/17.
 */
public class JsonUtilsN {

    /*
    * serializa un arreglo en json
    */
    public static List<Long> toArrayLong(final JsonNode jsonNode, final String fieldName) throws Exception {
        final List<Long> numbers = new ArrayList<>();
        for(JsonNode objNode : jsonNode.get(fieldName)) {
            if (StringUtils.isNumeric(objNode.asText())) {
                numbers.add(objNode.asLong());
            }else {
                throw new Exception(objNode.asText() + " not is numeric");
            }
        }
        return numbers;
    }
}
