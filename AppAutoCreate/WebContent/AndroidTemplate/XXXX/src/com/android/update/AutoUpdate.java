package com.android.update;

import java.io.File;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Environment;
import android.util.Log;

import com.android.utils.AppUtil;
import com.android.view.Dialog;
import com.android.view.Dialog.DialogClickListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class AutoUpdate {
	public final static String updateUrl = "";

	public int version;
	public String packagename;
	public Context context;

	public AutoUpdate(Context context) {
		PackageInfo packageInfo = AppUtil.getPackageInfo(context);
		version = packageInfo.versionCode;
		packagename = packageInfo.packageName;

		this.context = context;
	}

	public void checkUpdate() {
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET, updateUrl, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {

			}
		});

		Dialog.showSelectDialog(context, "更新提示", "发现新版本，是否立即更新",
				new DialogClickListener() {
					@Override
					public void confirm() {
						downloadApk("http://www.golfeven.cn/mobileapi/appdownload.php?platform=android&name=golfeven");
					}
					@Override
					public void cancel() {
					}
				});
	}

	public void downloadApk(String url) {
		final ProgressDialog dialog = new ProgressDialog(context);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setTitle("软件更新中");
		dialog.show();
		dialog.setCancelable(false);
		HttpUtils download = new HttpUtils();
		String filePath = Environment.getExternalStorageDirectory() + "/"
				+ packagename + "/app/update.apk";

		HttpHandler<File> httpHandler = download.download(url, filePath, false,
				false, new RequestCallBack<File>() {
					@Override
					public void onSuccess(ResponseInfo<File> responseInfo) {
						Log.v("file-path", responseInfo.result.getPath());
						AppUtil.installApk(context, responseInfo.result);
						dialog.dismiss();
					}
					public void onFailure(HttpException arg0, String arg1) {

					}
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						dialog.setProgress((int) (current * 100 / total));

					}

				});
	}
}
