package com.itheima.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OtherActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other);
		
//		String action = getIntent().getAction();		
//		Uri data = getIntent().getData();				
//		String type = getIntent().getType();			
		
		TextView otherTV = (TextView) findViewById(R.id.otherTV);
		Intent intent = getIntent();					// ��ȡIntent
		
		/*
		String name = intent.getStringExtra("name");	// ��Intent�л�ȡ����
		int age = intent.getIntExtra("age", -1);
		otherTV.setText(name + ": " + age);
		*/
		
		/*
		Person p = (Person) intent.getSerializableExtra("p");
		otherTV.setText(p.getName() + ": " + p.getAge());
		*/
		
		/*
		Bundle bundle = intent.getBundleExtra("bundle");
		otherTV.setText(bundle.getString("name") + ": " + bundle.getInt("age"));
		*/
		
		Person p = intent.getParcelableExtra("p");
		otherTV.setText(p.getName() + ": " + p.getAge());
		
		intent.putExtra("name", "����");
		intent.putExtra("age", 24);
		setResult(200, intent);		// 2.���ý������, ��finish()֮��, ������ݾͻᴫ�ݵ�onActivityResult()����
	}
	
	public void close(View v) {
		finish();	
	}

}
