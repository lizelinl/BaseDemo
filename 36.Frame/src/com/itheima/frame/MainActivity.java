package com.itheima.frame;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ImageView iv;
	private AnimationDrawable ad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		iv = (ImageView) findViewById(R.id.iv);
		iv.setImageResource(R.anim.frame);
		ad = (AnimationDrawable) iv.getDrawable();
	}

	public void start(View v) {
		if (ad.isRunning())
			ad.stop();
		ad.start();
	}

}
