package com.itheima.nettext;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ClassicActivity extends Activity {

	private EditText et;
	private TextView tv;
	private TextService service;
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		et = (EditText) findViewById(R.id.et);
		tv = (TextView) findViewById(R.id.tv);
		service = new TextService();
	}

	public void go(View v) {
		new Thread() {
			public void run() {
				try {
					String path = et.getText().toString().trim();	// 从EditText获取地址
					final String text = service.getText(path);		// 访问网络, 得到文本
					handler.post(new Runnable() {
						public void run() {
							tv.setText(text);						// 设置到TextView中
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					handler.post(new Runnable(){
						public void run() {
							Toast.makeText(getApplicationContext(), "服务器忙, 请稍后再试!", Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		}.start();
	}
}
