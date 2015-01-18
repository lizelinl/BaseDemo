package com.itheima.netimage;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.image.SmartImageView;

public class MainActivity extends Activity {

	private EditText et;
	private SmartImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		et = (EditText) findViewById(R.id.et);
		iv = (SmartImageView) findViewById(R.id.iv);
		
		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);	
		System.out.println(manager.getActiveNetworkInfo().getState());		// ��ȡ����״̬
		System.out.println(manager.getActiveNetworkInfo().getTypeName());	// ��ȡ��������
		
		// ����һ���߳�, ��黺���ļ��д�С, �������һ��ָ���Ĵ�С, ����һЩ���ļ�
	}
	
	public void go(View v) throws Exception {
		iv.setImageUrl(et.getText().toString().trim());		// SmartImageView����ֱ�Ӵ�һ��URL������ȡͼƬ
		
		/*
		String path = et.getText().toString().trim();									// ��ȡ�����ַ
		final File file = new File(getCacheDir(), URLEncoder.encode(path, "UTF-8"));	// ���建���ļ���·��
		
		AsyncHttpClient client = new AsyncHttpClient();	
		if (file.exists())																// ��������ļ�����
			client.addHeader("If-Modified-Since", format(file.lastModified()));			// �������ͷ
		
		client.get(path, new AsyncHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				if (statusCode == 200) {
					Bitmap bm = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);	// ��ȡ��������
					iv.setImageBitmap(bm);
					try {
						FileOutputStream out = new FileOutputStream(file);
						out.write(responseBody);	// ���浽����
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				if (statusCode == 304) {
					Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());	// �ӻ����ļ���ȡ����
					iv.setImageBitmap(bm);
				} else
					Toast.makeText(getApplicationContext(), "�������: " + statusCode, Toast.LENGTH_SHORT).show();
			}
		});
		*/
	}
	
	public String format(long ms) {
		Date date = new Date(ms);
		SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
		String s = format.format(date);
		return s;
	}

	
}
