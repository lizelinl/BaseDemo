package com.itheima.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView listView = (ListView) findViewById(R.id.listView);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item, R.id.textView);
		adapter.add("选项1");
		adapter.add("选项2");
		adapter.add("选项3");
		adapter.add("选项4");
		adapter.add("选项5");
		adapter.add("选项6");
		adapter.add("选项7");
		adapter.add("选项8");
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {		// 监听ListView点击事件
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				FragmentManager manager = getSupportFragmentManager();	// getFragmentManager();	// 获取管理器
				FragmentTransaction ft = manager.beginTransaction();	// 打开事务
				
				switch (position) {
					case 0:
						ft.replace(R.id.rightLL, new Fragment0());		// 把右边的LinearLayout替换为Fragment0
						break;
					case 1:
						ft.replace(R.id.rightLL, new Fragment1());		
						break;
					case 2:
						ft.replace(R.id.rightLL, new Fragment2());		
						break;
					case 3:
						ft.replace(R.id.rightLL, new Fragment3());		
						break;
				}
				ft.commit();	// 提交事务
			}
		});
	}

}
