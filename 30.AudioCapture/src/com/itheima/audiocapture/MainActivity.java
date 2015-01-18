package com.itheima.audiocapture;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button startBT;
	private Button stopBT;
	private MediaRecorder recorder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		startBT = (Button) findViewById(R.id.startBT);
		stopBT = (Button) findViewById(R.id.stopBT);
	}

	public void start(View v) throws Exception {
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile("/mnt/sdcard/" + System.currentTimeMillis() + ".3gp");
		recorder.prepare();
		recorder.start();

		startBT.setEnabled(false);
		stopBT.setEnabled(true);
	}

	public void stop(View v) {
		recorder.stop();
		recorder.release();

		startBT.setEnabled(true);
		stopBT.setEnabled(false);
	}

}
