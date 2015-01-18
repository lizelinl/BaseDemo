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
		public void onServiceDisconnected(ComponentName name) {		// �ڷ���ɱ��ʱ��ִ��(���ʱ����ִ��)
		}
		public void onServiceConnected(ComponentName name, IBinder iBinder) {	
			ii = Stub.asInterface(iBinder);		// Stub.asInterface() ���԰�IBinderתΪ�ӿ�����
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bindService(new Intent("itheima.service.REMOTE_SERVICE") , conn, BIND_AUTO_CREATE);	// �󶨷���, bindService(), �õ�IBinder
	}
	
	public void pay(View v) throws Exception {
		System.out.println(ii.pay(new Person(101, "FD", 29), 150));	// ͨ��IBinder, ����Service�е�֧��
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(conn);	// ������, unbindService()
	}

}
