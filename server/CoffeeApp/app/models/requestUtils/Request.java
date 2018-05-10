package models.requestUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.responseUtils.Response;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;


/**
 * @author drocha yenny on 9/22/16.
 */
public class Request{

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    public static JsonNode removeParameter(JsonNode json, String parameter){
        JsonNode jsonRemoved = null;

        if(json.get(parameter) != null && json instanceof ObjectNode){
            jsonRemoved = json.get(parameter);
            ObjectNode object = (ObjectNode) json;
            object.remove(parameter);
        }
        return jsonRemoved;
    }

    public static JsonNode removeParameters(JsonNode json, String[] parameters){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonRemoved = mapper.createObjectNode();

        for(int i = 0; i < parameters.length; ++i) {
            if (json.get(parameters[i]) != null && json instanceof ObjectNode) {
                jsonRemoved.set(parameters[i], json.get(parameters[i]));
                ObjectNode object = (ObjectNode) json;
                object.remove(parameters[i]);
            }
        }
        return jsonRemoved;
    }

    public static java.sql.Date formatter_date(String date)
    {
        try
        {
            java.util.Date utilDate = dateFormat.parse(date);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            
            return sqlDate;
        }
        catch(Exception e){
            Throwable eRoot = Response.getCause(e);
            eRoot.printStackTrace();
           return null;
         }
    }
}
