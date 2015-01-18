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
		
		final GestureLibrary library = GestureLibraries.fromRawResource(this, R.raw.gestures);	// ���ݿ��ļ��������ƿ����
		library.load();
		
		GestureOverlayView gov = (GestureOverlayView) findViewById(R.id.gov);
		
		gov.addOnGesturePerformedListener(new OnGesturePerformedListener() {
			public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {		// �û�����һ������֮��ִ��
				ArrayList<Prediction> list = library.recognize(gesture);						// �����ƿ�ʶ������
				for (Prediction p : list) 
					System.out.println(p.name + ": " + p.score);
				
				if (list.get(0).score > 4)
					Toast.makeText(getApplicationContext(), list.get(0).name, Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "�����޷�ʶ��", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
