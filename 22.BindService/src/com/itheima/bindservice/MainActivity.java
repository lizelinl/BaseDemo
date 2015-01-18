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
		public void onServiceDisconnected(ComponentName name) {		// �ڷ���ɱ��ʱ��ִ��(���ʱ����ִ��)
		}
		public void onServiceConnected(ComponentName name, IBinder iBinder) {	
			ii = (InvokeInterface) iBinder;		// �󶨳ɹ���ʱ��ִ��, IBinder����onBind()�������صĶ���
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bindService(new Intent(this, MyService.class) , conn, BIND_AUTO_CREATE);	// �󶨷���, bindService(), �õ�IBinder
	}
	
	public void pay(View v) {
		ii.pay();	// ͨ��IBinder, ����Service�е�֧��
		ii.play();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(conn);	// ������, unbindService()
	}

}
