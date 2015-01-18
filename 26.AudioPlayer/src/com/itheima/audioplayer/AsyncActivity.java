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
		
		// �����绰״̬, �����ͨ�˵绰, ��ͣ
		TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		manager.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	public void play(View v) throws Exception {
		if (player == null) {
			player = new MediaPlayer();
			player.setAudioStreamType(AudioManager.STREAM_MUSIC);
			player.setDataSource("file:///mnt/sdcard/1.mp4");
			player.prepareAsync();		// �����߳��м�������
			player.setOnPreparedListener(new MyPreparedListener());			// ����׼�����
			player.setOnCompletionListener(new MyCompletionListener());		// �����������
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
		dialog.setTitle("���ڻ���, ���Ժ�...");
		dialog.setCancelable(false);
		dialog.show();
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

	private class MyPreparedListener implements OnPreparedListener {
		public void onPrepared(MediaPlayer mp) {
			player.start();
			handleSeekBar();
			ib.setImageResource(android.R.drawable.ic_media_pause);
			player.setOnBufferingUpdateListener(new MyBufferListener());	// ������������
			dialog.dismiss();
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		player.stop();		// �ͷ���Ƶ�ļ����ڴ�
		player.release();	// �ͷŲ������ڴ�
		player = null;		// ��������
	}
	
	private class MyBufferListener implements OnBufferingUpdateListener {
		public void onBufferingUpdate(MediaPlayer mp, int percent) {
			sb.setSecondaryProgress(player.getDuration() / 100 * percent);		// ��ʾ�������ݵĽ���
		}
	}
	
	private class MyPhoneStateListener extends PhoneStateListener {
		private boolean isPlaying;
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
				case TelephonyManager.CALL_STATE_RINGING:	// ����
					if (player != null) {
						isPlaying = player.isPlaying();
						player.pause();
					}
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:	// ͨ��
					break;
				case TelephonyManager.CALL_STATE_IDLE:		// ����
					if (player != null && isPlaying) 
						player.start();
					break;
			}
		}
	}

}
