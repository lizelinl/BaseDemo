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
		
		sv.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);	// Ϊ�˼��ݵͰ汾���û�������
		sv.getHolder().addCallback(new MyCallback());	// ����SurfaceView�Ĵݻٺ��ع�
	}
	
	private class MyCallback implements Callback {
		public void surfaceDestroyed(SurfaceHolder holder) {	// �ݻٵ�ʱ��, ��ס����λ��, �ͷ���Դ
			if (player != null) {
				position = player.getCurrentPosition();
				player.stop();
				player.release();
				player = null;
			}
		}
		public void surfaceCreated(SurfaceHolder holder) {		// �ع���ʱ��, ��������
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
			player.setDisplay(sv.getHolder());								// ������Ƶ��ʾλ��
			player.setDataSource("file:///mnt/sdcard/1.mp4");
			player.prepareAsync();											// �����߳��м�������
			player.setOnPreparedListener(new MyPreparedListener());			// ����׼�����
			player.setOnCompletionListener(new MyCompletionListener());		// �����������
			showProgressDialog();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
					try {
						if (player.isPlaying() && !sb.isPressed())
							sb.setProgress(player.getCurrentPosition());	// ÿ��һ��ʱ��, ���ý������ĵ�ǰ����Ϊ�������ĵ�ǰ����
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
			player.seekTo(position);
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
	
}
