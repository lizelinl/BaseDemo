package com.itheima.audioplayer;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class PrepareActivity extends Activity {

	private SeekBar sb;
	private ImageButton ib;
	private MediaPlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sb = (SeekBar) findViewById(R.id.sb);
		ib = (ImageButton) findViewById(R.id.ib);
	}
	
	public void play(View v) throws Exception {
		if (player == null) {
			player = new MediaPlayer();
			player.setAudioStreamType(AudioManager.STREAM_MUSIC);
			player.setDataSource("http://192.168.1.251:8080/WebServer/2.mp3");
			player.prepare(); 			// 加载数据(主线程)
			player.start();
//			player.setLooping(true);	// 设置是否循环播放(单曲循环)
			player.setOnCompletionListener(new MyCompletionListener());		// 监听播放完成
			handleSeekBar();
		} else if (player.isPlaying()) {
			player.pause();
		} else {
			player.start();
		}
		
		ib.setImageResource(player.isPlaying() ? android.R.drawable.ic_media_pause : android.R.drawable.ic_media_play);
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


}
