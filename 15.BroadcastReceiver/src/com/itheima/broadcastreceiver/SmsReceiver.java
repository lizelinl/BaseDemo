package com.itheima.broadcastreceiver;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {
	// 系统收到短信 -> 发出广播 -> 自定义接收者 -> 过滤数据(中断) -> 短信应用(广播接收者) -> 通知

	@Override
	public void onReceive(Context context, Intent intent) {		// 收到广播时自动执行
		System.out.println("onReceive");
		
		Object[] pdus = (Object[]) intent.getExtras().get("pdus");
		for (Object pdu : pdus) {
			SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);	// 把pdu封装成SmsMessage对象
			Date date = new Date(sms.getTimestampMillis());
			String num = sms.getOriginatingAddress();
			String body = sms.getMessageBody();
			
			System.out.println(date + ": " + num + ": " + body);
			
			if ("18600012345".equals(num))
				abortBroadcast();
		}
	}

}
