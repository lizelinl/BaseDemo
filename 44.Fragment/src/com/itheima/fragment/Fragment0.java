package com.itheima.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment0 extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		// onCrate()
		return inflater.inflate(R.layout.f0, null);		// setContentView() 根据布局文件创建界面
	}

}
