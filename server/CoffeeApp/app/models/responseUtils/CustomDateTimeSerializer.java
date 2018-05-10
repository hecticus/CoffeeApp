package models.responseUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * Created by yenny on 9/22/16.
 */

public class CustomDateTimeSerializer extends JsonSerializer<DateTime> {

    private static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void serialize(DateTime value, JsonGenerator gen, SerializerProvider arg2) throws IOException, JsonProcessingException {
        gen.writeString(formatter.print(value));
    }
}