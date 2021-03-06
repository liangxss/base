package com.yhm.wst;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.yhm.wst.util.CommonPreference;

/**
 * Created by liang on 2017/11/28.
 */
public class Config {
    public final static boolean DEBUG = isDebug(MyApplication.getApplication());
    public static final String API_HOST ;

    static {
        if(DEBUG){
            API_HOST = CommonPreference.getApiHost();
        }else{
            API_HOST = "https://api/api/";
        }
    }

    private static boolean isDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

}
