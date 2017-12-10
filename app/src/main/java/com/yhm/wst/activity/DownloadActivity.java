package com.yhm.wst.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yhm.wst.BaseActivity;
import com.yhm.wst.R;
import com.yhm.wst.http.ProgressDownloader;
import com.yhm.wst.http.ProgressResponseBody;
import com.yhm.wst.util.CommonUtils;
import com.yhm.wst.util.FileUtils;
import com.yhm.wst.util.LogUtil;

import java.io.File;

/**
 * Created by Administrator on 2016/10/24.
 */
public class DownloadActivity extends BaseActivity implements ProgressResponseBody.ProgressListener, View.OnClickListener {

    public static final String TAG = "DownloadActivity";
//    public static final String PACKAGE_URL = "http://imgs.huafer.cc/download/huafer.apk";
    ProgressBar progressBar;
    private long breakPoints;
    private ProgressDownloader downloader;
    private File file;
    private long totalBytes;
    private long contentLength;
    private TextView tvDialogContent, tvDialogTitle;
    private Button btnDialogLeft, btnDialogRight;
    private String appDownloadUrl;
    private String appContent;
    private String appLevel;
    private boolean isDownload = false;


    @Override
    public void initParams(Bundle params) {
        if(params != null) {
//            appLevel = params.getString(MyConstants.EXTRA_APP_LEVEL);
//            appDownloadUrl = params.getString(MyConstants.EXTRA_APP_URL);
//            appContent = params.getString(MyConstants.EXTRA_APP_TIP);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_download;
    }

    @Override
    public void initView(View view) {
        tvDialogContent = (TextView) findViewById(R.id.tvDialogContent);
        tvDialogTitle = (TextView) findViewById(R.id.tvDialogTitle);
        btnDialogLeft = (Button) findViewById(R.id.btnDialogLeft);
        btnDialogRight = (Button) findViewById(R.id.btnDialogRight);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvDialogContent.setMovementMethod(new ScrollingMovementMethod());
        btnDialogLeft.setOnClickListener(this);
        btnDialogRight.setOnClickListener(this);

        if(appLevel.equals("2")) {
            btnDialogLeft.setVisibility(View.GONE);
        }
        tvDialogContent.setText(appContent);
        tvDialogTitle.setText("版本更新");
        btnDialogLeft.setText("取消");
        btnDialogRight.setText("确定");
    }

    @Override
    public void setOnClickListener() {

    }

    @Override
    public void initData(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.btnDialogLeft:
                finish();
                break;
            case R.id.btnDialogRight:
                if(!isDownload) {
                    if(TextUtils.isEmpty(appDownloadUrl)) {
                        tvDialogContent.setText("下载地址异常，请返回重试。");
                        return;
                    }
                    isDownload = true;
                    tvDialogTitle.setText("下载中");
                    progressBar.setVisibility(View.VISIBLE);
                    tvDialogContent.setVisibility(View.GONE);
                    btnDialogLeft.setVisibility(View.GONE);
                    // 新下载前清空断点信息
                    breakPoints = 0L;
                    if(FileUtils.isSDCardExist()) {
                        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "yhm.apk");
                        if(file.exists()) {
                            file.delete();
                        }
                        downloader = new ProgressDownloader(appDownloadUrl, file, this);
                        downloader.download(0L);
                    } else {
                        showToast("请安装SD卡");
                        finish();
                    }
                } else {
                    showToast("下载中，请不要重复点击");
                }
                break;
        }
    }

    @Override
    public void onPreExecute(long contentLength) {
        // 文件总长只需记录一次，要注意断点续传后的contentLength只是剩余部分的长度
        if(contentLength <= 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    tvDialogContent.setVisibility(View.VISIBLE);
                    btnDialogRight.setVisibility(View.GONE);
                    btnDialogLeft.setVisibility(View.VISIBLE);
                    btnDialogLeft.setText("关闭");
                    tvDialogContent.setText("文件已被损坏。");
                }
            });
            return;
        }
        if (this.contentLength == 0L) {
            this.contentLength = contentLength;
            progressBar.setMax((int) (contentLength / 1024));
        }
    }

    @Override
    public void update(long totalBytes, boolean done) {
        // 注意加上断点的长度
        this.totalBytes = totalBytes + breakPoints;
        LogUtil.e("liang", "total:"+(totalBytes + breakPoints));
        LogUtil.e("liang", "done:"+done);
        progressBar.setProgress((int) (totalBytes + breakPoints) / 1024);
        if (done) {//开始安装
            if(file != null && file.isFile()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                startActivity(intent);
            } else {
                // 切换到主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CommonUtils.toast(DownloadActivity.this, "文件异常，请退出重试");
                    }
                });
            }
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if(!appLevel.equals("2")) {
            super.onBackPressed();
        }
    }
}
