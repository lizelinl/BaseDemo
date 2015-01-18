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
        
        // 从数据库中查询出所有数据
        ClassicAccountDao dao = new ClassicAccountDao(this);
        List<Account> list = dao.queryAll();									// 封装数据的集合
        List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();	// 适配器要求的集合
        
        // 遍历封装数据的集合, 把每个Account中的数据装入一个Map中, 再把所有Map装入适配器要求的集合中
		for (Account a : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("_id", a.getId());
			map.put("name", a.getName());
			map.put("balance", a.getBalance());
			data.add(map);
		}
        
        // 创建Adapter
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.account_item	// 创建适配器, 指定Context, 数据, 布局文件
        		, new String[]{ "_id", "name", "balance" }							// 数据Map中的Key
        		, new int[]{ R.id.idTV, R.id.nameTV, R.id.balanceTV });				// TextView的id
        
        // 设置Adapter
        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);	// 获取条目上的数据(Map)
				Toast.makeText(SimpleActivity.this, map.get("name").toString(), Toast.LENGTH_SHORT).show();
			}
		});
        
    }
    
}
