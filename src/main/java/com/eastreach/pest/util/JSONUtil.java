package com.eastreach.pest.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

/**
 * json处理工具类.
 **/
public class JSONUtil {

    public static Gson gson = new GsonBuilder()
            .setDateFormat(GsonDateConverter.dateFormat)
            .registerTypeAdapter(Integer.class, new GsonIntegerConverter())
            .registerTypeAdapter(int.class, new GsonIntegerConverter())
            .registerTypeAdapter(Double.class, new GsonDoubleConverter())
            .registerTypeAdapter(double.class, new GsonDoubleConverter())
            .registerTypeAdapter(Date.class, new GsonDateConverter())
            .registerTypeAdapter(String.class, new GsonStringConverter())
            .create();
}
