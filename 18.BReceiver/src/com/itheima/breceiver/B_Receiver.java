package com.itheima.breceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class B_Receiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("B_Receiver onReceive: " + intent.getStringExtra("data"));
		
		if (isOrderedBroadcast()) {
			int code = getResultCode();
			String data = getResultData();
			Bundle bundle = getResultExtras(true);	
			System.out.println(code + ", " + data + ", " + bundle.getString("name") + ", " + bundle.getInt("age"));
			
			bundle.putString("name", "ÍõÎå");
			bundle.putInt("age", 23);
			setResult(3, "B_Receiver", bundle);
		}
	}

}
