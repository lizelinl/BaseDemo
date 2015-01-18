package com.itheima.creceiver;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends Activity {

	private C_Receiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		receiver = new C_Receiver();			// 创建广播接收者
		IntentFilter filter = new IntentFilter("itheima.broadcast.ORDERED");	// 指定要接收的广播
		filter.setPriority(-1);					// 设置优先级
		registerReceiver(receiver, filter);		// Activity启动, 注册广播接收者
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		unregisterReceiver(receiver);			// Activity退出, 注销广播接收者
	}

}
