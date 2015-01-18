package com.itheima.imagecapture;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;

public class MainActivity extends Activity {

	private SurfaceView sv;
	private Camera c;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sv = (SurfaceView) findViewById(R.id.sv);
		
		sv.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		sv.getHolder().addCallback(new MyCallback());
	}
	
	private class MyCallback implements Callback {
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				c = Camera.open();						// 获取摄像头对象
				
				Parameters params = c.getParameters();	// 获取摄像头的设置参数
				System.out.println(params.flatten());
				params.setPictureSize(800, 480);		// 图片尺寸
				params.setPreviewSize(800, 480);		// 预览尺寸
				params.setJpegQuality(50);				// JPEG质量
				params.setFlashMode("on");				// 闪光灯
				c.setParameters(params);				// 设置参数
				
				c.setPreviewDisplay(sv.getHolder());	// 设置预览位置
				c.startPreview();						// 显示预览画面
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		}
		public void surfaceDestroyed(SurfaceHolder holder) {
			c.stopPreview();	// 停止预览
			c.release();		// 释放摄像头(摄像头只能被1个应用使用)
			c = null;			// 垃圾回收
		}
	}
	
	public void take(View v) throws Exception {
		c.autoFocus(new AutoFocusCallback() {
			public void onAutoFocus(boolean success, Camera camera) {	// 自动对焦之后执行
				
				c.takePicture(null, null, new PictureCallback() {
					public void onPictureTaken(byte[] data, Camera camera) {	// 获得JPG图片数据后执行
						try {
							FileOutputStream out = new FileOutputStream(new File("/mnt/sdcard/" + System.currentTimeMillis() + ".jpg"));
							out.write(data);	// 写出照片数据
							out.close();
							c.startPreview();	// 重新开始预览
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
	}

}
