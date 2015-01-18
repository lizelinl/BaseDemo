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
		public void handleMessage(Message msg) {	// 该方法在sendMessage()方法之后执行, 形参就是发送过来的Message对象
			tv.setText(msg.obj + "");				// 主线程更新界面
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
					// Message msg = new Message();			// 创建消息对象
					Message msg = handler.obtainMessage();	// 从消息池中获取一个Message
					msg.obj = i;							// 把数据放在消息对象中
					handler.sendMessage(msg);				// 在新线程中发送消息对象, 主线程会自动执行handleMessage()方法
					System.out.println(i);
					SystemClock.sleep(1000);
				}
			}
		}.start();
		
	}


}
