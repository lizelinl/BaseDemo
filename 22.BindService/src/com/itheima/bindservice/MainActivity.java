package com.itheima.bindservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

public class MainActivity extends Activity {
	private InvokeInterface ii;
	
	private ServiceConnection conn = new ServiceConnection() {
		public void onServiceDisconnected(ComponentName name) {		// 在服务被杀的时候执行(解绑时不会执行)
		}
		public void onServiceConnected(ComponentName name, IBinder iBinder) {	
			ii = (InvokeInterface) iBinder;		// 绑定成功的时候执行, IBinder就是onBind()方法返回的对象
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bindService(new Intent(this, MyService.class) , conn, BIND_AUTO_CREATE);	// 绑定服务, bindService(), 得到IBinder
	}
	
	public void pay(View v) {
		ii.pay();	// 通过IBinder, 调用Service中的支付
		ii.play();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(conn);	// 解绑服务, unbindService()
	}

}
