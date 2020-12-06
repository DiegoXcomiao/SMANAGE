package org.manage.tools;

import com.google.gson.Gson;

/**
 * @author: Diego
 * @date: 2020/6/23 9:35
 * @Des:
 */
public class GsonUtil {
    public static String object2Json(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static <T> T json2Object(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }
}
