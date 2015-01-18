package com.itheima.areceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class A_Receiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("A_Receiver onReceive: " + intent.getStringExtra("data"));
		
		if (isOrderedBroadcast()) {
			int code = getResultCode();
			String data = getResultData();
			Bundle bundle = getResultExtras(true);	
			System.out.println(code + ", " + data + ", " + bundle.getString("name") + ", " + bundle.getInt("age"));
			
			bundle.putString("name", "李四");
			bundle.putInt("age", 22);
			setResult(2, "A_Receiver", bundle);		// 修改Reuslt数据, 后面的接收者将收到修改后的数据
			
//			abortBroadcast();	// 中断广播, 阻止后面的接收者接收广播, 不包括ResultReceiver
		}
	}

}
