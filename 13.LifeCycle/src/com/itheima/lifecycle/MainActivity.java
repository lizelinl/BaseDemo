package com.itheima.lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {	// ����
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.println("onCreate");
	}

	@Override
	protected void onStart() {		// �ɼ�
		super.onStart();
		System.out.println("onStart");
	}
	
	@Override
	protected void onResume() {		// ��ý���
		super.onResume();
		System.out.println("onResume");
	}
	
	@Override
	protected void onPause() {		// ʧȥ����
		super.onPause();
		System.out.println("onPause");
	}
	
	@Override
	protected void onStop() {		// ���ɼ�
		super.onStop();
		System.out.println("onStop");
	}
	
	@Override
	protected void onDestroy() {	// ����
		super.onDestroy();
		System.out.println("onDestroy");
	}
	
	@Override
	protected void onRestart() {	// �Ӳ��ɼ��ָ��ɼ�
		super.onRestart();
		System.out.println("onRestart");
	}
	
	public void play(View v) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file:///mnt/sdcard/1.mp3"), "audio/*");
		startActivity(intent);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle bundle) {			// �ݻٵ�ʱ����Զ�ִ��
		super.onSaveInstanceState(bundle);
		System.out.println("onSaveInstanceState");
		bundle.putString("data", "�ݻ�ʱ���������");
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle bundle) {		// �ָ���ʱ����Զ�ִ��
		super.onRestoreInstanceState(bundle);
		System.out.println("onRestoreInstanceState: " + bundle.getString("data"));
	}
	
	public void change(View v) {
		switch (getResources().getConfiguration().orientation) {	// ��ȡ��Ļ����
			case Configuration.ORIENTATION_LANDSCAPE:				// ����
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);	// ��Ϊ����
				break;	
			case Configuration.ORIENTATION_PORTRAIT:				// ����
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);	// ��Ϊ����
				break;
		}
	}

}
