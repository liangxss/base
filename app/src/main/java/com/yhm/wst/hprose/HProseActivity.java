//package com.embarcadero.YHM_APP.hprose;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//
//import com.embarcadero.YHM_APP.BaseActivity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * Created by Administrator on 2017/11/21.
// */
//public class HProseActivity extends BaseActivity {
//
//    TextView textView;
//    TextView tv;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_hprose);
//
//        tv = (TextView) findViewById(R.id.tv);
//        textView = (TextView) findViewById(R.id.textView);
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                init();
//            }
//        });
//        initTickle();
//    }
//
//    @Override
//    public void initParams(Bundle params) {
//
//    }
//
//    @Override
//    public int bindLayout() {
//        return 0;
//    }
//
//    @Override
//    public void initView(View view) {
//
//    }
//
//    @Override
//    public void initData(Context mContext) {
//
//    }
//
//    @Override
//    public void setOnClickListener() {
//
//    }
//
//    @Override
//    public void widgetClick(View v) {
//
//    }
//
//    private void initTickle() {
//        Object[] params = new Object[] {};
//        // 在此处添加执行的代码
//        HProseManager.postAsyn(AppConst.BASE_URL + "login.php/", "tickle", params, new HProseManager.StringCallback() {
//            @Override
//            public void onError(String s, Throwable throwable) {
//                LogUtil.i("liang-tickle", throwable.toString());
//            }
//
//            @Override
//            public void onResponse(String json, Object[] objects) {
//                LogUtil.i("liang-tickle", "tickle: " + json);
//            }
//        });
//    }
//
//    private void init() {
//        List<Object> paramlist = new ArrayList<Object>();
//        List<Object> titlelist = new ArrayList<Object>();
//        titlelist.add("tablename");
//        titlelist.add("edittime");
//        paramlist.add(titlelist);
//        List<Object> infolist = new ArrayList<Object>();
//        infolist.add("productinfo");
//        infolist.add("1403000000");
//        paramlist.add(infolist);
//        List<Object> mullist = new ArrayList<Object>();
//        mullist.add("productmulbarcode");
//        mullist.add("1403000000");
//        paramlist.add(mullist);
//        Object[] params = new Object[] {paramlist};
//        HProseManager.postAsyn("http://test1.1haomei.com/SyncTable.php/", "getAppSync", params, new HProseManager.StringCallback() {
//            @Override
//            public void onError(String s, Throwable throwable) {
//
//            }
//
//            @Override
//            public void onResponse(String json, Object[] objects) {
//                Gson gson = new Gson();
//                HProseManager.Result result= gson.fromJson(json, HProseManager.Result.class);
//                int error = result.error;
//                String err_msg = result.err_msg;
//                ArrayList<HProseManager.AppSync> list = result.result;
//                textView.setText(list.toString());
//                Log.i("liang", list.get(0).rowcount);
//            }
//        });
//    }
//}
