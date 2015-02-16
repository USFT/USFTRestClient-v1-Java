package com.usft.rest;
import com.google.gson.*;
import java.lang.reflect.*;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.format.*;
/**
 * From    https://sites.google.com/site/gson/gson-type-adapters-for-common-classes-1
 */
public final class DateTimeTypeConverter
        implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

    protected DateTimeFormatter formatter = ISODateTimeFormat.dateTime();

    @Override
    public JsonElement serialize(DateTime src, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.print(src));
    }

    @Override
    public DateTime deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        try {
            return new DateTime(json.getAsString());
        } catch (IllegalArgumentException e) {
            // May be it came in formatted as a java.util.Date, so try that
            Date date = context.deserialize(json, Date.class);
            return new DateTime(date);
        }
    }
}