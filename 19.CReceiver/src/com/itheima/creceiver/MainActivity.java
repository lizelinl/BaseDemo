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
		
		receiver = new C_Receiver();			// �����㲥������
		IntentFilter filter = new IntentFilter("itheima.broadcast.ORDERED");	// ָ��Ҫ���յĹ㲥
		filter.setPriority(-1);					// �������ȼ�
		registerReceiver(receiver, filter);		// Activity����, ע��㲥������
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		unregisterReceiver(receiver);			// Activity�˳�, ע���㲥������
	}

}
