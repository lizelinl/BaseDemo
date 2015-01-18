package com.itheima.launchmode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class A_Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity);
	}
	
	public void launchA(View v) {
		startActivity(new Intent(this, A_Activity.class));
	}
	
	public void launchB(View v) {
		startActivity(new Intent(this, B_Activity.class));
	}

}
