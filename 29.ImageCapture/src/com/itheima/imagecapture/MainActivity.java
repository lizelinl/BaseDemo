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
				c = Camera.open();						// ��ȡ����ͷ����
				
				Parameters params = c.getParameters();	// ��ȡ����ͷ�����ò���
				System.out.println(params.flatten());
				params.setPictureSize(800, 480);		// ͼƬ�ߴ�
				params.setPreviewSize(800, 480);		// Ԥ���ߴ�
				params.setJpegQuality(50);				// JPEG����
				params.setFlashMode("on");				// �����
				c.setParameters(params);				// ���ò���
				
				c.setPreviewDisplay(sv.getHolder());	// ����Ԥ��λ��
				c.startPreview();						// ��ʾԤ������
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		}
		public void surfaceDestroyed(SurfaceHolder holder) {
			c.stopPreview();	// ֹͣԤ��
			c.release();		// �ͷ�����ͷ(����ͷֻ�ܱ�1��Ӧ��ʹ��)
			c = null;			// ��������
		}
	}
	
	public void take(View v) throws Exception {
		c.autoFocus(new AutoFocusCallback() {
			public void onAutoFocus(boolean success, Camera camera) {	// �Զ��Խ�֮��ִ��
				
				c.takePicture(null, null, new PictureCallback() {
					public void onPictureTaken(byte[] data, Camera camera) {	// ���JPGͼƬ���ݺ�ִ��
						try {
							FileOutputStream out = new FileOutputStream(new File("/mnt/sdcard/" + System.currentTimeMillis() + ".jpg"));
							out.write(data);	// д����Ƭ����
							out.close();
							c.startPreview();	// ���¿�ʼԤ��
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
	}

}
