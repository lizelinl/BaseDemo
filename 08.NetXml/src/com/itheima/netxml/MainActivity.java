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
				Toast.makeText(getApplicationContext(), "获取XML成功", Toast.LENGTH_SHORT).show();
			}
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
			}
		});
	}
	
	public void getJson(View v) {
		new AsyncHttpClient().get("http://192.168.1.251:8080/WebServer/persons.js", new JsonHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers, JSONArray arr) {
				try {
					for (int i = 0; i < arr.length(); i++ ) {	// 遍历JSONArray
						JSONObject obj = arr.getJSONObject(i);	// 得到每一个JSONObject
						int id = obj.getInt("id");				// 获取每个JSONObject中的数据, 封装成Person
						String name = obj.getString("name");
						int age = obj.getInt("age");
						Person p = new Person(id, name, age);
						System.out.println(p);
					}
					Toast.makeText(getApplicationContext(), "获取JSON成功!!!", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(getApplicationContext(), "获取JSON成功", Toast.LENGTH_SHORT).show();
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
			JSONArray arr = new JSONArray(json);		// 把字符串封装成一个JSONArray
			for (int i = 0; i < arr.length(); i++ ) {	// 遍历JSONArray
				JSONObject obj = arr.getJSONObject(i);	// 得到每一个JSONObject
				int id = obj.getInt("id");				// 获取每个JSONObject中的数据, 封装成Person
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
					if ("person".equals(parser.getName())) {		// 如果开始的是person, 创建对象, 获取id数据
						String id = parser.getAttributeValue(0);
						p = new Person();
						p.setId(Integer.parseInt(id));
						persons.add(p);
					} else if ("name".equals(parser.getName())) {	// 如果开始的是name, 获取下一个文本
						String name = parser.nextText();
						p.setName(name);
					} else if ("age".equals(parser.getName())) {	// 如果开始的是age, 获取下一个文本
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
