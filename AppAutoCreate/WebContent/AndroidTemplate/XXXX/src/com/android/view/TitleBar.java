package com.android.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.utils.ViewUtil;

public class TitleBar extends RelativeLayout{

	public ImageView leftImg;//左边ogo 或者是返回按钮
	public TextView title;   //主标题
	public TextView miniTitle; //小标题
	public RelativeLayout rightlLayout; //右边布局
	public TitleBar(Context context) {
		super(context);
		init(context);
	}
	
	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public void init(Context context){
		int dip_50 = ViewUtil.dip2px(getContext(), 50);
		int dip_10 = ViewUtil.dip2px(getContext(), 10);
		int dip_5 = ViewUtil.dip2px(getContext(), 5);
		int dip_2 = ViewUtil.dip2px(getContext(), 2);
		
		RelativeLayout.LayoutParams params = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		this.setLayoutParams(params);
		
		RelativeLayout.LayoutParams leftimgParam = new RelativeLayout.LayoutParams(dip_50, dip_50);
		leftimgParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT|CENTER_VERTICAL);
		leftimgParam.setMargins(dip_5, 0, 0, 0);
		leftImg = new ImageView(context);
		leftImg.setId(1);
		this.addView(leftImg, leftimgParam);
		
		
		RelativeLayout.LayoutParams titleParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		titleParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
		titleParam.setMargins(0, dip_5, 0, dip_5);
		title = new TextView(context);
		title.setTextColor(Color.WHITE);
		title.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
		title.setId(2);
		this.addView(title, titleParam);
		
		
		RelativeLayout.LayoutParams rightParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, dip_50);
		rightParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
		rightParam.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
		rightParam.setMargins(0, 0, 0, dip_5);
		rightlLayout = new RelativeLayout(context);
		rightlLayout.setMinimumWidth(dip_50);
		rightlLayout.setId(3);
		this.addView(rightlLayout, rightParam);
		
		
		RelativeLayout.LayoutParams miniTitleParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		miniTitleParam.addRule(RelativeLayout.BELOW,title.getId());
		miniTitleParam.addRule(RelativeLayout.LEFT_OF,rightlLayout.getId());
		miniTitleParam.setMargins(0, dip_2, 0, dip_2);
		miniTitle = new TextView(context);
		miniTitle.setTextColor(Color.WHITE);
		miniTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
		miniTitle.setId(4);
		this.addView(miniTitle, miniTitleParam);
		
		
		
	}
	
	public void setTitleText(CharSequence text){
		title.setText(text);
	}

}
