package com.yhm.wst.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.yhm.wst.BaseFragment;
import com.yhm.wst.R;
import com.yhm.wst.activity.PrtListActivity;
import com.yhm.wst.util.CommonPreference;


/**
 * Created by liang_xs on 2017/8/9.
 * 首页运营Fragment
 */

public class OperationFragment extends BaseFragment implements View.OnClickListener{

    private View mGoToOperation;
    private View mGoToStockCheck;
    private View mGoToStockQuery;

    @Override
    public int bindLayout() {
        return R.layout.fragment_operation;
    }

    @Override
    public void initView(View view) {
        mGoToOperation = view.findViewById(R.id.goto_operation);
        mGoToStockCheck = view.findViewById(R.id.goto_stock_check);
        mGoToStockQuery = view.findViewById(R.id.goto_stock_query);

        mGoToOperation.setOnClickListener(this);
        mGoToStockCheck.setOnClickListener(this);
        mGoToStockQuery.setOnClickListener(this);
    }

    @Override
    public void initData(Context mContext) {
        boolean backDecisionDescreport = CommonPreference.getBackDecisionDescreportAction();
        if(backDecisionDescreport) {
            mGoToOperation.setVisibility(View.VISIBLE);
        } else {
            mGoToOperation.setVisibility(View.GONE);
        }

    }

    @Override
    public void widgetClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.goto_operation:
                break;
            case  R.id.goto_stock_check:
                intent = new Intent(getActivity(), PrtListActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            // 跳转到库存查询页面
            case R.id.goto_stock_query:
                break;
        }
    }
}
