package com.eastreach.pest.util;

import com.google.gson.*;
import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 **/
public class GsonDateConverter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    public static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            if (json.getAsString().equals("")) {
                return null;
            }
            String dateTimeString = json.getAsString();
            if (dateTimeString.contains("T") && dateTimeString.contains(".")) {
                dateTimeString = dateTimeString.split(".")[0];
                dateTimeString = dateTimeString.split("T")[0];
            }
            dateTimeString.replaceAll("/", "-");
            if (dateTimeString.length() < 12) {
                dateTimeString += " 00:00:00";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            return sdf.parse(dateTimeString);
//            DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFormat);
//            return formatter.parseDateTime(dateTimeString).toDate();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(new DateTime(src).toString(dateFormat));
    }
}