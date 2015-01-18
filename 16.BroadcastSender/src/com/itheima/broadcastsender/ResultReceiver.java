package com.itheima.broadcastsender;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ResultReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("ResultReceiver onReceive: " + intent.getStringExtra("data"));
		
		int code = getResultCode();
		String data = getResultData();
		Bundle bundle = getResultExtras(true);	
		System.out.println(code + ", " + data + ", " + bundle.getString("name") + ", " + bundle.getInt("age"));
	}

}
