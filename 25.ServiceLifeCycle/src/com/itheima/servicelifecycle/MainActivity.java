package com.itheima.servicelifecycle;

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
	private ServiceConnection conn = new ServiceConnection() {
		public void onServiceDisconnected(ComponentName name) {
			System.out.println("onServiceDisconnected");
		}
		public void onServiceConnected(ComponentName name, IBinder iBinder) {
			ii = Stub.asInterface(iBinder);
		}
	};
	
	private Intent intent;
	private InvokeInterface ii;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		intent = new Intent("itheima.service.REMOTE_SERVICE");
	}

	public void start(View v) {
		startService(intent);
	}

	public void bind(View v) {
		bindService(intent , conn, BIND_AUTO_CREATE);
	}

	public void invoke(View v) throws Exception {
		System.out.println(ii.pay(new Person(1, "ΆΰΓ½Με", 20), 90)); 
	}

	public void unbind(View v) {
		unbindService(conn);
	}

	public void stop(View v) {
		stopService(intent);
	}

}
