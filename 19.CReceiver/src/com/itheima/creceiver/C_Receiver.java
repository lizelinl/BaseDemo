package com.itheima.creceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class C_Receiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("C_Receiver onReceive: " + intent.getStringExtra("data"));
		
		if (isOrderedBroadcast()) {
			int code = getResultCode();
			String data = getResultData();
			Bundle bundle = getResultExtras(true);	
			System.out.println(code + ", " + data + ", " + bundle.getString("name") + ", " + bundle.getInt("age"));
			
			bundle.putString("name", "赵六");
			bundle.putInt("age", 24);
			setResult(4, "C_Receiver", bundle);		// 修改Reuslt数据, 后面的接收者将收到修改后的数据
		}
	}

}
