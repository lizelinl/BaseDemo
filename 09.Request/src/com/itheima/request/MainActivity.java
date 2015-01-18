package com.itheima.request;

import java.io.File;

import org.apache.http.Header;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MainActivity extends Activity {

	private EditText usernameET;
	private EditText passwordET;
	private EditText pathET;
	private AsyncHttpClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		usernameET = (EditText) findViewById(R.id.usernameET);
		passwordET = (EditText) findViewById(R.id.passwordET);
		pathET = (EditText) findViewById(R.id.pathET);
		client = new AsyncHttpClient();
	}
	
	public void login(View v) throws Exception {
		String username = usernameET.getText().toString().trim();	
		String password = passwordET.getText().toString().trim();
		String path = pathET.getText().toString().trim();
		
		RequestParams params = new RequestParams();
		params.put("username", username);		// URLEncoder.encode()
		params.put("password", password);
		params.put("upload", new File(path));	// File类型会作为上传文件字段
		
		client.post("http://192.168.1.251:8080/WebServer/LoginServlet", params , new AsyncHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
			}
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				Toast.makeText(getApplicationContext(), "登录失败 " + statusCode, Toast.LENGTH_SHORT).show();
				error.printStackTrace();
			}
		});
	}

}
