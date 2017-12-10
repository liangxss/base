package com.yhm.wst.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.yhm.wst.BaseActivity;
import com.yhm.wst.R;
import com.yhm.wst.service.TickleService;
import com.yhm.wst.util.CommonPreference;
import com.yhm.wst.util.LogUtil;


public class SplashActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    public final static int REQUEST_READ_PHONE_STATE = 1;
    private Animation animation;
    private View layoutAd;

    @Override
    public void initParams(Bundle params) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(View view) {
        layoutAd = find(R.id.layoutAd);
    }


    private void initAnim() {
        //渐变
        animation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.animation_ad_alpha);
        layoutAd.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                gotoHome();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    @Override
    public void initData(Context mContext) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){//判断版本号是否大于23
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
            } else {
                initAnim();
            }
        } else {
            initAnim();
        }
    }

    @Override
    public void setOnClickListener() {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    private void gotoHome() {
        boolean isFirstRunLead = CommonPreference.getBooleanValue(CommonPreference.IS_FIRST, true);
        if (isFirstRunLead) {
            startActivity(GuideActivity.class);
        } else {
            String token = CommonPreference.getUserToken();
            LogUtil.i("token", "token:" + token);
            if(TextUtils.isEmpty(token)) {
                startActivity(LoginActivity.class);
            } else {
                startActivity(MainActivity.class);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        gotoHome();
    }
}
