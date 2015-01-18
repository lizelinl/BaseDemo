package com.itheima.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.SystemClock;

public class MyService extends Service {
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		System.out.println("onCreate");
		super.onCreate();
		
		Notification n = new Notification(R.drawable.ic_launcher, "MyService", System.currentTimeMillis());
		Intent intent = new Intent();
		intent.setClassName("com.itheima.service", "com.itheima.service.MainActivity");
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);	
		n.setLatestEventInfo(this, "MyService", "MyService�ں�̨��������", pi);				
		
		startForeground(Process.myPid(), n);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, final int startId) {
		System.out.println("onStartCommand");
		
		new Thread() {
			public void run() {
				for (int i = 0; i < 10; i++) {
					System.out.println(i);
					SystemClock.sleep(1000);
				}
				// stopSelf();		// ���۵����˶��ٴ�start(), ��ֹͣ����
				stopSelf(startId);	// ��ǵ�ǰ�����start()ֹͣ, �ȵ�����start()��ֹͣ��ʱ��, ֹͣ����
			}
		}.start();
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		System.out.println("onBind");
		return null;
	}
	
	@Override
	public void onDestroy() {
		System.out.println("onDestroy");
		super.onDestroy();
	}

}
