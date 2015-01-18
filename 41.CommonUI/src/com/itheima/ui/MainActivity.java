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
		String[] items = { "tom", "tony", "terry", "txt", "��Т��", "����", "������", "���޼�", "ooo" };
		AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.actv);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items);
		actv.setAdapter(adapter);
	}

	private void generateSpinner() {
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item); 	// ���������б�Ĭ�ϲ���
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);							// ����������Ĳ���
		adapter.add("��ѡ��Ҫѧϰ�Ŀγ�");			// �����Ŀ
		adapter.add("Java");			// �����Ŀ
		adapter.add(".Net");
		adapter.add("PHP");
		adapter.add("��ҳ���");
		adapter.add("iOS");
		spinner.setAdapter(adapter);	// �����������������б�
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {	// ��Ӽ�����
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String item = (String) parent.getItemAtPosition(position);	// ��ȡѡ�е���Ŀ
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
		int id = lessonsRG.getCheckedRadioButtonId();	// ��ȡѡ�еĵ�ѡ��ť��id
		String msg = null;
		switch (id) {
			case R.id.javaRB:
				msg = ((RadioButton)findViewById(id)).getText().toString();		// ��ȡ��ť�ϵ��ı�
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
		CheckBox javaCB = (CheckBox) findViewById(R.id.smokeCB); 	// ����ID��ȡ��ѡ��
		CheckBox netCB = (CheckBox) findViewById(R.id.drinkCB);
		CheckBox phpCB = (CheckBox) findViewById(R.id.permCB);

		StringBuilder sb = new StringBuilder();
		sb.append(javaCB.isChecked() ? javaCB.getText() + " " : "");
		sb.append(netCB.isChecked() ? netCB.getText() + " " : "");
		sb.append(phpCB.isChecked() ? phpCB.getText() + " " : "");

		Toast.makeText(this, sb, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {			// ���˵���ťʱ����
		getMenuInflater().inflate(R.menu.main, menu);		// ���ز����ļ�
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {	// ��ѡ�в˵���ʱ����
		switch (item.getItemId()) {
			case R.id.collect:
				Toast.makeText(getApplicationContext(), "�ղش�����", Toast.LENGTH_SHORT).show();
				break;
			case R.id.sina:
				Toast.makeText(getApplicationContext(), "��������΢��", Toast.LENGTH_SHORT).show();
				break;
			case R.id.tencent:
				Toast.makeText(getApplicationContext(), "����QQ�ռ�", Toast.LENGTH_SHORT).show();
				break;
		}
		return super.onOptionsItemSelected(item);
	}



}
