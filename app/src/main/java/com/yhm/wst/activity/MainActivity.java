package com.yhm.wst.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yhm.wst.ActivityManager;
import com.yhm.wst.BaseActivity;
import com.yhm.wst.R;
import com.yhm.wst.fragment.CartFragment;
import com.yhm.wst.fragment.ClassFragment;
import com.yhm.wst.fragment.HomeFragment;
import com.yhm.wst.fragment.MineFragment;
import com.yhm.wst.fragment.OperationFragment;
import com.yhm.wst.service.TickleService;
import com.yhm.wst.util.CommonPreference;
import com.yhm.wst.util.CommonUtils;
import com.umeng.analytics.MobclickAgent;
import com.yhm.wst.util.LogUtil;

public class MainActivity extends BaseActivity {
    private HomeFragment homeFragment = new HomeFragment();
    private ClassFragment classFragment = new ClassFragment();
    private OperationFragment operationFragment = new OperationFragment();
    private CartFragment cartFragment = new CartFragment();
    private MineFragment mineFragment = new MineFragment();
    private View tabHome, tabClass, tabOperation, tabCart, tabMine, currSelectedTab;
    private TextView tvTabHome, tvTabClass, tvTabOperation, tvTabCart, tvTabMine;
    private Fragment currFragment;
    private Intent intent;


    @Override
    public void initParams(Bundle params) {
        CommonPreference.setBooleanValue(CommonPreference.IS_FIRST, false);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        tabHome = findViewById(R.id.tabHome);
        tabClass = findViewById(R.id.tabClass);
        tabOperation = findViewById(R.id.tabOperation);
        tabCart = findViewById(R.id.tabCart);
        tabMine = findViewById(R.id.tabMine);

        tvTabHome = (TextView) findViewById(R.id.tvTabHome);
        tvTabClass = (TextView) findViewById(R.id.tvTabClass);
        tvTabOperation = (TextView) findViewById(R.id.tvTabOperation);
        tvTabCart = (TextView) findViewById(R.id.tvTabCart);
        tvTabMine = (TextView) findViewById(R.id.tvTabMine);
    }

    @Override
    public void initData(Context mContext) {
        int action = 0;
        switch (action) {
            case 0:
                break;
            case 1:
                tabHome.setVisibility(View.GONE);
                tabClass.setVisibility(View.GONE);
                tabCart.setVisibility(View.GONE);
                break;

        }
        selectHomeFragment();
    }

    @Override
    public void setOnClickListener() {
        tabHome.setOnClickListener(this);
        tabClass.setOnClickListener(this);
        tabOperation.setOnClickListener(this);
        tabCart.setOnClickListener(this);
        tabMine.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tabHome:
                selectFragment(homeFragment);
                break;

            case R.id.tabClass:
                selectFragment(classFragment);
                break;

            case R.id.tabOperation:
                selectFragment(operationFragment);
                break;
            case R.id.tabCart:
                selectFragment(cartFragment);
                break;
            case R.id.tabMine:
                selectFragment(mineFragment);
                break;
        }
        selectView(v);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(intent != null) {
            stopService(intent);
        }
    }

    public void selectHomeFragment(){
        selectFragment(operationFragment);
        selectView(tabOperation);
    }

    private void selectFragment(Fragment fragment) {
        if (currFragment == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragmentContainer, fragment).commit();
            currFragment = fragment;
        } else {
            if (currFragment != fragment) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (!fragment.isAdded()) {
                    transaction.hide(currFragment).add(R.id.fragmentContainer, fragment).commitAllowingStateLoss();
                } else {
                    transaction.hide(currFragment).show(fragment).commitAllowingStateLoss();
                }
                currFragment = fragment;
            }
        }
    }


    private void selectView(View v) {
        if (currSelectedTab != null) {
            currSelectedTab.setSelected(false);
        }
        v.setSelected(true);
        currSelectedTab = v;
        if (v == tabHome) {
            tvTabHome.setSelected(true);
            tvTabClass.setSelected(false);
            tvTabOperation.setSelected(false);
            tvTabCart.setSelected(false);
            tvTabMine.setSelected(false);
        } else if (v == tvTabClass) {
            tvTabHome.setSelected(false);
            tvTabClass.setSelected(true);
            tvTabOperation.setSelected(false);
            tvTabCart.setSelected(false);
            tvTabMine.setSelected(false);
        } else if (v == tvTabOperation) {
            tvTabHome.setSelected(false);
            tvTabClass.setSelected(false);
            tvTabOperation.setSelected(true);
            tvTabCart.setSelected(false);
            tvTabMine.setSelected(false);
        } else if (v == tvTabCart) {
            tvTabHome.setSelected(false);
            tvTabClass.setSelected(false);
            tvTabOperation.setSelected(false);
            tvTabCart.setSelected(true);
            tvTabMine.setSelected(false);
        } else if (v == tvTabMine) {
            tvTabHome.setSelected(false);
            tvTabClass.setSelected(false);
            tvTabOperation.setSelected(false);
            tvTabCart.setSelected(false);
            tvTabMine.setSelected(true);
        }
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    private long exitTime = 0;
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            CommonUtils.toast(this, "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            MobclickAgent.onKillProcess(this);
            ActivityManager.finishAllActivities();
            System.exit(0);
        }
    }
}
