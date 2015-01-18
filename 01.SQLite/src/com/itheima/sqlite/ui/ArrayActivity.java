package com.itheima.sqlite.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.itheima.sqlite.R;

public class ArrayActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        
        // ����ID��ȡListView
        ListView lv = (ListView) findViewById(R.id.lv);
        
        // Ҫ��ӵ�����
        String[] arr = { "��Ŀ1", "��Ŀ2", "��Ŀ3", "��Ŀ4", "��Ŀ5", "��Ŀ6", "��Ŀ7", "��Ŀ8", "��Ŀ9", "��Ŀ10" };
        
        // ����������(�����ݷ�װ��View, ��ӵ�ListView�Ĺ���)
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item, R.id.tv, arr);
        
        // ��ListView�������Ŀ(����������)
        lv.setAdapter(adapter);
        
        // �������¼�
        lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String s = (String) parent.getItemAtPosition(position);				// ��ȡָ����Ŀ�ϵ�����
				Toast.makeText(ArrayActivity.this, s, Toast.LENGTH_SHORT).show();	// ����
			}
		});
    }
    
}
