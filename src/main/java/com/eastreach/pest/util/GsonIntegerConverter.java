package com.eastreach.pest.util;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 *
 **/
public class GsonIntegerConverter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            if (json.getAsString().equals("")) {
                return 0;
            }
        } catch (Exception ignore) {
        }
        try {
            return json.getAsInt();
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}