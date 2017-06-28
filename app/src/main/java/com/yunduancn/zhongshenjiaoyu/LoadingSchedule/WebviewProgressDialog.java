package com.yunduancn.zhongshenjiaoyu.LoadingSchedule;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.yunduancn.zhongshenjiaoyu.R;

public class WebviewProgressDialog extends Dialog {
	static CircleProgressBar progress1;
	private static WebviewProgressDialog customProgressDialog = null;

	public WebviewProgressDialog(Context context, int themeResId) {
		super(context, themeResId);
	}

	public WebviewProgressDialog(Context context) {
		super(context);

	}

	@SuppressLint("InlinedApi")
	public static WebviewProgressDialog createDialog(Context context) {

		customProgressDialog = new WebviewProgressDialog(context,
				R.style.CustomProgressDialog);
		customProgressDialog.setContentView(R.layout.webviewdialog);

		progress1 = (CircleProgressBar) customProgressDialog
				.findViewById(R.id.progress1);
		progress1.setColorSchemeResources(android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

		return customProgressDialog;
	}

	public void setProgress(int Progress) {
		
		progress1.setProgress(Progress);
	}
}
