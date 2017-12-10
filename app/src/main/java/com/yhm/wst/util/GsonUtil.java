package com.yhm.wst.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

/**
 * Created by Administrator on 2017/11/27.
 */
public class GsonUtil {
    private static GsonUtil mInstance;
    private Gson gson;

    public static GsonUtil getInstance() {
        if (mInstance == null) {
            synchronized (GsonUtil.class) {
                if (mInstance == null) {
                    mInstance = new GsonUtil();
                }
            }
        }
        return mInstance;
    }

    private GsonUtil() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .create();
    }

    public static Gson getGson(){
        return getInstance().gson;
    }
}
