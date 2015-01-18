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
		Intent intent = new Intent(this, OtherActivity.class);		// 创建意图
//		intent.setClass(this, OtherActivity.class);					// 指定类
//		intent.setClassName(this, "com.itheima.activity.MainActivity");
//		intent.setClassName("com.android.camera", "com.android.camera.Camera");		// 启动其他应用时, Activity的export属性为true才能用
		startActivity(intent);										// 启动Activity
	}
	
	public void implicit(View v) {
		Intent intent = new Intent();
		
		/*
		intent.setAction(Intent.ACTION_CALL);				// 设置动作
		intent.setData(Uri.parse("tel://18600012345"));		// 设置数据
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
		
		startActivity(intent);								// 根据意图中的动作和数据来匹配一个Activity
	}
	
	@Override
	public void finish() {	// 重写finish(), 在退出的时候弹出对话框
		new Builder(this)
			.setTitle("确定要退出吗?")
			.setPositiveButton("确定", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					MainActivity.super.finish();	// 如果点击确定按钮, 执行父类的finish(), 真正退出
				}
			})
			.setNegativeButton("取消", null)
			.show();
		
		/*
		if (System.currentTimeMillis() - previous < 1000) {
			super.finish();
		} else {
			previous = System.currentTimeMillis(); 
			Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
		}
		*/
	}
	
	public void sendData(View v) {
		Intent intent = new Intent(this, OtherActivity.class);
		
		/*
		intent.putExtra("name", "张三");		// 向Intent中装入数据, String
		intent.putExtra("age", 21);			// 基本数据类型
		*/
		
		/*
		Person p = new Person("李四", 22);
		intent.putExtra("p", p);			// 如果需要传输自定义类对象, 必须实现Serializable接口
		*/
		
		/*
		Bundle bundle = new Bundle();
		bundle.putString("name", "王五");	// 传递一个Bundle(Map集合)
		bundle.putInt("age", 23);
		intent.putExtra("bundle", bundle);
		*/
		
		Person p = new Person("李四", 22);
		intent.putExtra("p", p);
		
		startActivity(intent);				// 启动Activity, Intent会把数据携带过去
	}
	
	public void getResult100(View v) {
		Intent intent = new Intent(this, OtherActivity.class);
		Person p = new Person("李四", 22);
		intent.putExtra("p", p);
		startActivityForResult(intent, 100);	// 1.启动新的Activity, 并等待结果
	}
	
	public void getResult101(View v) {
		Intent intent = new Intent(this, OtherActivity.class);
		Person p = new Person("李四", 22);
		intent.putExtra("p", p);
		startActivityForResult(intent, 101);	// 1.启动新的Activity, 并等待结果
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {		// 3.这个方法会在新Activity关闭后执行
		String name = intent.getStringExtra("name");
		int age= intent.getIntExtra("age", -1);
		TextView mainTV = (TextView) findViewById(R.id.mainTV);
		mainTV.setText(name + ": " + age);
		
		/*
		 * requestCode: 代表从哪里启动的Activity
		 * resultCode:  代表哪个Activity关闭之后返回的数据
		 */
		Toast.makeText(getApplicationContext(), requestCode + ", " + resultCode, Toast.LENGTH_SHORT).show();
	}

}
