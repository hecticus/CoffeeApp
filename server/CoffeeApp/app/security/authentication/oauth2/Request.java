package security.authentication.oauth2;

import java.util.Map;

/**
 * Created by nisa on 18/10/17.
 */
public class Request {
    public static void verifyInvalidRequest(Map<String, String[]> request, String[] parameters) throws NullPointerException {
        for (String parameter: parameters){
            if(!request.containsKey(parameter)) {
                NullPointerException e = new NullPointerException(parameter);
                throw e;
            }
        }
    }
}
