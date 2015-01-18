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
		
		webView = (WebView) findViewById(R.id.webView);				// 根据ID找到WebView
		webView.getSettings().setJavaScriptEnabled(true);			// 允许JS
		webView.loadUrl("file:///android_asset/index.html");		// 加载页面
		
		webView.addJavascriptInterface(new Contact(), "contact");	// 创建Contact对象, 传给WebView, 作为JS对象
	}
	
	class Contact {
		public void showContacts() {
			String json = "[{name:\"朴乾\", amount:\"20000\", phone:\"13901012345\"}, {name:\"付东\", amount:\"12345\", phone:\"18600054321\"}]";
			webView.loadUrl("javascript:show('" + json + "')");		// 调用JS方法
		}
		public void call(String phone) {
			startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel://" + phone)));
		}
	}

}
