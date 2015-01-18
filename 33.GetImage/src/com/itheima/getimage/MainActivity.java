package com.itheima.getimage;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		iv = (ImageView) findViewById(R.id.iv);
	}
	
	public void open(View v) {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, 100);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		Uri uri = intent.getData();
		String path = getAbsolutePath(uri);
		Bitmap bm = getUsableBitmap(path);
		iv.setImageBitmap(bm);
		System.out.println(bm.getWidth() + ", " + bm.getHeight());
	}
	
	public String getAbsolutePath(Uri uri) {
		Cursor c = getContentResolver().query(uri, new String[] { "_data" }, null, null, null);
		c.moveToNext();
		return c.getString(0);
	}
	
	@SuppressWarnings("deprecation")
	private Bitmap getUsableBitmap(String path) {
		Options opts = new Options();
		opts.inJustDecodeBounds = true;			// ����ֻ����ͼƬ��С
		BitmapFactory.decodeFile(path, opts);
		
		WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
		int width = manager.getDefaultDisplay().getWidth();		// ��Ļ���
		int height = manager.getDefaultDisplay().getHeight();	// ��Ļ�߶�
		
		int xScale = opts.outWidth / width;
		int yScale = opts.outHeight / height;
		int scale = xScale > yScale ? xScale : yScale;	// �������ű���
		
		opts.inJustDecodeBounds = false;				// ���ò�ֻ����ͼƬ��С
		opts.inSampleSize = scale;						// �������ű���
		return BitmapFactory.decodeFile(path, opts);	// �������ü���ͼƬ(����)
	}

}
	