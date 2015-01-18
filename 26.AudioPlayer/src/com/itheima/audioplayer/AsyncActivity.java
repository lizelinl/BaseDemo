package com.itheima.audioplayer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class AsyncActivity extends Activity {

	private SeekBar sb;
	private ImageButton ib;
	private MediaPlayer player;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sb = (SeekBar) findViewById(R.id.sb);
		ib = (ImageButton) findViewById(R.id.ib);
		
		// 监听电话状态, 如果接通了电话, 暂停
		TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		manager.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	public void play(View v) throws Exception {
		if (player == null) {
			player = new MediaPlayer();
			player.setAudioStreamType(AudioManager.STREAM_MUSIC);
			player.setDataSource("file:///mnt/sdcard/1.mp4");
			player.prepareAsync();		// 在新线程中加载数据
			player.setOnPreparedListener(new MyPreparedListener());			// 监听准备完成
			player.setOnCompletionListener(new MyCompletionListener());		// 监听播放完成
			showProgressDialog();
		} else if (player.isPlaying()) {
			player.pause();
		} else {
			player.start();
		}
		ib.setImageResource(player.isPlaying() ? android.R.drawable.ic_media_pause : android.R.drawable.ic_media_play);
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
					if (player.isPlaying() && !sb.isPressed())
						sb.setProgress(player.getCurrentPosition());	// 每隔一段时间, 设置进度条的当前进度为播放器的当前进度
					SystemClock.sleep(100);
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
	
	private class MyPhoneStateListener extends PhoneStateListener {
		private boolean isPlaying;
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
				case TelephonyManager.CALL_STATE_RINGING:	// 震铃
					if (player != null) {
						isPlaying = player.isPlaying();
						player.pause();
					}
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:	// 通话
					break;
				case TelephonyManager.CALL_STATE_IDLE:		// 空闲
					if (player != null && isPlaying) 
						player.start();
					break;
			}
		}
	}

}
