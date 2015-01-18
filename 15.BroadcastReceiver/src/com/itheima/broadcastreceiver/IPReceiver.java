package com.itheima.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class IPReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String number = getResultData();
		
		if (!number.startsWith("17951"))
			setResultData("17951" + number);
		
//		setResultData("");
	}

}
