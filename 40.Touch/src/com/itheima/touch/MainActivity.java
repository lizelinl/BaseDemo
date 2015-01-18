package com.itheima.touch;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		imageView = (ImageView) findViewById(R.id.imageView);
		imageView.setOnTouchListener(new MyOnTouchListener());		// ��Ӵ���������
	}
	
	private class MyOnTouchListener implements OnTouchListener {
		private float x;		// ͼƬ�ƶ�ǰ��x������
		private float y;		// ͼƬ�ƶ�ǰ��y������
		private Matrix newMatrix = new Matrix(); 		// �����ƶ�ͼƬ�ľ���
		private Matrix oldMatrix = new Matrix();		// ͼƬ�ƶ�ǰ�ľ���
		private int type;		// ��������, һ����ָ��������������ָ����
		private float start;	// �ڶ�����ָ����ʱ�ľ���
		private float end;		// ������ָ�ƶ���ľ���
		private PointF point;	// �Ŵ�ʱ�����ĵ�

		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction() & MotionEvent.ACTION_MASK) {  
				case MotionEvent.ACTION_DOWN:
					type = 1;
					x = event.getX();
					y = event.getY();
					oldMatrix.set(imageView.getImageMatrix());
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					type = 2;
					start = countDistance(event); 		// ����ڶ�����ָ����ʱ����֮��ľ���
					point = countPoint(event); 			// �������ĵ�
					oldMatrix.set(imageView.getImageMatrix());
					break;
				case MotionEvent.ACTION_MOVE:
					newMatrix.set(oldMatrix);
					if (type == 1) { 	// 1����ָ����
						newMatrix.postTranslate(event.getX() - x, event.getY() - y);
					} else { 			// 2����ָ����
						end = countDistance(event); 	// �������ʱ����
						float scale = end / start; 		// �������ű���
						newMatrix.postScale(scale, scale, point.x, point.y); 	// ��ģ�ͽ�������
					}
					break;
			}
			imageView.setImageMatrix(newMatrix); 	// �ı�ͼƬ
			return true;
		}
	}

	public float countDistance(MotionEvent event) {
		float a = event.getX(1) - event.getX(0); 	// x�����
		float b = event.getY(1) - event.getY(0); 	// y�����
		return (float) Math.sqrt(a * a + b * b); 	// ���ɶ���
	}

	public PointF countPoint(MotionEvent event) {
		float x = (event.getX(0) + event.getX(1)) / 2; 	// x���м��
		float y = (event.getY(0) + event.getY(1)) / 2; 	// y���м��
		return new PointF(x, y);
	}

	
	/*
	private class MyOnTouchListener implements OnTouchListener {
		private float x;
		private float y;
		private Matrix oldMatrix = new Matrix();
		private Matrix newMatrix = new Matrix();		// ��������ͼƬ��ģ��

		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {				// �жϴ�������������
				case MotionEvent.ACTION_DOWN: 			// ����ʱ
					x = event.getX(); 					// ��ȡx������
					y = event.getY();					// ��ȡy������
					oldMatrix.set(imageView.getImageMatrix());		// ��ģ�ͼ�סͼƬ����λ��
					break;
				case MotionEvent.ACTION_MOVE: 			// �ƶ�ʱ
					newMatrix.set(oldMatrix); 			// ����һ��ģ�ͼ�ס����ʱ��λ��
					newMatrix.postTranslate(event.getX() - x, event.getY() - y);	// �ƶ�ģ��
					break;
			}
			imageView.setImageMatrix(newMatrix);		// ��ͼƬ�ŵ����ƶ����ģ����
			return true;
		}
	}
	*/
	
}
