package com.yhm.wst.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.yhm.wst.R;

/**
 * @ClassName: SyncProgressDialog
 * @Description: 网络请求等待提示
 * @author liang_xs
 * @date 2016-03-27
 */
public class SyncProgressDialog extends Dialog {
	private ProgressBar progressBar;

	public SyncProgressDialog(Context context) {
		this(context, true);
	}

	public SyncProgressDialog(Context context, boolean isCancel) {
		super(context, R.style.DialogProgress);
		setCancelable(isCancel);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_sync_progress);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
	}

	public void setProgress(int progress){
		progressBar.setProgress(progress);
	}
	public void setMaxProgress(int max){
		progressBar.setMax(max);
	}
}
