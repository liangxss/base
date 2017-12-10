package com.yhm.wst.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yhm.wst.hprose.HProseManager;
import com.yhm.wst.util.LogUtil;


/**
 * 每隔一段时间刷新一次token
 * Created by liang_xs on 2017/11/22.
 */
public class TickleService extends Service {
    private long TICKLE_TIME = 1000 * 60 * 5;

    final Handler handler = new Handler();
    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            // 在此处添加执行的代码
            HProseManager.postAsyn("", "tickle", null, new HProseManager.StringCallback() {
                @Override
                public void onError(String s, Throwable throwable) {
                    LogUtil.i("liang-tickle", throwable.toString());
                }

                @Override
                public void onResponse(String json, Object[] objects) {
                    LogUtil.i("liang-tickle", "tickle: " + json);
                }
            });
            handler.postDelayed(this, TICKLE_TIME);// 延时时长
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler.postDelayed(runnable, TICKLE_TIME);// 打开定时器，执行操作
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i("liang-service", "serviceStart");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i("liang-service", "serviceDestroy");
        handler.removeCallbacks(runnable);// 关闭定时器处理
    }
}
