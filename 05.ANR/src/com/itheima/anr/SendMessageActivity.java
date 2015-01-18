package com.itheima.anr;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

public class SendMessageActivity extends Activity {

	private TextView tv;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {	// �÷�����sendMessage()����֮��ִ��, �βξ��Ƿ��͹�����Message����
			tv.setText(msg.obj + "");				// ���̸߳��½���
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.tv);	
	}
	
	public void go(View v) {
		new Thread() {
			public void run() {
				for (int i = 1; ; i++) {
					// Message msg = new Message();			// ������Ϣ����
					Message msg = handler.obtainMessage();	// ����Ϣ���л�ȡһ��Message
					msg.obj = i;							// �����ݷ�����Ϣ������
					handler.sendMessage(msg);				// �����߳��з�����Ϣ����, ���̻߳��Զ�ִ��handleMessage()����
					System.out.println(i);
					SystemClock.sleep(1000);
				}
			}
		}.start();
		
	}


}
