package models.manager.requestUtils;

import com.fasterxml.jackson.databind.JsonNode;
import  java.util.regex.Matcher;
import  java.util.regex.Pattern;

/**
 * @author drocha and yenny
 * @version 9/9/16.
 */
public class Validation {

    public static boolean isInt(JsonNode json){
        if(json.isInt())
            return true;
        return false;
    }

    public static boolean isLong(JsonNode json){
        if(json.isIntegralNumber())
            return true;
        return false;
    }

    public static boolean isDouble(JsonNode json){
        if(json.isNumber())
            return true;
        return false;
    }

    public static boolean isTextual(JsonNode json){
        if(json.isTextual())
            return true;
        return false;
    }

    /**
     * @param parametrs check the parameter (string type) with regular expressions and validate it Long
     * @return true or false
     * @author drocha
     * @version 9/9/16.
     */
    public static Boolean isLong(String parametrs)
    {

        Pattern pat = Pattern.compile("[0-9]");
        Matcher mat = pat.matcher(parametrs);
        if (mat.matches())
            return true;
        return false;

    }

    /**
     * @param parametrs check the parameter (string type) with regular expressions and validate it textual
     * @return true or false
     * @author drocha
     * @version 9/9/16.
     */
    public static Boolean isTextual(String parametrs)
    {

        Pattern pat = Pattern.compile("[a-zA-Z0-9\\s]");
        Matcher mat = pat.matcher(parametrs);
        if (mat.matches())
            return true;
        return false;

    }

    /**
     * @param parametrs check the parameter (string type) with regular expressions and validate it double
     * @return true or false
     * @author drocha
     * @version 9/9/16.
     */
    public static Boolean isDouble(String parametrs)
    {

        Pattern pat = Pattern.compile("[-+]?[0-9]*\\.?[0-9]");
        Matcher mat = pat.matcher(parametrs);
        if (mat.matches())
            return true;
        return false;

    }

    /**
     * @param parametrs check the parameter (string type) with regular expressions and validate it email
     * @return true or false
     * @author drocha
     * @version 9/9/16.
     */
    public static Boolean isEmail(String parametrs)
    {

        Pattern pat = Pattern.compile("[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,3})");
        Matcher mat = pat.matcher(parametrs);
        if (mat.matches())
            return true;
        return false;

    }
}
