package com.yhm.wst.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhm.wst.BaseFragment;
import com.yhm.wst.R;
import com.yhm.wst.activity.LoginActivity;
import com.yhm.wst.dialog.DialogCallback;
import com.yhm.wst.dialog.TextDialog;
import com.yhm.wst.util.CommonPreference;
import com.yhm.wst.util.CommonUtils;


/**
 * Created by liang_xs on 2017/11/28.
 * 我的Fragment
 */

public class MineFragment extends BaseFragment implements View.OnClickListener{
    private ImageView ivSetting;
    private TextView tvName;
    private TextView tvVersion;


    @Override
    public int bindLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(View view) {
        ivSetting = find(R.id.ivSetting);
        tvName = find(R.id.tvName);
        tvVersion = find(R.id.tvVersion);

        ivSetting.setOnClickListener(this);
    }

    @Override
    public void initData(Context mContext) {
        tvName.setText(CommonPreference.getUserName());
        tvVersion.setText("版本号：" + CommonUtils.getAppVersionName());
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.ivSetting:
                if(TextUtils.isEmpty(CommonPreference.getUserToken())) {
                    startActivity(LoginActivity.class);
                } else {
                    final TextDialog textDialog = new TextDialog(getActivity());
                    textDialog.setContentText("确认退出登录？");
                    textDialog.setLeftText("取消");
                    textDialog.setLeftCall(new DialogCallback() {
                        @Override
                        public void Click() {
                            textDialog.dismiss();
                        }
                    });
                    textDialog.setRightText("确定");
                    textDialog.setRightCall(new DialogCallback() {
                        @Override
                        public void Click() {
                            CommonPreference.setUserToken("");
                            startActivity(LoginActivity.class);
                            textDialog.dismiss();
                        }
                    });
                    textDialog.show();
                }
            break;
        }
    }
}
