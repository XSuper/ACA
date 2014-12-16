package com.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;

import com.android.base.BaseActivity;
import com.android.pay.SMSPay;
import com.android.xxxx.R;

public class WelcomeActivity extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//请求短信
		SMSPay.getInstance(this).requestSMS();
		//
		LinearLayout view = new LinearLayout(this);
		view.setBackgroundResource(R.drawable.welcome);
		setContentView(view);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f,1f);
		alphaAnimation.setDuration(1000);
		view.setAnimation(alphaAnimation);
		alphaAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getBaseContext(),MainActivity.class);
				startActivity(intent);
				finish();
				
			}
		});
		
	}

}
