package com.itheima.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		generateSpinner();
		generateACTV();
	}

	private void generateACTV() {
		String[] items = { "tom", "tony", "terry", "txt", "张孝祥", "张泽华", "张三丰", "张无忌", "ooo" };
		AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.actv);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items);
		actv.setAdapter(adapter);
	}

	private void generateSpinner() {
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item); 	// 设置下拉列表默认布局
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);							// 设置下拉后的布局
		adapter.add("请选择要学习的课程");			// 添加条目
		adapter.add("Java");			// 添加条目
		adapter.add(".Net");
		adapter.add("PHP");
		adapter.add("网页设计");
		adapter.add("iOS");
		spinner.setAdapter(adapter);	// 把适配器传给下拉列表
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {	// 添加监听器
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String item = (String) parent.getItemAtPosition(position);	// 获取选中的条目
				Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
				// android2.2 jdk5
				// android2.3 jdk6
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	public void onRadioClick(View v) {
		RadioGroup lessonsRG = (RadioGroup) findViewById(R.id.lessonsRG);
		int id = lessonsRG.getCheckedRadioButtonId();	// 获取选中的单选按钮的id
		String msg = null;
		switch (id) {
			case R.id.javaRB:
				msg = ((RadioButton)findViewById(id)).getText().toString();		// 获取按钮上的文本
				break;
			case R.id.netRB:
				msg = ".Net";
				break;
			case R.id.phpRB:
				msg = "PHP";
				break;
		}
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	public void onCheckboxClick(View v) {
		CheckBox javaCB = (CheckBox) findViewById(R.id.smokeCB); 	// 根据ID获取多选框
		CheckBox netCB = (CheckBox) findViewById(R.id.drinkCB);
		CheckBox phpCB = (CheckBox) findViewById(R.id.permCB);

		StringBuilder sb = new StringBuilder();
		sb.append(javaCB.isChecked() ? javaCB.getText() + " " : "");
		sb.append(netCB.isChecked() ? netCB.getText() + " " : "");
		sb.append(phpCB.isChecked() ? phpCB.getText() + " " : "");

		Toast.makeText(this, sb, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {			// 按菜单按钮时调用
		getMenuInflater().inflate(R.menu.main, menu);		// 加载布局文件
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {	// 当选中菜单项时调用
		switch (item.getItemId()) {
			case R.id.collect:
				Toast.makeText(getApplicationContext(), "收藏此文章", Toast.LENGTH_SHORT).show();
				break;
			case R.id.sina:
				Toast.makeText(getApplicationContext(), "分享到新浪微博", Toast.LENGTH_SHORT).show();
				break;
			case R.id.tencent:
				Toast.makeText(getApplicationContext(), "分享到QQ空间", Toast.LENGTH_SHORT).show();
				break;
		}
		return super.onOptionsItemSelected(item);
	}



}
