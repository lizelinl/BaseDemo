package com.itheima.videoplayer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class AsyncActivity extends Activity {

	private SeekBar sb;
	private ImageButton ib;
	private MediaPlayer player;
	private ProgressDialog dialog;
	private SurfaceView sv;
	private int position;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sb = (SeekBar) findViewById(R.id.sb);
		ib = (ImageButton) findViewById(R.id.ib);
		sv = (SurfaceView) findViewById(R.id.sv);
		
		sv.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);	// 为了兼容低版本设置缓冲类型
		sv.getHolder().addCallback(new MyCallback());	// 监听SurfaceView的摧毁和重构
	}
	
	private class MyCallback implements Callback {
		public void surfaceDestroyed(SurfaceHolder holder) {	// 摧毁的时候, 记住播放位置, 释放资源
			if (player != null) {
				position = player.getCurrentPosition();
				player.stop();
				player.release();
				player = null;
			}
		}
		public void surfaceCreated(SurfaceHolder holder) {		// 重构的时候, 继续播放
			if (position != 0) 	
				start();
		}
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		}
	}
	
	public void play(View v) throws Exception {
		if (player == null) {
			start();
		} else if (player.isPlaying()) {
			player.pause();
		} else {
			player.start();
		}
		ib.setImageResource(player.isPlaying() ? android.R.drawable.ic_media_pause : android.R.drawable.ic_media_play);
	}

	private void start() {
		try {
			player = new MediaPlayer();
			player.setDisplay(sv.getHolder());								// 设置视频显示位置
			player.setDataSource("file:///mnt/sdcard/1.mp4");
			player.prepareAsync();											// 在新线程中加载数据
			player.setOnPreparedListener(new MyPreparedListener());			// 监听准备完成
			player.setOnCompletionListener(new MyCompletionListener());		// 监听播放完成
			showProgressDialog();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showProgressDialog() {
		dialog = new ProgressDialog(this);
		dialog.setTitle("正在缓冲, 请稍候...");
		dialog.setCancelable(false);
		dialog.show();
	}

	private void handleSeekBar() {
		sb.setMax(player.getDuration());	// 把进度条的最大刻度设置为音频文件的持续时间
		
		new Thread() {
			public void run() {
				while (player != null) {
					try {
						if (player.isPlaying() && !sb.isPressed())
							sb.setProgress(player.getCurrentPosition());	// 每隔一段时间, 设置进度条的当前进度为播放器的当前进度
						SystemClock.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			}
			public void onStopTrackingTouch(SeekBar seekBar) {
				if (player != null)
					player.seekTo(sb.getProgress());	// 设置播放器播放进度为进度条当前进度
			}
		});
	}
	
	private class MyCompletionListener implements OnCompletionListener {
		public void onCompletion(MediaPlayer mp) {
			player.stop();		// 释放音频文件的内存
			player.release();	// 释放播放器内存
			player = null;		// 垃圾回收
			sb.setProgress(0);
			ib.setImageResource(android.R.drawable.ic_media_play);
		}
	}

	private class MyPreparedListener implements OnPreparedListener {
		public void onPrepared(MediaPlayer mp) {
			player.seekTo(position);
			player.start();
			handleSeekBar();
			ib.setImageResource(android.R.drawable.ic_media_pause);
			player.setOnBufferingUpdateListener(new MyBufferListener());	// 监听缓冲数据
			dialog.dismiss();
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		player.stop();		// 释放音频文件的内存
		player.release();	// 释放播放器内存
		player = null;		// 垃圾回收
	}
	
	private class MyBufferListener implements OnBufferingUpdateListener {
		public void onBufferingUpdate(MediaPlayer mp, int percent) {
			sb.setSecondaryProgress(player.getDuration() / 100 * percent);		// 显示缓冲数据的进度
		}
	}
	
}
