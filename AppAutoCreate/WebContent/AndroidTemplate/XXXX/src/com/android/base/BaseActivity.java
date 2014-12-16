package com.android.base;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.utils.ViewUtil;
import com.android.view.TitleBar;

public class BaseActivity extends FragmentActivity {
	public LayoutInflater mInflater;
	public View Layout_base;
	public RelativeLayout contentLayout;
	public LayoutParams layoutParamsFF;
	public LayoutParams layoutParamsFW;
	public LayoutParams layoutParamsWF;
	public LayoutParams layoutParamsWW;

	public TitleBar titleBar;
	/* progress 相关 */
	ProgressDialog dialog;

	private int focusCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mInflater = LayoutInflater.from(this);

		layoutParamsFF = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layoutParamsFW = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWF = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		layoutParamsWW = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		ActivityTask.getActivityTask().addActivity(this);
	}

	
	//添加友盟统计
	
	public void onResume() {
		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ActivityTask.getActivityTask().finishActivity(this);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if (focusCount == 0) {
			onActivityLoad();
		}
		focusCount++;
	}

	public void onActivityLoad() {
		// TODO Auto-generated method stub

	}

	// 为了得到Layout_base
	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		setContentView(mInflater.inflate(layoutResID, null));

	}

	@Override
	public void setContentView(View view) {
		// TODO Auto-generated method stub
		super.setContentView(view);
		Layout_base = (ViewGroup) view;
	}

	@Override
	public void setContentView(View view,
			android.view.ViewGroup.LayoutParams params) {
		// TODO Auto-generated method stub
		super.setContentView(view, params);
		Layout_base = (ViewGroup) view;
	}

	/**
	 * activity显示titlebar
	 * 
	 * @param layoutResID
	 */
	public void setContentViewWithTitleBar(int layoutResID) {
		setContentViewWithTitleBar(mInflater.inflate(layoutResID, null));
	}

	public void setContentViewWithTitleBar(View contentView) {
		
		// 最外层布局
		RelativeLayout Layout_base = new RelativeLayout(this);
		Layout_base.setBackgroundColor(Color.rgb(255, 255, 255));
		// 内容布局
		contentLayout = new RelativeLayout(this);
		contentLayout.setPadding(0, 0, 0, 0);

		contentLayout.removeAllViews();
		contentLayout.addView(contentView, layoutParamsFF);


		titleBar = new TitleBar(this);
		
		// 填入View
		Layout_base.addView(titleBar, layoutParamsFW);

		RelativeLayout.LayoutParams layoutParamsFW1 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParamsFW1.addRule(RelativeLayout.BELOW, titleBar.getId());
		Layout_base.addView(contentLayout, layoutParamsFW1);

		// 设置ContentView
		setContentView(Layout_base, layoutParamsFF);
	}

	/**
	 * 
	 * @return 是否成功显示
	 */
	public boolean showTitleProgress(boolean show) {
		if (titleBar == null) {
			return false;
		}
		if (show) {
			titleBar.rightlLayout.removeAllViews();
			ProgressBar progressBar = new ProgressBar(this);
			float w = ViewUtil.dip2px(this, 45);
			android.view.ViewGroup.LayoutParams params = new LayoutParams(
					(int) w, (int) w);
			progressBar.setLayoutParams(params);
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			titleBar.rightlLayout.addView(progressBar);
			titleBar.rightlLayout.setVisibility(View.VISIBLE);
		} else {
			titleBar.rightlLayout.removeAllViews();
		}
		return true;
	}

	public void showToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_LONG).show();
	}

	public void showProgressDialog(boolean show) {
		if (dialog == null) {
			dialog = new ProgressDialog(this);
			dialog.setMessage("正在加载");
		}
		if (show) {
			dialog.show();
		} else {
			dialog.dismiss();
		}
	}
}
