package com.itheima.activity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@SuppressWarnings("unused")
	private long previous;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void explicit(View v) {
		Intent intent = new Intent(this, OtherActivity.class);		// ������ͼ
//		intent.setClass(this, OtherActivity.class);					// ָ����
//		intent.setClassName(this, "com.itheima.activity.MainActivity");
//		intent.setClassName("com.android.camera", "com.android.camera.Camera");		// ��������Ӧ��ʱ, Activity��export����Ϊtrue������
		startActivity(intent);										// ����Activity
	}
	
	public void implicit(View v) {
		Intent intent = new Intent();
		
		/*
		intent.setAction(Intent.ACTION_CALL);				// ���ö���
		intent.setData(Uri.parse("tel://18600012345"));		// ��������
		*/
		
		/*
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://www.baidu.com"));
		*/
		
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file:///mnt/sdcard/a.jpg"), "image/*");
		
		/*
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file:///mnt/sdcard/1.mp3"), "audio/*");
		*/
		
		/*
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file:///mnt/sdcard/1.mp4"), "video/*");
		*/
		
		startActivity(intent);								// ������ͼ�еĶ�����������ƥ��һ��Activity
	}
	
	@Override
	public void finish() {	// ��дfinish(), ���˳���ʱ�򵯳��Ի���
		new Builder(this)
			.setTitle("ȷ��Ҫ�˳���?")
			.setPositiveButton("ȷ��", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					MainActivity.super.finish();	// ������ȷ����ť, ִ�и����finish(), �����˳�
				}
			})
			.setNegativeButton("ȡ��", null)
			.show();
		
		/*
		if (System.currentTimeMillis() - previous < 1000) {
			super.finish();
		} else {
			previous = System.currentTimeMillis(); 
			Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�", Toast.LENGTH_SHORT).show();
		}
		*/
	}
	
	public void sendData(View v) {
		Intent intent = new Intent(this, OtherActivity.class);
		
		/*
		intent.putExtra("name", "����");		// ��Intent��װ������, String
		intent.putExtra("age", 21);			// ������������
		*/
		
		/*
		Person p = new Person("����", 22);
		intent.putExtra("p", p);			// �����Ҫ�����Զ��������, ����ʵ��Serializable�ӿ�
		*/
		
		/*
		Bundle bundle = new Bundle();
		bundle.putString("name", "����");	// ����һ��Bundle(Map����)
		bundle.putInt("age", 23);
		intent.putExtra("bundle", bundle);
		*/
		
		Person p = new Person("����", 22);
		intent.putExtra("p", p);
		
		startActivity(intent);				// ����Activity, Intent�������Я����ȥ
	}
	
	public void getResult100(View v) {
		Intent intent = new Intent(this, OtherActivity.class);
		Person p = new Person("����", 22);
		intent.putExtra("p", p);
		startActivityForResult(intent, 100);	// 1.�����µ�Activity, ���ȴ����
	}
	
	public void getResult101(View v) {
		Intent intent = new Intent(this, OtherActivity.class);
		Person p = new Person("����", 22);
		intent.putExtra("p", p);
		startActivityForResult(intent, 101);	// 1.�����µ�Activity, ���ȴ����
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {		// 3.�������������Activity�رպ�ִ��
		String name = intent.getStringExtra("name");
		int age= intent.getIntExtra("age", -1);
		TextView mainTV = (TextView) findViewById(R.id.mainTV);
		mainTV.setText(name + ": " + age);
		
		/*
		 * requestCode: ���������������Activity
		 * resultCode:  �����ĸ�Activity�ر�֮�󷵻ص�����
		 */
		Toast.makeText(getApplicationContext(), requestCode + ", " + resultCode, Toast.LENGTH_SHORT).show();
	}

}
