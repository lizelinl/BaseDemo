package com.itheima.anr;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

public class PostActivity extends Activity {

	private TextView tv;
	private Handler handler = new Handler();
	private int i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.tv);	
	}
	
	public void go(View v) {
		new Thread() {
			public void run() {
				for (i = 1; ; i += 2) {
					handler.post(new Runnable(){	// �����߳���ʹ��Handler�����̷߳���һ�δ���, ���߳��Զ�ִ��run()����
						public void run() {
							tv.setText(i + "");
						}
					});
					
					System.out.println(i);
					SystemClock.sleep(1000);
				}
			}
		}.start();
	}


}
