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
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);	// ������ͼ, ָ������(����)
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); 			// ָ���洢·��
		startActivityForResult(intent, 100);							// ����ϵͳ�Դ�������Ӧ��
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
				Toast.makeText(getApplicationContext(), "���ճɹ�: " + imageUri, Toast.LENGTH_SHORT).show();
				break;
			case 200:
				Toast.makeText(getApplicationContext(), "¼��ɹ�: " + videoUri, Toast.LENGTH_SHORT).show();
				break;
		}
	}
}
