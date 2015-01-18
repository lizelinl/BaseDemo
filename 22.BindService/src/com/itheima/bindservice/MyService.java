package com.itheima.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {
	
	@Override
	public void onCreate() {
		System.out.println("onCreate");
		super.onCreate();
	}
	
	@Override
	public IBinder onBind(Intent intent) {		// bindService()时会执行
		System.out.println("onBind");
		return new MyBinder();
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		System.out.println("onUnbind");
		return super.onUnbind(intent);
	}
	
	@Override
	public void onDestroy() {
		System.out.println("onDestroy");
		super.onDestroy();
	}
	
	private class MyBinder extends Binder implements InvokeInterface {
		public void pay() {
			System.out.println("进行网络支付!");
		}
		public void play() {
			System.out.println("进行音乐播放");
		}
	}

}
