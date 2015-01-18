package com.itheima.videocapture;

import java.io.IOException;

import android.app.Activity;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.VideoSource;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	private SurfaceView sv;
	private ImageButton ib;
	private Camera c;
	private MediaRecorder recorder;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sv = (SurfaceView) findViewById(R.id.sv);
		ib = (ImageButton) findViewById(R.id.ib);
		
		sv.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		sv.getHolder().addCallback(new MyCallback());
	}
	
	private class MyCallback implements Callback {
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				c = Camera.open(0);						// ��ȡ����ͷ����
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
		if (recorder == null) {
			c.unlock();							// ����
			recorder = new MediaRecorder();		// ����ý���¼��
			recorder.setCamera(c);				// ����Ҫ¼�Ƶ�����ͷ
			recorder.setAudioSource(AudioSource.MIC);		// ������ƵԴ
			recorder.setVideoSource(VideoSource.CAMERA);	// ������ƵԴ
			recorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_LOW));		// ����������
			recorder.setOutputFile("/mnt/sdcard/" + System.currentTimeMillis() + ".mp4");	// �����ļ�·��
			recorder.setPreviewDisplay(sv.getHolder().getSurface());						// ¼��ʱ����ʾ
			recorder.prepare();		// ׼��
			recorder.start();		// ��ʼ¼��
			ib.setImageResource(android.R.drawable.presence_video_busy);	// �ı䰴ťͼ��
		} else {
			recorder.stop();		// ֹͣ¼��
			recorder.release();		// �ͷ�
			recorder = null;		// ��������
			c.lock();				// ����
			ib.setImageResource(android.R.drawable.presence_video_online);	// �ı䰴ťͼ��
		}
	}


}
