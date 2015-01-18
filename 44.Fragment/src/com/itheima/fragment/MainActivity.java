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
		adapter.add("ѡ��1");
		adapter.add("ѡ��2");
		adapter.add("ѡ��3");
		adapter.add("ѡ��4");
		adapter.add("ѡ��5");
		adapter.add("ѡ��6");
		adapter.add("ѡ��7");
		adapter.add("ѡ��8");
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {		// ����ListView����¼�
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				FragmentManager manager = getSupportFragmentManager();	// getFragmentManager();	// ��ȡ������
				FragmentTransaction ft = manager.beginTransaction();	// ������
				
				switch (position) {
					case 0:
						ft.replace(R.id.rightLL, new Fragment0());		// ���ұߵ�LinearLayout�滻ΪFragment0
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
				ft.commit();	// �ύ����
			}
		});
	}

}
