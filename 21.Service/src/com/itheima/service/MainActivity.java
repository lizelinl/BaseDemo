package com.itheima.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		intent = new Intent(this, MyService.class);
	}

	public void start(View v) {
		startService(intent);	// 启动服务, onStartCommand()
	}
	
	public void stop(View v) {
		stopService(intent);	// 停止服务, onDestroy()
	}
	
}
