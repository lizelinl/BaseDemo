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
			player.prepare(); 			// ��������(���߳�)
			player.start();
//			player.setLooping(true);	// �����Ƿ�ѭ������(����ѭ��)
			player.setOnCompletionListener(new MyCompletionListener());		// �����������
			handleSeekBar();
		} else if (player.isPlaying()) {
			player.pause();
		} else {
			player.start();
		}
		
		ib.setImageResource(player.isPlaying() ? android.R.drawable.ic_media_pause : android.R.drawable.ic_media_play);
	}

	private void handleSeekBar() {
		sb.setMax(player.getDuration());	// �ѽ����������̶�����Ϊ��Ƶ�ļ��ĳ���ʱ��
		
		new Thread() {
			public void run() {
				while (player != null) {
					if (player.isPlaying() && !sb.isPressed())
						sb.setProgress(player.getCurrentPosition());	// ÿ��һ��ʱ��, ���ý������ĵ�ǰ����Ϊ�������ĵ�ǰ����
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
					player.seekTo(sb.getProgress());	// ���ò��������Ž���Ϊ��������ǰ����
			}
		});
	}
	
	private class MyCompletionListener implements OnCompletionListener {
		public void onCompletion(MediaPlayer mp) {
			player.stop();		// �ͷ���Ƶ�ļ����ڴ�
			player.release();	// �ͷŲ������ڴ�
			player = null;		// ��������
			sb.setProgress(0);
			ib.setImageResource(android.R.drawable.ic_media_play);
		}
	}


}
