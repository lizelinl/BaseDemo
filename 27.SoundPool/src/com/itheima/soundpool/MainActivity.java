package com.itheima.soundpool;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	private SoundPool pool;
	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		pool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);		// ¥¥Ω®“Ù∆µ≥ÿ
		id = pool.load(this, R.raw.jiaodizhu, 1);					// º”‘ÿ“Ù∆µ(“Ï≤Ωº”‘ÿ)
	}
	
	public void jiaodizhu(View v) {
		pool.play(id, 1, 1, 0, 0, 1);	// ≤•∑≈“Ù∆µ
	}

}
