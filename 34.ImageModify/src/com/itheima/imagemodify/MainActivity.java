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
		Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth() * 2, bitmap.getHeight() * 2, bitmap.getConfig());	// 创建新的Bitmap, 指定大小
		Canvas canvas = new Canvas(newBitmap);		// 创建画布
		Matrix matrix = new Matrix();				// 创建模型
		matrix.postScale(2, 2);						// 设置缩放比例, x轴和y轴都是2倍大小
		canvas.drawBitmap(bitmap, matrix, null);	// 参照matrix模型, 在画布上按照bitmap画
		
		imageView.setImageBitmap(newBitmap);		// 把新图片设置到ImageView中
		bitmap = newBitmap;							// 记住新图片, 下次从这个图片操作
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
		matrix.postScale(-1, 1);						// -1 代表x轴做镜像(镜像之后图片会移动到左边)
		matrix.postTranslate(bitmap.getWidth(), 0);		// 向右移动
		canvas.drawBitmap(bitmap, matrix, null);
		
		imageView.setImageBitmap(newBitmap);
		bitmap = newBitmap;	
	}

}
