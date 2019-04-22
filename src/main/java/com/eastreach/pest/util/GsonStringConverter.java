package com.eastreach.pest.util;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * gson字符串处理器
 * 字符串序列化时，如果字符串内容为 [111],  中括号加数字，就认为时数组处理.
 */
public class GsonStringConverter implements JsonSerializer<String>, JsonDeserializer<String> {
    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String result = json.toString();
        if (result.startsWith("{") && result.endsWith("}")) {
            return "\"" + result + "\"";
        }
        return json.getAsString();
    }

    /**
     * @param src
     * @param typeOfSrc
     * @param context
     * @return
     */
    @Override
    public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
        if (src != null && src.startsWith("[") && src.endsWith("]")) {
            src = src.subSequence(1, src.length() - 1).toString();
        }
        return new JsonPrimitive(src);
    }
}
