package com.android.control;


public class MobileApi {

	private static MobileApi mobileApi;

	private MobileApi() {

	}

	public static MobileApi getInstance() {
		if (mobileApi == null) {
			mobileApi = new MobileApi();
		}
		return mobileApi;
	}

	private static final String BASEURL = "http://api.dandanchina.com/rest/v1/";

	/**
	 * 2.1 用户登录验证 Verify 已测试
	 * 
	 * @param context
	 * @param simpleCallback
	 */
//	public void Verify(Context context,
//			final SimpleRequestCallBack<VerifyInfo> simpleCallback) {
//		String v = AppUtil.getPackageInfo(context).versionName;
//		String url = BASEURL + "verify/Android/" + v;
//		DataController controller = new DataController(url, null, 0l,
//				HttpMethod.GET);
//		controller.getDateFromNet(NetCachePolicy.POLICY_NOCACHE,
//				new DataRequestCallBack(context) {
//
//					@Override
//					public void onSuccess(Response response) {
//						VerifyInfo info = response
//								.modelFromData(VerifyInfo.class);
//						simpleCallback.onSuccess(info, response);
//					}
//
//					@Override
//					public void onFailure() {
//						simpleCallback.onFailure();
//					}
//				});
//
//	}

	
}
