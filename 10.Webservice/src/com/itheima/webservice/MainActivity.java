package com.itheima.webservice;

import java.io.StringReader;

import org.apache.http.Header;
import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class MainActivity extends Activity {

	private EditText numberET;
	private TextView addressTV;
	private AsyncHttpClient client;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		numberET = (EditText) findViewById(R.id.numberET);
		addressTV = (TextView) findViewById(R.id.addressTV);
		client = new AsyncHttpClient();
	}


	public void query(View v) {
		String number = numberET.getText().toString().trim();
		
		String url = "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo";
		RequestParams params = new RequestParams();
		params.put("mobileCode", number);
		params.put("userID", "");
		
		client.get(url, params, new TextHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers, String responseString) {
				try {
					XmlPullParser parser = Xml.newPullParser();
					parser.setInput(new StringReader(responseString));
					for (int type = parser.getEventType(); type != XmlPullParser.END_DOCUMENT; type = parser.next())
						if (type == XmlPullParser.START_TAG && "string".equals(parser.getName()))
							addressTV.setText(parser.nextText());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				Toast.makeText(getApplicationContext(), "ณ๖ดํมห " + statusCode, Toast.LENGTH_SHORT).show();
				throwable.printStackTrace();
			}
		});
		
	}
}
