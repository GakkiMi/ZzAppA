package com.huadingcloudpackage.www.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by hcw on 2016/10/10.
 */
public class JsonUtils {
    private static Gson mGson = new Gson();

    /**
     * 把一个Object类型的数据转换为 Json格式的字符串
     * @param object ：
     * @return ：
     */
    public static String toJsonString(Object object) {
        return mGson.toJson(object);
    }

    /**
     * 把一个Object类型的数据转换为 Json格式的字符串
     *
     * @param object ：
     * @return ：
     */
    public static String toJsonArray(Object object) {
        return mGson.toJson(object);
    }


    public static <T> List<T> parseArray(String jsonArrayString, TypeToken<List<T>> type) {
        return mGson.fromJson(jsonArrayString, type.getType());
    }

    public static <T> List<T> parseArray(String jsonArrayString, Type type) {
        return mGson.fromJson(jsonArrayString, type);
    }
    public static <T> T parseArray(JsonElement element, TypeToken<T> type) {
        return mGson.fromJson(element, type.getType());
    }

    @SuppressWarnings("hiding")
    public static <T> T parseJson(String response, Class<T> clazz) {
        try {
            return mGson.fromJson(response, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 将json 字符 解析 出来
     *
     * @param jsonString :json字符串
     * @param c          : Class 类型
     * @param <T>        : 泛型
     * @return :T所代表的类的对象
     */
    public static <T> T parse(String jsonString, Class<T> c) {
        return mGson.fromJson(jsonString, c);
    }

    public static boolean isJson(String content) {
        if (content == null || content.isEmpty() || content.length() == 0 || content.equals("")) {
            return false;
        }
        return true;
    }

    public static int getStatus(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            int status = jsonObject.getInt("status");
            return status;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int getCode(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            int code = jsonObject.getInt("code");
            return code;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
