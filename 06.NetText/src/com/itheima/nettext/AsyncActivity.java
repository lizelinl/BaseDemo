package com.itheima.nettext;

import org.apache.http.Header;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

public class AsyncActivity extends Activity {

	private EditText et;
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		et = (EditText) findViewById(R.id.et);
		tv = (TextView) findViewById(R.id.tv);
	}

	public void go(View v) {
		new AsyncHttpClient().get(et.getText().toString().trim(), new TextHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers, String responseString) {
				tv.setText(responseString);
			}
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				Toast.makeText(getApplicationContext(), "·þÎñÆ÷Ã¦!!! " + statusCode, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
