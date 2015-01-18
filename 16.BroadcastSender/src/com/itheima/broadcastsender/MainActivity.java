package com.itheima.broadcastsender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void sendNormal(View v) {
		Intent intent = new Intent("itheima.broadcast.NORMAL");
		intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);		// ������δ��������Ӧ��(3.1֮��Ĭ�ϲ�����)
		intent.putExtra("data", "��ͨ�㲥Intent���͵�����");
		sendBroadcast(intent, null);
	}
	
	public void sendOrdered(View v) {
		Intent intent = new Intent("itheima.broadcast.ORDERED");
		intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);		
		intent.putExtra("data", "����㲥Intent���͵�����");
		
		Bundle bundle = new Bundle();
		bundle.putString("name", "����");
		bundle.putInt("age", 21);
		
		sendOrderedBroadcast(intent, "itheima.permission.BROADCAST", new ResultReceiver(), null, 1, "MainActivity", bundle);
	}

}
