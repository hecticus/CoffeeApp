package controllers.multimediaUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Config;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;




/**
 * Created by drocha on 8/12/16.
 */
public class Multimedia {

    public JsonNode uploadPhoto(JsonNode request)
    {
        try {



            URL url = new URL(Config.getString("Url-WS-Rackspace"));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(Config.getString("method"));
            conn.setRequestProperty("Content-Type", Config.getString("Content-Type"));

            ((ObjectNode) request).put("container", Config.getString("cdn-container"));
            ((ObjectNode) request).put("parent", Config.getString("cdn-parent"));

            OutputStream os = conn.getOutputStream();
            os.write(request.toString().getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK && conn.getResponseCode() != HttpURLConnection.HTTP_CREATED && conn.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output= br.readLine();
            conn.disconnect();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(output);

            return actualObj.get("result").get(0);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;

    }
}
