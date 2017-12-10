package com.yhm.wst.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.yhm.wst.ActivityManager;
import com.yhm.wst.BaseActivity;
import com.yhm.wst.R;
import com.yhm.wst.util.CommonPreference;
import com.yhm.wst.util.ImageUtils;

/**
 * 导航页
 * @author liang_xs
 *
 */
public class GuideActivity extends BaseActivity {

	private ViewPager mViewPager;
	private int[] imgRes;
	private ImageView[] views;
	private Button experience;
	private String extraMap;
	/** 点点 */
//	private ImageView[] dots;
	/** 滑动点点所在布局 */
//	private LinearLayout container;


	@Override
	public void initParams(Bundle params) {

	}

	@Override
	public int bindLayout() {
		return R.layout.activity_guide;
	}

	@Override
	public void initView(View view) {
		mViewPager = (ViewPager) findViewById(R.id.first_viewpager);
		imgRes = new int[] { R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3, R.mipmap.guide4};
		views = new ImageView[imgRes.length];
		for (int i = 0; i < imgRes.length; i++) {
			ImageView image = new ImageView(this);
			// 避免OOM
			ImageUtils.showImage(GuideActivity.this, image, imgRes[i]);
			views[i] = image;
		}
//		container = (LinearLayout) findViewById(R.id.container);
		experience = (Button) findViewById(R.id.immediately_experience);
		mViewPager.setAdapter(new GuidePagerAdapter());
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				for (int i = 0; i < views.length; i++) {
					if (arg0 == views.length - 1) {
						views[arg0].setOnClickListener(new goMainListener());
					} else {
						views[i].setOnClickListener(null);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	@Override
	public void setOnClickListener() {

	}

	@Override
	public void initData(Context mContext) {

	}

	@Override
	public void widgetClick(View v) {

	}

	private class goMainListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			startActivity(MainActivity.class);
		}

	}

	private class GuidePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return views.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView(views[position]);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((ViewPager) container).addView(views[position]);
			return views[position];
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		ActivityManager.finishAllActivities();
		super.onBackPressed();
	}
}