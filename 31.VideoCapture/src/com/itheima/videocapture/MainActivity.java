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
				c = Camera.open(0);						// 获取摄像头对象
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
		if (recorder == null) {
			c.unlock();							// 解锁
			recorder = new MediaRecorder();		// 创建媒体记录器
			recorder.setCamera(c);				// 设置要录制的摄像头
			recorder.setAudioSource(AudioSource.MIC);		// 设置音频源
			recorder.setVideoSource(VideoSource.CAMERA);	// 设置视频源
			recorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_LOW));		// 设置清晰度
			recorder.setOutputFile("/mnt/sdcard/" + System.currentTimeMillis() + ".mp4");	// 设置文件路径
			recorder.setPreviewDisplay(sv.getHolder().getSurface());						// 录制时的显示
			recorder.prepare();		// 准备
			recorder.start();		// 开始录制
			ib.setImageResource(android.R.drawable.presence_video_busy);	// 改变按钮图标
		} else {
			recorder.stop();		// 停止录制
			recorder.release();		// 释放
			recorder = null;		// 垃圾回收
			c.lock();				// 锁定
			ib.setImageResource(android.R.drawable.presence_video_online);	// 改变按钮图标
		}
	}


}
