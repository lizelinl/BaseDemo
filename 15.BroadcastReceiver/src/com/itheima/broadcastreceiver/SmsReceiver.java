package com.itheima.broadcastreceiver;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {
	// ϵͳ�յ����� -> �����㲥 -> �Զ�������� -> ��������(�ж�) -> ����Ӧ��(�㲥������) -> ֪ͨ

	@Override
	public void onReceive(Context context, Intent intent) {		// �յ��㲥ʱ�Զ�ִ��
		System.out.println("onReceive");
		
		Object[] pdus = (Object[]) intent.getExtras().get("pdus");
		for (Object pdu : pdus) {
			SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);	// ��pdu��װ��SmsMessage����
			Date date = new Date(sms.getTimestampMillis());
			String num = sms.getOriginatingAddress();
			String body = sms.getMessageBody();
			
			System.out.println(date + ": " + num + ": " + body);
			
			if ("18600012345".equals(num))
				abortBroadcast();
		}
	}

}
