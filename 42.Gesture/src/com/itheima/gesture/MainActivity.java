package com.itheima.gesture;

import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final GestureLibrary library = GestureLibraries.fromRawResource(this, R.raw.gestures);	// 根据库文件创建手势库对象
		library.load();
		
		GestureOverlayView gov = (GestureOverlayView) findViewById(R.id.gov);
		
		gov.addOnGesturePerformedListener(new OnGesturePerformedListener() {
			public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {		// 用户做了一个手势之后执行
				ArrayList<Prediction> list = library.recognize(gesture);						// 用手势库识别手势
				for (Prediction p : list) 
					System.out.println(p.name + ": " + p.score);
				
				if (list.get(0).score > 4)
					Toast.makeText(getApplicationContext(), list.get(0).name, Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "手势无法识别", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
