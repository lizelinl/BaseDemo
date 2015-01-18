package com.itheima.webview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends Activity {

	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		webView = (WebView) findViewById(R.id.webView);				// ����ID�ҵ�WebView
		webView.getSettings().setJavaScriptEnabled(true);			// ����JS
		webView.loadUrl("file:///android_asset/index.html");		// ����ҳ��
		
		webView.addJavascriptInterface(new Contact(), "contact");	// ����Contact����, ����WebView, ��ΪJS����
	}
	
	class Contact {
		public void showContacts() {
			String json = "[{name:\"��Ǭ\", amount:\"20000\", phone:\"13901012345\"}, {name:\"����\", amount:\"12345\", phone:\"18600054321\"}]";
			webView.loadUrl("javascript:show('" + json + "')");		// ����JS����
		}
		public void call(String phone) {
			startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel://" + phone)));
		}
	}

}
