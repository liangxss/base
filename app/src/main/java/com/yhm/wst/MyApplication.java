package com.yhm.wst;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.yhm.wst.database.Helper;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.yhm.wst.util.CrashHandler;

/**
 * Created by liang on 2017/11/28.
 */
public class MyApplication extends MultiDexApplication {
    private static MyApplication mApplication;
    private Helper mHelper;


    public static MyApplication getApplication() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(this);
        initFresco();
        setDatabase();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        //mHelper = new Helper(new GreenDaoUtils(this));
        mHelper = new Helper(this);
    }

    private void initFresco() {
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this).
                setMaxCacheSize(100 * ByteConstants.MB).build();

        Supplier<MemoryCacheParams> bitmapCacheParamsSupplier = new Supplier<MemoryCacheParams>() {

            @Override
            public MemoryCacheParams get() {
                MemoryCacheParams param = new MemoryCacheParams(
                        20*ByteConstants.MB
                        ,Integer.MAX_VALUE
                        ,20*ByteConstants.MB
                        ,Integer.MAX_VALUE
                        ,Integer.MAX_VALUE);
                return param;
            }
        };
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setBitmapMemoryCacheParamsSupplier(bitmapCacheParamsSupplier)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .build();
        Fresco.initialize(this,config);
    }
}
