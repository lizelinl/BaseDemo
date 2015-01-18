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
		System.out.println(manager.getActiveNetworkInfo().getState());		// 获取网络状态
		System.out.println(manager.getActiveNetworkInfo().getTypeName());	// 获取连接类型
		
		// 启动一个线程, 检查缓存文件夹大小, 如果超过一个指定的大小, 清理一些旧文件
	}
	
	public void go(View v) throws Exception {
		iv.setImageUrl(et.getText().toString().trim());		// SmartImageView可以直接从一个URL联网获取图片
		
		/*
		String path = et.getText().toString().trim();									// 获取网络地址
		final File file = new File(getCacheDir(), URLEncoder.encode(path, "UTF-8"));	// 定义缓存文件的路径
		
		AsyncHttpClient client = new AsyncHttpClient();	
		if (file.exists())																// 如果缓存文件存在
			client.addHeader("If-Modified-Since", format(file.lastModified()));			// 添加请求头
		
		client.get(path, new AsyncHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				if (statusCode == 200) {
					Bitmap bm = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);	// 获取网络数据
					iv.setImageBitmap(bm);
					try {
						FileOutputStream out = new FileOutputStream(file);
						out.write(responseBody);	// 保存到本地
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				if (statusCode == 304) {
					Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());	// 从缓存文件读取数据
					iv.setImageBitmap(bm);
				} else
					Toast.makeText(getApplicationContext(), "网络错误: " + statusCode, Toast.LENGTH_SHORT).show();
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
