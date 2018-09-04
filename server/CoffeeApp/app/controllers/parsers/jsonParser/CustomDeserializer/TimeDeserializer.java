package controllers.parsers.jsonParser.CustomDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    @Override
    public ZonedDateTime deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException {
        return  ZonedDateTime.parse(jsonparser.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX"));
    }
}