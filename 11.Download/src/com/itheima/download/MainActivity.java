package com.itheima.download;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

//	private AsyncHttpClient client;
	
	private EditText pathET;
	private ProgressBar downloadPB;
	private TextView percentTV;
	private TextView progressTV;
	private DownloadTask task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		pathET = (EditText) findViewById(R.id.pathET);
		downloadPB = (ProgressBar) findViewById(R.id.downloadPB);
		percentTV = (TextView) findViewById(R.id.percentTV);
		progressTV = (TextView) findViewById(R.id.progressTV);
		
//		client = new AsyncHttpClient();
	}
	
	public void download(View v) {
		ImageButton ib = (ImageButton) v;
		
		if (task == null) {
			String targetUrl = pathET.getText().toString().trim();
			File localFile = new File(Environment.getExternalStorageDirectory(), targetUrl.substring(targetUrl.lastIndexOf("/") + 1));
			task = new DownloadTask(targetUrl, localFile, 3, this, downloadPB, percentTV, progressTV);
			task.execute();
			ib.setImageResource(android.R.drawable.ic_media_pause);
		} else {
			task.stop();
			task = null;
			ib.setImageResource(android.R.drawable.ic_media_play);
		}
	}

	/*
	public void classicDownload(View v) {
		String path = pathET.getText().toString().trim();
		File file = new File("/mnt/sdcard/", path.substring(path.lastIndexOf("/") + 1));
		
		client.get(path, new FileAsyncHttpResponseHandler(file) {		// 处理文件传输的处理器
			public void onSuccess(int statusCode, Header[] headers, File file) {
				Toast.makeText(getApplicationContext(), "下载成功", Toast.LENGTH_SHORT).show();
			}
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
				Toast.makeText(getApplicationContext(), "下载失败 " + statusCode, Toast.LENGTH_SHORT).show();
				throwable.printStackTrace();
			}
			public void onProgress(int bytesWritten, int totalSize) {	// 下载或上传的进度
				System.out.println(bytesWritten + " / " + totalSize + " (" + bytesWritten * 100 / totalSize + "%)");
			}
		});
	}
	*/

}
