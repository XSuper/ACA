package com.android.ui;

import java.util.ArrayList;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.android.base.ActivityTask;
import com.android.base.BaseActivity;
import com.android.bean.VideoType;
import com.android.update.AutoUpdate;
import com.android.utils.PropertiesUtil;
import com.android.utils.ViewUtil;
import com.android.view.Dialog;
import com.android.view.Dialog.DialogClickListener;
import com.android.xxxx.R;
import com.slidingmenu.lib.SlidingMenu;

public class MainActivity extends BaseActivity implements
		OnCheckedChangeListener {

	SlidingMenu menu;
	RadioGroup menuGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewWithTitleBar(R.layout.main_activity);

		AutoUpdate update = new AutoUpdate(this);
		update.checkUpdate();

		titleBar.setBackgroundColor(PropertiesUtil.getInstance(this).themeColor);
		titleBar.setTitleText(PropertiesUtil.getInstance(this).name);
		// titleBar.miniTitle.setText("这是副标题");
		titleBar.leftImg.setImageResource(R.drawable.menu1);
		titleBar.leftImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.toggle();
			}
		});
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setFadeDegree(0.35f);
		menu.setBehindWidthRes(R.dimen.menu_width);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.menu);
		menuGroup = (RadioGroup) menu.findViewById(R.id.menu_radiogroup);
		ArrayList<VideoType> types = PropertiesUtil.getInstance(this).types;

		int dip_50 = ViewUtil.dip2px(this, 50);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, dip_50);

		int[] colors = new int[] {
				PropertiesUtil.getInstance(this).themeColor, Color.WHITE };
		int[][] states = new int[2][];
		states[0] = new int[] { android.R.attr.state_checked };
		states[1] = new int[] {};
		ColorStateList color = new ColorStateList(states, colors);
		
		
		for (int i = 0; i < types.size(); i++) {
			VideoType videoType = types.get(i);
			RadioButton button = new RadioButton(this);
			button.setText(videoType.getName());
			button.setTag(videoType.getId());
			button.setButtonDrawable(R.drawable.transparent_bg);
			button.setTextColor(color);
			menuGroup.addView(button, params);
			if(i==0){
				button.setChecked(true);
			}
		}
//		for (VideoType videoType : types) {
//			RadioButton button = new RadioButton(this);
//			button.setText(videoType.getName());
//			button.setTag(videoType.getId());
//			button.setButtonDrawable(R.drawable.transparent_bg);
//			button.setTextColor(color);
//			menuGroup.addView(button, params);
//		}
		menuGroup.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		RadioButton rb = (RadioButton) group.findViewById(checkedId);
		showToast(rb.getText().toString() + "---" + rb.getTag());
		menu.showContent();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (menu.isMenuShowing()) {

			Dialog.showSelectDialog(this, "确定退出应用吗", new DialogClickListener() {
				
				@Override
				public void confirm() {
					ActivityTask.getActivityTask().AppExit(
							getApplicationContext());
				}
				
				@Override
				public void cancel() {
					
				}
			});
		} else {
			menu.showMenu();
		}
	}

}
