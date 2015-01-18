package com.itheima.tween;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.imageView);
	}

	public void alpha(View v) {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);	// 加载XML定义动画特效
//		anim.setFillAfter(true);			// 保持结束时的画面
		imageView.startAnimation(anim);		// 应用动画特效
	}
	
	public void scale(View v) {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale);
		imageView.startAnimation(anim);	
	}
	
	public void rotate(View v) {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.rotate);
		imageView.startAnimation(anim);	
	}
	
	public void translate(View v) {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate);
		imageView.startAnimation(anim);	
	}
	
	public void combo(View v) {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.combo);
		imageView.startAnimation(anim);	
	}

}
