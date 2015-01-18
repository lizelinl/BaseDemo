package com.itheima.imagemodify;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ImageView imageView;
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.imageView);
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qq);
	}
	
	public void zoomIn(View v) {
		Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth() * 2, bitmap.getHeight() * 2, bitmap.getConfig());	// �����µ�Bitmap, ָ����С
		Canvas canvas = new Canvas(newBitmap);		// ��������
		Matrix matrix = new Matrix();				// ����ģ��
		matrix.postScale(2, 2);						// �������ű���, x���y�ᶼ��2����С
		canvas.drawBitmap(bitmap, matrix, null);	// ����matrixģ��, �ڻ����ϰ���bitmap��
		
		imageView.setImageBitmap(newBitmap);		// ����ͼƬ���õ�ImageView��
		bitmap = newBitmap;							// ��ס��ͼƬ, �´δ����ͼƬ����
	}
	
	public void zoomOut(View v) {
		Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getConfig());
		Canvas canvas = new Canvas(newBitmap);	
		Matrix matrix = new Matrix();
		matrix.postScale(0.5f, 0.5f);	
		canvas.drawBitmap(bitmap, matrix, null);
		
		imageView.setImageBitmap(newBitmap);
		bitmap = newBitmap;	
	}
	
	public void rotate(View v) {
		Bitmap newBitmap = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getWidth(), bitmap.getConfig());
		Canvas canvas = new Canvas(newBitmap);	
		Matrix matrix = new Matrix();
		matrix.postRotate(90, bitmap.getWidth() / 2, bitmap.getHeight() / 2);	
		canvas.drawBitmap(bitmap, matrix, null);
		
		imageView.setImageBitmap(newBitmap);
		bitmap = newBitmap;	
	}
	
	public void inverse(View v) {
		Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight() , bitmap.getConfig());
		Canvas canvas = new Canvas(newBitmap);	
		Matrix matrix = new Matrix();
		matrix.postScale(-1, 1);						// -1 ����x��������(����֮��ͼƬ���ƶ������)
		matrix.postTranslate(bitmap.getWidth(), 0);		// �����ƶ�
		canvas.drawBitmap(bitmap, matrix, null);
		
		imageView.setImageBitmap(newBitmap);
		bitmap = newBitmap;	
	}

}
