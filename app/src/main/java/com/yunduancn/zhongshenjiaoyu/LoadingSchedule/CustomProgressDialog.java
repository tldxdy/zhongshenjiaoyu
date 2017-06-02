package com.yunduancn.zhongshenjiaoyu.LoadingSchedule;



import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;

import com.yunduancn.zhongshenjiaoyu.R;

public class CustomProgressDialog extends Dialog {
	static CircleProgressBar progress1;
	private static CustomProgressDialog customProgressDialog = null;
	public CustomProgressDialog(Context context, int themeResId) {
		super(context, themeResId);
	}

	public CustomProgressDialog(Context context) {
		super(context);
		
		
	}
	
	@SuppressLint("InlinedApi") 
	public static CustomProgressDialog createDialog(Context context){
		
		customProgressDialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
		customProgressDialog.setContentView(R.layout.dialog);
		
		progress1 = (CircleProgressBar) customProgressDialog.findViewById(R.id.progress1);
		progress1.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_orange_light,android.R.color.holo_red_light);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		
		return customProgressDialog;
	}
	@SuppressLint("InlinedApi") 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.dialog);
	
	}

}
