package com.itheima.sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class MainActivity extends Activity implements SensorEventListener {

	private SensorManager manager;
	private Sensor sensor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		manager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensor = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);	// ×¢²á¼àÌýÆ÷
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		manager.unregisterListener(this);	// ×¢Ïú¼àÌý
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		/*
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		System.out.println("x = " + x + ", y = " + y + ", z = " + z);
		*/
		
		System.out.println(event.values[0]);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

}
