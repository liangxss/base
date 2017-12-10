package com.yhm.wst.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yhm.wst.R;
import com.yhm.wst.util.CommonUtils;

public class PublishBottomPopupWindow extends PopupWindow implements OnClickListener, View.OnFocusChangeListener {
	private View blankSpace;
	private Context mContext;
	private TextView tvBtnConfirm;
	private OnDataReachListener mListener;
	private EditText etRealQuantity;
	private TextView tvName, tvBarcode, tvStoreCode, tvUnits, tvSpec;

	public PublishBottomPopupWindow(Context context, View root, OnDataReachListener l){
		this.mContext=context;
		this.mListener =l;
		initView();
	}


	private void initView(){
		View view = LayoutInflater.from(mContext).inflate(R.layout.pop_publish_bottom, null);
		setContentView(view);
		view.getBackground().setAlpha(150);
		etRealQuantity = (EditText) view.findViewById(R.id.etRealQuantity);
		tvBtnConfirm = (TextView)view. findViewById(R.id.tvBtnConfirm);
		tvName = (TextView)view. findViewById(R.id.tvName);
		tvBarcode = (TextView)view. findViewById(R.id.tvBarcode);
		tvStoreCode = (TextView)view. findViewById(R.id.tvStoreCode);
		tvUnits = (TextView)view. findViewById(R.id.tvUnits);
		tvSpec = (TextView)view. findViewById(R.id.tvSpec);
		tvBtnConfirm.setOnClickListener(this);
		blankSpace=view.findViewById(R.id.blankSpace);
		blankSpace.setOnClickListener(this);
		setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
		etRealQuantity.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tvBtnConfirm:
				if(TextUtils.isEmpty(etRealQuantity.getText().toString().trim())) {
					CommonUtils.toast(mContext,"请输入数量");
					return;
				}
				if(mListener !=null){
					mListener.onConfirmButtonClicked(etRealQuantity.getText().toString().trim());
				}
				etRealQuantity.getText().clear();
				CommonUtils.hideKeyBoard(mContext);
				dismiss();
				break;

			case R.id.blankSpace:
				if(mListener !=null){
					mListener.onBlankSpaceClicked();
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {

	}


	public interface OnDataReachListener{
		void onConfirmButtonClicked(String realQuantity);
		void onBlankSpaceClicked();
	}

	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		super.showAtLocation(parent, gravity, x, y);
		etRealQuantity.setOnFocusChangeListener(this);
	}

	@Override
	public void dismiss() {
		super.dismiss();
		etRealQuantity.setOnFocusChangeListener(null);
	}
}
