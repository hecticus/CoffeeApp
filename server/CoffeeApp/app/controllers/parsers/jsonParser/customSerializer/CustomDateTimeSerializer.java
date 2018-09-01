package controllers.parsers.jsonParser.customSerializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by yenny on 9/22/16.
 */
public class CustomDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator jgen, SerializerProvider arg2) throws IOException {
        jgen.writeString(DATETIME_FORMATTER.format(value));
    }
}
