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
		imageView.setOnTouchListener(new MyOnTouchListener());		// 添加触摸监听器
	}
	
	private class MyOnTouchListener implements OnTouchListener {
		private float x;		// 图片移动前的x轴坐标
		private float y;		// 图片移动前的y轴坐标
		private Matrix newMatrix = new Matrix(); 		// 用来移动图片的矩阵
		private Matrix oldMatrix = new Matrix();		// 图片移动前的矩阵
		private int type;		// 操作类型, 一根手指触摸还是两根手指触摸
		private float start;	// 第二根手指按下时的距离
		private float end;		// 两根手指移动后的距离
		private PointF point;	// 放大时的中心点

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
					start = countDistance(event); 		// 计算第二根手指按下时两点之间的距离
					point = countPoint(event); 			// 计算中心点
					oldMatrix.set(imageView.getImageMatrix());
					break;
				case MotionEvent.ACTION_MOVE:
					newMatrix.set(oldMatrix);
					if (type == 1) { 	// 1根手指触摸
						newMatrix.postTranslate(event.getX() - x, event.getY() - y);
					} else { 			// 2跟手指触摸
						end = countDistance(event); 	// 计算结束时距离
						float scale = end / start; 		// 计算缩放比例
						newMatrix.postScale(scale, scale, point.x, point.y); 	// 对模型进行缩放
					}
					break;
			}
			imageView.setImageMatrix(newMatrix); 	// 改变图片
			return true;
		}
	}

	public float countDistance(MotionEvent event) {
		float a = event.getX(1) - event.getX(0); 	// x轴距离
		float b = event.getY(1) - event.getY(0); 	// y轴距离
		return (float) Math.sqrt(a * a + b * b); 	// 勾股定理
	}

	public PointF countPoint(MotionEvent event) {
		float x = (event.getX(0) + event.getX(1)) / 2; 	// x轴中间点
		float y = (event.getY(0) + event.getY(1)) / 2; 	// y轴中间点
		return new PointF(x, y);
	}

	
	/*
	private class MyOnTouchListener implements OnTouchListener {
		private float x;
		private float y;
		private Matrix oldMatrix = new Matrix();
		private Matrix newMatrix = new Matrix();		// 用来操作图片的模型

		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {				// 判断触摸动作的类型
				case MotionEvent.ACTION_DOWN: 			// 按下时
					x = event.getX(); 					// 获取x轴坐标
					y = event.getY();					// 获取y轴坐标
					oldMatrix.set(imageView.getImageMatrix());		// 用模型记住图片所在位置
					break;
				case MotionEvent.ACTION_MOVE: 			// 移动时
					newMatrix.set(oldMatrix); 			// 用另一个模型记住按下时的位置
					newMatrix.postTranslate(event.getX() - x, event.getY() - y);	// 移动模型
					break;
			}
			imageView.setImageMatrix(newMatrix);		// 把图片放到了移动后的模型中
			return true;
		}
	}
	*/
	
}
