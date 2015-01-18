package com.itheima.remoteactivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.itheima.remoteservice.invokeinterface.InvokeInterface;
import com.itheima.remoteservice.invokeinterface.InvokeInterface.Stub;
import com.itheima.removeservice.bean.Person;

public class MainActivity extends Activity {
	private InvokeInterface ii;
	
	private ServiceConnection conn = new ServiceConnection() {
		public void onServiceDisconnected(ComponentName name) {		// 在服务被杀的时候执行(解绑时不会执行)
		}
		public void onServiceConnected(ComponentName name, IBinder iBinder) {	
			ii = Stub.asInterface(iBinder);		// Stub.asInterface() 可以把IBinder转为接口类型
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bindService(new Intent("itheima.service.REMOTE_SERVICE") , conn, BIND_AUTO_CREATE);	// 绑定服务, bindService(), 得到IBinder
	}
	
	public void pay(View v) throws Exception {
		System.out.println(ii.pay(new Person(101, "FD", 29), 150));	// 通过IBinder, 调用Service中的支付
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(conn);	// 解绑服务, unbindService()
	}

}
