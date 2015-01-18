package com.itheima.remoteservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.itheima.remoteservice.invokeinterface.InvokeInterface.Stub;
import com.itheima.removeservice.bean.Person;

public class RemoteService extends Service {

	@Override
	public void onCreate() {
		System.out.println("Remote onCreate");
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("Remote onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {		// bindService()时会执行
		System.out.println("Remote onBind");
		return new MyBinder();
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		System.out.println("Remote onUnbind");
		return true;
	}
	
	@Override
	public void onRebind(Intent intent) {
		System.out.println("Remote onRebind");
		super.onRebind(intent);
	}
	
	@Override
	public void onDestroy() {
		System.out.println("Remote onDestroy");
		super.onDestroy();
	}
	
	private class MyBinder extends Stub {
		public boolean pay(Person p, int amount) throws RemoteException {
			System.out.println("向" + p.getName() + "支付" + amount);
			return amount < 100;
		}
	}

}
