package com.yhm.wst.fragment;

import android.content.Context;
import android.view.View;

import com.yhm.wst.BaseFragment;
import com.yhm.wst.R;


/**
 * Created by liang_xs on 2017/8/9.
 * 购物车Fragment
 */

public class CartFragment extends BaseFragment implements View.OnClickListener{


    @Override
    public int bindLayout() {
        return R.layout.fragment_cart;
    }

    @Override
    public void initView(View view) {
    }

    @Override
    public void initData(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {
    }
}
