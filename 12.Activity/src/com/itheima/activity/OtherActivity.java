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
		Intent intent = getIntent();					// 获取Intent
		
		/*
		String name = intent.getStringExtra("name");	// 从Intent中获取数据
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
		
		intent.putExtra("name", "赵六");
		intent.putExtra("age", 24);
		setResult(200, intent);		// 2.设置结果数据, 在finish()之后, 结果数据就会传递到onActivityResult()方法
	}
	
	public void close(View v) {
		finish();	
	}

}
