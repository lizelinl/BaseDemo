package com.itheima.lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {	// 创建
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.println("onCreate");
	}

	@Override
	protected void onStart() {		// 可见
		super.onStart();
		System.out.println("onStart");
	}
	
	@Override
	protected void onResume() {		// 获得焦点
		super.onResume();
		System.out.println("onResume");
	}
	
	@Override
	protected void onPause() {		// 失去焦点
		super.onPause();
		System.out.println("onPause");
	}
	
	@Override
	protected void onStop() {		// 不可见
		super.onStop();
		System.out.println("onStop");
	}
	
	@Override
	protected void onDestroy() {	// 销毁
		super.onDestroy();
		System.out.println("onDestroy");
	}
	
	@Override
	protected void onRestart() {	// 从不可见恢复可见
		super.onRestart();
		System.out.println("onRestart");
	}
	
	public void play(View v) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file:///mnt/sdcard/1.mp3"), "audio/*");
		startActivity(intent);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle bundle) {			// 摧毁的时候会自动执行
		super.onSaveInstanceState(bundle);
		System.out.println("onSaveInstanceState");
		bundle.putString("data", "摧毁时保存的数据");
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle bundle) {		// 恢复的时候会自动执行
		super.onRestoreInstanceState(bundle);
		System.out.println("onRestoreInstanceState: " + bundle.getString("data"));
	}
	
	public void change(View v) {
		switch (getResources().getConfiguration().orientation) {	// 获取屏幕方向
			case Configuration.ORIENTATION_LANDSCAPE:				// 横屏
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);	// 改为竖屏
				break;	
			case Configuration.ORIENTATION_PORTRAIT:				// 竖屏
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);	// 改为横屏
				break;
		}
	}

}
