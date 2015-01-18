package com.itheima.sqlite.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.itheima.sqlite.R;
import com.itheima.sqlite.bean.Account;
import com.itheima.sqlite.dao.ClassicAccountDao;

@SuppressWarnings("unchecked")
public class SimpleActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        
        // �����ݿ��в�ѯ����������
        ClassicAccountDao dao = new ClassicAccountDao(this);
        List<Account> list = dao.queryAll();									// ��װ���ݵļ���
        List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();	// ������Ҫ��ļ���
        
        // ������װ���ݵļ���, ��ÿ��Account�е�����װ��һ��Map��, �ٰ�����Mapװ��������Ҫ��ļ�����
		for (Account a : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("_id", a.getId());
			map.put("name", a.getName());
			map.put("balance", a.getBalance());
			data.add(map);
		}
        
        // ����Adapter
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.account_item	// ����������, ָ��Context, ����, �����ļ�
        		, new String[]{ "_id", "name", "balance" }							// ����Map�е�Key
        		, new int[]{ R.id.idTV, R.id.nameTV, R.id.balanceTV });				// TextView��id
        
        // ����Adapter
        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);	// ��ȡ��Ŀ�ϵ�����(Map)
				Toast.makeText(SimpleActivity.this, map.get("name").toString(), Toast.LENGTH_SHORT).show();
			}
		});
        
    }
    
}
