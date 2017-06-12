package com.yunduancn.zhongshenjiaoyu.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.dou361.ijkplayer.utils.ResourceUtils;
import com.yunduancn.zhongshenjiaoyu.R;

public class dialogexit {
	public interface onexitlistener {
		public void exitlistener();
	};
	

	public static void show(Context con, String content,
			final onexitlistener listener) {
		View v = View.inflate(con, R.layout.titledialog, null);
		TextView content1 = (TextView) v.findViewById(R.id.text);
			content1.setText(content);

		TextView confim = (TextView) v.findViewById(R.id.confim);
		TextView cancel = (TextView) v.findViewById(R.id.cancel);
		final AlertDialog dialog = new AlertDialog.Builder(con).create();
		dialog.setCancelable(false);
		dialog.show();
		dialog.setContentView(v);
		confim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog.dismiss();
				listener.exitlistener();

			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog.dismiss();
				

			}
		});
		dialog.getWindow().setLayout(ResourceUtils.getScreenWidth(con)/3*2, LayoutParams.WRAP_CONTENT);
		dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogexit);
	}
	
}
