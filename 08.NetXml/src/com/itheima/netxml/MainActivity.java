package com.itheima.netxml;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void getXml(View v) {
		new AsyncHttpClient().get("http://192.168.1.251:8080/WebServer/persons.xml", new TextHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers, String responseString) {
				List<Person> persons = parseXml(responseString);
				for (Person p : persons) 
					System.out.println(p);
				Toast.makeText(getApplicationContext(), "��ȡXML�ɹ�", Toast.LENGTH_SHORT).show();
			}
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
			}
		});
	}
	
	public void getJson(View v) {
		new AsyncHttpClient().get("http://192.168.1.251:8080/WebServer/persons.js", new JsonHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers, JSONArray arr) {
				try {
					for (int i = 0; i < arr.length(); i++ ) {	// ����JSONArray
						JSONObject obj = arr.getJSONObject(i);	// �õ�ÿһ��JSONObject
						int id = obj.getInt("id");				// ��ȡÿ��JSONObject�е�����, ��װ��Person
						String name = obj.getString("name");
						int age = obj.getInt("age");
						Person p = new Person(id, name, age);
						System.out.println(p);
					}
					Toast.makeText(getApplicationContext(), "��ȡJSON�ɹ�!!!", Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
		/*
		new AsyncHttpClient().get("http://192.168.1.251:8080/WebServer/persons.js", new TextHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers, String responseString) {
				List<Person> persons = parseJson(responseString);
				for (Person p : persons) 
					System.out.println(p);
				Toast.makeText(getApplicationContext(), "��ȡJSON�ɹ�", Toast.LENGTH_SHORT).show();
			}
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
			}
		});
		*/
	}
	
	@SuppressWarnings("unused")
	private List<Person> parseJson(String json) {
		List<Person> persons = new ArrayList<Person>();
		try {
			JSONArray arr = new JSONArray(json);		// ���ַ�����װ��һ��JSONArray
			for (int i = 0; i < arr.length(); i++ ) {	// ����JSONArray
				JSONObject obj = arr.getJSONObject(i);	// �õ�ÿһ��JSONObject
				int id = obj.getInt("id");				// ��ȡÿ��JSONObject�е�����, ��װ��Person
				String name = obj.getString("name");
				int age = obj.getInt("age");
				Person p = new Person(id, name, age);
				persons.add(p);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return persons;
	}
	
	private List<Person> parseXml(String xml) {
		List<Person> persons = new ArrayList<Person>();
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(xml));
			Person p = null;
			for (int type = parser.getEventType(); type != XmlPullParser.END_DOCUMENT; type = parser.next()) {
				if (type == XmlPullParser.START_TAG) {
					if ("person".equals(parser.getName())) {		// �����ʼ����person, ��������, ��ȡid����
						String id = parser.getAttributeValue(0);
						p = new Person();
						p.setId(Integer.parseInt(id));
						persons.add(p);
					} else if ("name".equals(parser.getName())) {	// �����ʼ����name, ��ȡ��һ���ı�
						String name = parser.nextText();
						p.setName(name);
					} else if ("age".equals(parser.getName())) {	// �����ʼ����age, ��ȡ��һ���ı�
						String age = parser.nextText();
						p.setAge(Integer.parseInt(age));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return persons;
	}

}
