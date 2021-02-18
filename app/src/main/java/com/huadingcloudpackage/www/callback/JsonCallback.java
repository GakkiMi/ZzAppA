package com.huadingcloudpackage.www.callback;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.huadingcloudpackage.www.util.LogUtilsApp;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/**
 * @name 工程名：yaosu
 * @class 包名：com.yaosuce.www.okhttp_test2
 * @describe 描述：
 * @anthor 作者：whffi QQ:84569945
 * @time 时间：2017/6/13 16:07
 * @change 变更：
 * @chang 时间：
 * @class 描述：
 */

public abstract class JsonCallback<T> extends AbsCallback {
    private Type type;
    private Class<T> clazz;

    public JsonCallback() {

    }

    public JsonCallback(Type type) {
        this.type = type;

    }

    public JsonCallback(Class<T> clazz) {
        this.clazz = clazz;

    }

    public abstract void success(Response response) throws IOException;

    public abstract void error(Response response);

    @Override
    public void onError(Response response) {
        //  super.onError(response);
        error(response);
    }

    @Override
    public void onSuccess(Response response) {
        try {
            success(response);
        } catch (IOException e) {
            LogUtilsApp.d(e.getMessage());
        }
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) return null;

        T data = null;

        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(body.charStream());
        if (type != null) {
            data = gson.fromJson(jsonReader, type);

        } else if (clazz != null) {
            data = gson.fromJson(jsonReader, clazz);
        } else {
            Type genType = getClass().getGenericSuperclass();
            Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            data = gson.fromJson(jsonReader, type);

        }


        return data;
    }


}
