package com.itheima.systemcamera;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Uri imageUri = Uri.parse("file:///mnt/sdcard/" + System.currentTimeMillis() + ".jpg");
	private Uri videoUri = Uri.parse("file:///mnt/sdcard/" + System.currentTimeMillis() + ".mp4");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void takeImage(View v) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);	// 创建意图, 指定动作(拍照)
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); 			// 指定存储路径
		startActivityForResult(intent, 100);							// 启动系统自带的拍照应用
	}

	public void takeVideo(View v) {
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
		startActivityForResult(intent, 200);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case 100:
				Toast.makeText(getApplicationContext(), "拍照成功: " + imageUri, Toast.LENGTH_SHORT).show();
				break;
			case 200:
				Toast.makeText(getApplicationContext(), "录像成功: " + videoUri, Toast.LENGTH_SHORT).show();
				break;
		}
	}
}
