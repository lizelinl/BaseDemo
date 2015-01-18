package com.itheima.notification;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@SuppressWarnings("deprecation")
	public void sendNotification(View v) {
		// 创建通知
		Notification n = new Notification(R.drawable.ic_launcher, "下载完成", System.currentTimeMillis());
		
		// 设置通知数据
		Intent intent = new Intent();
		intent.setClassName("com.itheima.download", "com.itheima.download.MainActivity");
		PendingIntent pi = PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_ONE_SHOT);	// Context, 请求码, 点击时的意图, 一次
		n.setLatestEventInfo(this, "FeiQ.exe下载完成", "FeiQ.exe下载完成, 耗时2分35秒.", pi);				// Context, 标题, 文本, 待定意图
		n.flags = Notification.FLAG_AUTO_CANCEL;	// 用过之后自动取消
		n.sound = Uri.parse("file:///mnt/sdcard/jiaodizhu.mp3");
		
		// 获取通知管理器
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		// 用管理器发送通知
		manager.notify(Process.myPid(), n);
	}
	
	public void normal(View v) {
		OnClickListener listener = new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						Toast.makeText(getApplicationContext(), "更新", Toast.LENGTH_SHORT).show();
						break;
					case DialogInterface.BUTTON_NEUTRAL:
						Toast.makeText(getApplicationContext(), "稍后", Toast.LENGTH_SHORT).show();
						break;
					case DialogInterface.BUTTON_NEGATIVE:
						Toast.makeText(getApplicationContext(), "拒绝", Toast.LENGTH_SHORT).show();
						break;
				}
			}
		};
		
		new Builder(this)
			.setTitle("是否下载更新")
			.setMessage("1.速度更快\r\n2.更多功能\r\n3.修复BUG")
			.setPositiveButton("更新", listener)
			.setNeutralButton("稍后", listener)
			.setNegativeButton("拒绝", listener)
			.setCancelable(false)	// 是否可以取消
			.show();
	}
	
	public void single(View v) {
		final String[] items = { "Java", ".Net", "PHP", "网页平面设计", "iOS", "C++" };
		
		OnClickListener listener = new OnClickListener() {
			private int index;
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						Toast.makeText(getApplicationContext(), items[index], Toast.LENGTH_SHORT).show();
						break;
					default:
						index = which;
				}
			}
		};
		
		new Builder(this)
			.setTitle("选择要学习的课程")
			.setSingleChoiceItems(items, -1, listener)	// 设置数据, 默认选项, 监听器
			.setPositiveButton("确定", listener)
			.show();
	}
	
	
	public void multi(View v) {
		final String[] items = { "抽烟", "喝酒", "烫头" };
		final boolean[] checkedItems = { false, false, false };
		
		OnMultiChoiceClickListener multiListener = new OnMultiChoiceClickListener() {
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				System.out.println(items[which] + ": " + isChecked);
			}
		};
		
		OnClickListener positiveListener = new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < checkedItems.length; i++) {
					sb.append(checkedItems[i] ? items[i] + " " : "");
				}
				Toast.makeText(getApplicationContext(), sb, Toast.LENGTH_SHORT).show();
			}
		};
		
		new Builder(this)
			.setTitle("请选择您的爱好")
			.setMultiChoiceItems(items , checkedItems, multiListener)
			.setPositiveButton("确定", positiveListener)
			.show();
	}
	
	public void progress(View v) {
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setTitle("正在下载, 请稍候...");
		dialog.show();
		dialog.setMax(321);			// 最大刻度
		
		new Thread(){
			public void run() {
				for (int i = 1; i <= 321; i++) {
					dialog.setProgress(i);	// 设置进度(可以在新线程中修改界面)
					SystemClock.sleep(10);
				}
				dialog.dismiss();	// 销毁
			}
		}.start();
		
		/*
		 * 带进度条的界面都可以在新线程中操作. 
		 * 		只要使用进度条, 一定是耗时操作. 
		 * 		耗时操作一定是在新线程中的.
		 * 		进度条通常都是在新线程中使用
		 * 		安卓已经替我们做了Handler的操作 
		 * 
		 * ProgressBar, SeekBar
		 */
		
	}

}
