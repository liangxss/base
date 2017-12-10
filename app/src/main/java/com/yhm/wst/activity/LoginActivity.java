package com.yhm.wst.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.yhm.wst.ActivityManager;
import com.yhm.wst.BaseActivity;
import com.yhm.wst.MyConstants;
import com.yhm.wst.R;
import com.yhm.wst.dialog.ProgressDialog;
import com.yhm.wst.hprose.HProseManager;
import com.yhm.wst.util.CommonPreference;
import com.yhm.wst.util.CommonUtils;
import com.yhm.wst.util.JsonValidator;
import com.yhm.wst.util.LogUtil;
import com.yhm.wst.view.ClearEditText;

/**
 * Created by liang on 2017/11/28.
 */
public class LoginActivity extends BaseActivity {
    private ClearEditText cetName;
    private ClearEditText cetNo;
    private ClearEditText cetPassword;
    private ImageView ivSave;
    private Button btnLogin;
    private boolean isSave = false;


    @Override
    public void initParams(Bundle params) {
        isSave = CommonPreference.getBooleanValue(CommonPreference.USER_SAVE, false);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(View view) {
        setTitleString("登录");
        getTitleBar().setLeftText("", null);
        cetName = find(R.id.cetName);
        cetNo = find(R.id.cetNo);
        cetPassword = find(R.id.cetPassword);
        ivSave = find(R.id.ivSave);
        btnLogin = find(R.id.btnLogin);
    }


    @Override
    public void setOnClickListener() {
        ivSave.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void initData(Context mContext) {
        if(isSave) {
            ivSave.setSelected(true);
            ivSave.setImageResource(R.mipmap.icon_checkbox_login_select);
            isSave = true;
            cetName.setText(CommonPreference.getName());
            cetNo.setText(CommonPreference.getUserNo());
            cetPassword.setText(CommonPreference.getPassWord());
        } else {
            ivSave.setSelected(false);
            ivSave.setImageResource(R.mipmap.icon_checkbox_login_nor);
            isSave = false;
        }
    }


    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.ivSave:
                if (isSave) {
                    ivSave.setSelected(false);
                    ivSave.setImageResource(R.mipmap.icon_checkbox_login_nor);
                    isSave = false;
                } else {
                    ivSave.setSelected(true);
                    ivSave.setImageResource(R.mipmap.icon_checkbox_login_select);
                    isSave = true;
                }
                break;
            case R.id.btnLogin:
                login();
                break;
        }
    }

    private void login(){
        ProgressDialog.showProgress(this, true);
        final String name = cetName.getText().toString().trim();
        final String no = cetNo.getText().toString().trim();
        final String password = cetPassword.getText().toString().trim();
        long time = 60 * 60 * 24;
        Object[] params = new Object[] {name, no, CommonUtils.MD5(password), String.valueOf(time), CommonUtils.getAppVersionName(), 4};
        HProseManager.postAsyn(MyConstants.GOODS, "login", params, new HProseManager.StringCallback() {
            @Override
            public void onError(String s, Throwable throwable) {
                LogUtil.i("liang", "login: " + throwable.toString());
                ProgressDialog.closeProgress();
                CommonUtils.HPError(LoginActivity.this, throwable);
            }

            @Override
            public void onResponse(String json, Object[] objects) {
                LogUtil.i("liang", "login: " + json);
                ProgressDialog.closeProgress();
                JsonValidator jsonValidator = new JsonValidator();
                if(!jsonValidator.validate(json)) {
                    showToast(getString(R.string.not_json));
                    return;
                }
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        ActivityManager.finishAllActivities();
        super.onBackPressed();
    }
}
